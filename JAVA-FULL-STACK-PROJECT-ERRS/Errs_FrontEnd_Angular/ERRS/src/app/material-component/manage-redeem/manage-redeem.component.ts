import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { CategoryService } from 'src/app/services/category.service';
import { DashboardsService } from 'src/app/services/dashboards.service';
import { RewardService } from 'src/app/services/reward.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-manage-redeem',
  templateUrl: './manage-redeem.component.html',
  styleUrls: ['./manage-redeem.component.scss']
})
export class ManageRedeemComponent implements OnInit {

  displayedColumns: string[] = ['name', 'category', 'points', 'quantityAvailable', 'imageThumbnailUrl', 'termsAndConditions', 'manufacturer', 'total', 'edit'];
  dataSource: any = [];
  manageRedeemForm: any = FormGroup;
  categories: any = [];
  rewards: any = [];
  points: any;
  imageThumbnailUrl: any;
  termsAndConditions: any;
  totalPoints: number = 0;
  responseMessage: any;
  userData: any;
  isRewardAlreadyAdded: boolean = false;
  validationMessage: string = '';
  rewardAddValidationMessage: string = '';


  constructor(private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private rewardService: RewardService,
    private snackbarService: SnackbarService,
    private ngxService: NgxUiLoaderService,
    private dashboardsService: DashboardsService) { }

  ngOnInit(): void {

    this.ngxService.start();
    this.getCategories();
    this.getLoggedInUserInfo();
    this.manageRedeemForm = this.formBuilder.group({
      redeemMethod: [null, [Validators.required]],
      reward: [null, [Validators.required]],
      category: [null, [Validators.required]],
      points: [null, [Validators.required]],
      total: [0, [Validators.required]],
      imageThumbnailUrl: [null, [Validators.required]],
      termsAndConditions: [null, [Validators.required]],
      manufacturer: [null, [Validators.required]],
      quantityAvailable: [null, [Validators.required]]

    });
  }

