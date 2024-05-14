import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from 'src/app/services/category.service';
import { RewardService } from 'src/app/services/reward.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-reward',
  templateUrl: './reward.component.html',
  styleUrls: ['./reward.component.scss']
})
export class RewardComponent implements OnInit {

  onAddReward = new EventEmitter();
  onEditReward = new EventEmitter();
  rewardForm: any = FormGroup;
  dialogAction: any = "Add";
  action: any = "Add";
  responseMessage: any;
  categories: any = [];

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBuilder: FormBuilder,
    private rewardService: RewardService,
    public dialogRef: MatDialogRef<RewardComponent>,
    private categoryService: CategoryService,
    private snackbarService: SnackbarService) { }

  ngOnInit(): void {
    this.rewardForm = this.formBuilder.group({
      name: [null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]],
      categoryId: [null, [Validators.required]],
      description: [null, [Validators.required]],
      manufacturer: [null, [Validators.required]],
      quantityAvailable: [null, [Validators.required, Validators.min(0), Validators.max(10000),Validators.pattern(GlobalConstants.quantityAvailableRegex)]],
      imageThumbnailUrl: [null, [Validators.required]],
      termsAndConditions: [null, [Validators.required]],
      points: [null, [Validators.required, Validators.pattern(GlobalConstants.pointsRegex)]]
    });

    if (this.dialogData.action === "Edit") {
      this.dialogAction = "Edit";
      this.action = "Update";
      this.rewardForm.patchValue(this.dialogData.data);
    }
    this.getCategories();
  }

  getCategories() {
    this.categoryService.getCategories().subscribe({
      next: (response: any) => {
        this.categories = response;
      }, error: (error: any) => {
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    })
  }

  handleSubmit() {
    if (this.dialogAction === "Edit") {
      this.edit();
    }
    else {
      this.add();
    }
  }

  add() {
    var formData = this.rewardForm.value;
    var data = {
      name: formData.name,
      categoryId: formData.categoryId,
      description: formData.description,
      manufacturer: formData.manufacturer,
      quantityAvailable: formData.quantityAvailable,
      imageThumbnailUrl: formData.imageThumbnailUrl,
      termsAndConditions: formData.termsAndConditions,
      points: formData.points
    }
    this.rewardService.add(data).subscribe({
      next: (response: any) => {
        this.dialogRef.close();
        this.onAddReward.emit();
        this.responseMessage = response.message;
        this.snackbarService.openSnackBar(this.responseMessage, "success");
      }, error: (error: any) => {
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    })
  }

  edit() { 
    var formData = this.rewardForm.value;
    var data = {
      id: this.dialogData.data.id,
      name: formData.name,
      categoryId: formData.categoryId,
      description: formData.description,
      manufacturer: formData.manufacturer,
      quantityAvailable: formData.quantityAvailable,
      imageThumbnailUrl: formData.imageThumbnailUrl,
      termsAndConditions: formData.termsAndConditions,
      points: formData.points
    }
    this.rewardService.update(data).subscribe({
      next: (response: any) => {
        this.dialogRef.close();
        this.onEditReward.emit();
        this.responseMessage = response.message;
        this.snackbarService.openSnackBar(this.responseMessage, "success");
      }, error: (error: any) => {
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    })
  }
}

