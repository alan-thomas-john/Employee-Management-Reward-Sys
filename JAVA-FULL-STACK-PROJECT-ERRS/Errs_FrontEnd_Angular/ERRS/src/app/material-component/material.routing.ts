import { Routes } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ManageCategoryComponent } from './manage-category/manage-category.component';
import { RouteGuardService } from '../services/route-guard.service';
import { ManageRewardComponent } from './manage-reward/manage-reward.component';
import { ManageRedeemComponent } from './manage-redeem/manage-redeem.component';
import { ManageEmployeeComponent } from './manage-employee/manage-employee.component';
import { ManageRecognitionComponent } from './manage-recognition/manage-recognition.component';
import { AddRecognitionComponent } from './add-recognition/add-recognition.component';
import { RecognitionComponent } from './recognition/recognition.component';
import { EditRecognitionComponent } from './edit-recognition/edit-recognition.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { AllocationrecognitionHistoryComponent } from './allocationrecognition-history/allocationrecognition-history.component';
import { AllocationHistoryComponent } from './allocation-history/allocation-history.component';



export const MaterialRoutes: Routes = [

    {
        path: 'category',
        component: ManageCategoryComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },

    {
        path: 'product',
        component: ManageRewardComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },

    {
        path: 'redeem',
        component: ManageRedeemComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin', 'user']
        }
    },

    {
        path: 'purchasehistory',
        component: PurchaseHistoryComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin', 'user']
        }
    },

    {
        path: 'recognitionHistory',
        component: AllocationrecognitionHistoryComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin','user']
        }
    },

    {
        path: 'allocationHistory',
        component: AllocationHistoryComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },



    {
        path: 'user',
        component: ManageEmployeeComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },

    {
        path: 'recognition/:recognitionId',
        component: ManageRecognitionComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },

    {
        path: 'recognitionHandler',
        component: AddRecognitionComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },

    {
        path: 'recognitionHandler/:recognitionId',
        component: EditRecognitionComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },

    {
        path: 'recognitions',
        component: RecognitionComponent,
        canActivate: [RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },


];
