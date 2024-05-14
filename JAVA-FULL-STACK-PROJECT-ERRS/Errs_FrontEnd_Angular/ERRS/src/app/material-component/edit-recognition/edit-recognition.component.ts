import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RecognitionService } from 'src/app/services/recognition.service';
import { SnackbarService } from 'src/app/services/snackbar.service';

@Component({
  selector: 'app-edit-recognition',
  templateUrl: './edit-recognition.component.html',
  styleUrls: ['./edit-recognition.component.scss']
})
export class EditRecognitionComponent {

  recognitionForm!: FormGroup;
  recognitionId!: number;
  recognition!: any;
  responseMessage: any;

  constructor(private route: ActivatedRoute, private router: Router, private fb: FormBuilder,
    private recognitionService: RecognitionService, private snackbarService: SnackbarService) {

  }

  ngOnInit(): void {

    this.recognitionForm = this.fb.group({
      recognitionName: ['', [Validators.required]],
      points: ['', [Validators.required]]

    });

    this.route.params.subscribe(params => {
      this.recognitionId = params['recognitionId']; // to fetch routing params, 'recognitionId' should match the parameter name in the route

      // Checking if recognitionId is available before fetching recognition data
      if (this.recognitionId) {
        this.fetchRecognitionData();
      }
    });

  }

  fetchRecognitionData(): void {
    // Fetching recognition data using recognitionId
    this.recognitionService.getRecognitionById(this.recognitionId).subscribe(
      (res) => {
        // Assigning fetched data to recognition and populate form
        this.recognition = res;
        this.populateForm();
      },
      (error) => {
        console.error('Error fetching recognition:', error);
      }
    );
  }

  populateForm(): void {
    // Populating form fields with fetched recognition data
    this.recognitionForm.setValue({
      recognitionName: this.recognition.recognitionName,
      points: this.recognition.points,
    });
  }
  // Function to edit the recognition values in the database
  onSubmit() {
    console.log(this.recognitionForm.value);

    this.recognitionService.editRecognition(this.recognitionId, this.recognitionForm.value)
      .subscribe((res) => {
        console.log(res);

        // Navigating to the 'recognition' route after editing
        this.router.navigate(['../../recognitions'], { relativeTo: this.route });
      });
  }
}
