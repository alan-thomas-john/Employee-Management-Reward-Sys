import { Component,ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { Subscription } from 'rxjs';
import { DashboardsService } from 'src/app/services/dashboards.service';
import { RecognitionService } from 'src/app/services/recognition.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { GlobalConstants } from 'src/app/shared/global-constants';


@Component({
  selector: 'app-allocationrecognition-history',
  templateUrl: './allocationrecognition-history.component.html',
  styleUrls: ['./allocationrecognition-history.component.scss']
})
export class AllocationrecognitionHistoryComponent {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
 
  displayedColumns: string[] = ['recognitionName', 'date', 'points'];
  recognitionList!:any;
  userId!: number;
  private subscriptions = new Subscription(); // to track all subscriptions
  userData: any;
  dataSource:any;

  constructor(private ngxService: NgxUiLoaderService ,
    private snackbarService: SnackbarService,private dashboardsService: DashboardsService,
    private recognitionService: RecognitionService,
  ) { }

  ngOnInit(): void {
    //this.ngxService.start();
    this.getLoggedInUserInfo();
  }

  ngAfterViewInit(): void {
    // Making sure to check if dataSource is initialized before trying to assign the paginator,so this is used.
    if (this.dataSource) {
      this.dataSource.paginator = this.paginator;
    }
  }

  getLoggedInUserInfo() {
    this.dashboardsService.getLoggedInUserInfo().subscribe({
      next: (response: any) => {
        this.userData = response;
        this.userId = this.userData.userId; 
        this.loadRecognitionHistory(); // Loading purchase history after getting userId
      },
      error: (error: any) => {
        console.log(error);
        // Error handling
      }
    });
  }

  private loadRecognitionHistory() {
    const rewardSubscription = this.recognitionService.getRecognitionByUserId(this.userId)
      .subscribe({
        next: (res: any) => {
          console.log(res);
          if (res.length > 0) {
            res.sort((a: any, b: any) => {
              return new Date(b.date).getTime() - new Date(a.date).getTime();
            });
            this.dataSource = new MatTableDataSource(res);
            this.dataSource.paginator = this.paginator;
          } else {
            this.dataSource = new MatTableDataSource([]);
            this.snackbarService.openSnackBar("No recognition history found.", GlobalConstants.error);
          }
        },
        error: (error) => {
          console.error(error);
          let errorMessage = GlobalConstants.genericError;
          if (error.error?.message) {
            errorMessage = error.error.message;
          }
          this.snackbarService.openSnackBar(errorMessage, GlobalConstants.error);
        }
      });
  
    this.subscriptions.add(rewardSubscription);
  }
}
