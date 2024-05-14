import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { CdkTableModule } from '@angular/cdk/table';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialRoutes } from './material.routing';
import { MaterialModule } from '../shared/material-module';
import { ManageCategoryComponent } from './manage-category/manage-category.component';
import { CategoryComponent } from './dialog/category/category.component';
import { ManageRewardComponent } from './manage-reward/manage-reward.component';
import { RewardComponent } from './dialog/reward/reward.component';
import { ManageRedeemComponent } from './manage-redeem/manage-redeem.component';
import { ManageEmployeeComponent } from './manage-employee/manage-employee.component';
import { ManageRecognitionComponent } from './manage-recognition/manage-recognition.component';
import { AddRecognitionComponent } from './add-recognition/add-recognition.component';
import { EditRecognitionComponent } from './edit-recognition/edit-recognition.component';
import { RecognitionComponent } from './recognition/recognition.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { AllocationrecognitionHistoryComponent } from './allocationrecognition-history/allocationrecognition-history.component';
import { AllocationHistoryComponent } from './allocation-history/allocation-history.component';




@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(MaterialRoutes),
        MaterialModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        CdkTableModule
    ],
    providers: [],
    declarations: [

        ManageCategoryComponent,
        CategoryComponent,
        ManageRewardComponent,
        RewardComponent,
        ManageRedeemComponent,
        ManageEmployeeComponent,
        ManageRecognitionComponent,
        AddRecognitionComponent,
        EditRecognitionComponent,
        RecognitionComponent,
        PurchaseHistoryComponent,
        AllocationrecognitionHistoryComponent,
        AllocationHistoryComponent,
        
    ]
})
export class MaterialComponentsModule { }