  getLoggedInUserInfo() {
    this.dashboardsService.getLoggedInUserInfo().subscribe({
      next: (response: any) => {
        this.userData = response;
      },
      error: (error: any) => {
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    });
  }


  getCategories() {
    this.categoryService.getFilteredCategories().subscribe({
      next: (response: any) => {
        this.ngxService.stop();
        this.categories = response;
      }, error: (error: any) => {
        this.ngxService.stop();
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

  getRewardsByCategory(value: any) {
    this.rewardService.getRewardsByCategory(value.id).subscribe({
      next: (response: any) => {
        this.rewards = response;
        this.manageRedeemForm.controls['points'].setValue('');
        this.manageRedeemForm.controls['total'].setValue(0);
        this.manageRedeemForm.controls['imageThumbnailUrl'].setValue(response.imageThumbnailUrl);
        this.manageRedeemForm.controls['termsAndConditions'].setValue(response.termsAndConditions);
        this.manageRedeemForm.controls['manufacturer'].setValue(response.manufacturer);
        this.manageRedeemForm.controls['quantityAvailable'].setValue(response.quantityAvailable);
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

  getRewardsDetails(value: any) {
    this.rewardService.getById(value.id).subscribe({
      next: (response: any) => {

        // Updating the isRewardAlreadyAdded variable based on whether the selected reward matches the one already added
        const rewardName = this.dataSource.find((e: { id: number }) => e.id !== value.id);
        this.isRewardAlreadyAdded = !!rewardName;
    
        this.points = response.points;
        this.imageThumbnailUrl = response.imageThumbnailUrl;
        this.manageRedeemForm.controls['points'].setValue(response.points);
        this.manageRedeemForm.controls['imageThumbnailUrl'].setValue(response.imageThumbnailUrl);
        this.manageRedeemForm.controls['total'].setValue(this.points * 1);
        this.manageRedeemForm.controls['termsAndConditions'].setValue(response.termsAndConditions);
        this.manageRedeemForm.controls['manufacturer'].setValue(response.manufacturer);
        this.manageRedeemForm.controls['quantityAvailable'].setValue(response.quantityAvailable);

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

  validateRewardAdd() {
    if (this.manageRedeemForm.controls['total'].value === 0 || this.manageRedeemForm.controls['total'].value === null) {
      this.rewardAddValidationMessage = "Please specify a reward to add in \" Select Reward \" .";
      return true;
    } else if (this.manageRedeemForm.controls['quantityAvailable'].value <= 0) {
      this.rewardAddValidationMessage = "Quantity available for specified reward is zero.";
      return true;
    } else {
      this.rewardAddValidationMessage = ""; // Clearing the message when the condition to add a reward is met
      return false;
    }
  }

 validateSubmit() {
    if (this.totalPoints === 0) {
        this.validationMessage = "Please add rewards to proceed.";
        return true;
    } else if (this.userData.points < this.totalPoints) {
        this.validationMessage = "Insufficient points to redeem the selected rewards.";
        return true;
    } else if (this.isRewardAlreadyAdded) {
        this.validationMessage = "The selected reward must be added before it can be submitted.";
        return true;
    } else {
        this.validationMessage = ""; // Clearing message when everything is okay
        return false;
    }
}

  add() {
    if (this.dataSource.length > 0) {
      this.snackbarService.openSnackBar("Only one reward can be added at a time or delete the product from added reward.", GlobalConstants.error);
      return;
    }

    var formData = this.manageRedeemForm.value;

    const rewardName = this.dataSource.find((e: { id: number }) => e.id !== formData.reward.id);
    this.isRewardAlreadyAdded = !!rewardName;
    this.snackbarService.openSnackBar("Delete the product from added rewards in order to add this reward and redeem.", GlobalConstants.error);

    if (rewardName === undefined) {
      if (formData.Quantity > formData.quantityAvailable) {
        this.snackbarService.openSnackBar('Not enough quantity available.', GlobalConstants.error);
        return;
      }
      this.totalPoints = this.totalPoints + formData.total;
      this.dataSource.push({ id: formData.reward.id, name: formData.reward.name, category: formData.category.name, Quantity: formData.Quantity, points: formData.points, total: formData.total, termsAndConditions: formData.termsAndConditions, imageThumbnailUrl: formData.imageThumbnailUrl, manufacturer: formData.manufacturer, quantityAvailable: formData.quantityAvailable });
      this.dataSource = [...this.dataSource];
      this.snackbarService.openSnackBar(GlobalConstants.rewardAdded, "success");

    }
    else {
      this.snackbarService.openSnackBar(GlobalConstants.rewarExistError, GlobalConstants.error);
    }
  }
  handleDeleteAction(value: any, element: any) {
    this.totalPoints = this.totalPoints - element.total;
    //incrementing available quantity back
    element.quantityAvailable += element.Quantity;
    this.dataSource.splice(value, 1);
    this.dataSource = [...this.dataSource];
  }

  submitAction() {
    var formData = this.manageRedeemForm.value;
    //  the order object structure 
    var purchase = {
      "quantity": 1,
      "date": new Date(),
      user: {
        userId: this.userData.userId //  userId in this.userData
      },
      product: {
        id: formData.reward.id //  reward is mapped to Product entity and has an id field
      }
    };

    this.buyReward(purchase);
  }

  buyReward(purchase: any) {
    var formData = this.manageRedeemForm.value;

    this.ngxService.start();
    this.rewardService.buyReward(purchase).subscribe({
      next: (response: any) => {
        this.ngxService.stop();
        // Handling success response
        console.log(response);
        this.responseMessage = response;
        const successMessage = this.responseMessage; // Concatenating the messages
        this.snackbarService.openSnackBar(successMessage, "success");

        //  refreshing the user details after purchase
        this.getRewardsDetails(formData.reward);
        this.getLoggedInUserInfo();
        // Checking if the quantity available becomes 0
        if (formData.quantityAvailable === 1) {
          // Reload the window after a delay of 3 seconds
          setTimeout(() => {
            window.location.reload();
          }, 1000); // Adjusting the time delay as needed
        }
      },
      error: (error: any) => {
        this.ngxService.stop();
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    });
  }

}
