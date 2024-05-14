import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RecognitionService } from 'src/app/services/recognition.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { SnackbarService } from 'src/app/services/snackbar.service';
//import {MatFormFieldModule} from '@angular/material/form-field';

@Component({
  selector: 'app-add-recognition',
  templateUrl: './add-recognition.component.html',
  styleUrls: ['./add-recognition.component.scss']
})
export class AddRecognitionComponent {

  recognitionForm!: FormGroup;
  responseMessage: any;

  constructor(private fb: FormBuilder, private recognitionService: RecognitionService,
    private router: Router, private route: ActivatedRoute, private snackbarService: SnackbarService) {

  }

  ngOnInit(): void {

    //form validation using reactive forms
    this.recognitionForm = this.fb.group({
      recognitionName: ['', [Validators.required,Validators.pattern(GlobalConstants.recognitionNameregex)]],
      points: ['', [Validators.required,Validators.min(0), Validators.max(1000000),Validators.pattern(GlobalConstants.pointsRegex)]]

    })

  }

  onSubmit() {
    console.log(this.recognitionForm.value);
    //adding recognitionsn into recognition table
    this.recognitionService.addRecognition(this.recognitionForm.value).subscribe({
      next: (response: any) => {
        console.log(response);
        this.snackbarService.openSnackBar('Recognition added successfully', 'Close');

        // Navigating after the recognition has been added
        setTimeout(() => {
          this.router.navigate(['../recognitions'], { relativeTo: this.route });
        }, 2000); // 2 seconds delay before routing
      }, error: (error: any) => {
        console.log(error.error?.message);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        }
        else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    })
  }
}
