import { Component, AfterViewInit, OnInit } from '@angular/core';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../services/snackbar.service';
import { GlobalConstants } from '../shared/global-constants';
import { DashboardsService } from '../services/dashboards.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  responseMessage: any;
  data: any;
  userData: any;
  name:any;
  winningPerfomer!: any;


  constructor(private dashboardsService: DashboardsService,
    private ngxService: NgxUiLoaderService,
    private snackbarService: SnackbarService,
    private userService:UserService) {
  }

  ngOnInit(): void {
    this.ngxService.start(); // Start the loader service

    // Fetch dashboard data
    this.dashboardsService.getDetails().subscribe({
      next: (response: any) => {
        this.data = response;
        // Stop the loader service after fetching dashboard data
        this.ngxService.stop();

        // Fetch logged-in user info
        this.getLoggedInUserInfo();

        this.getTopPerformersData();
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

  getTopPerformersData() {
    this.userService.getWinningPerfomer().subscribe({
      next: (response: any) => {
        this.winningPerfomer = response; 
      },
      error: (error: any) => {
        console.error('Error fetching winning performers:', error);
      }
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



}


