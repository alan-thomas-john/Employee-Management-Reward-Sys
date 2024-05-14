import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { DashboardsService } from 'src/app/services/dashboards.service';
import { RewardService } from 'src/app/services/reward.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { MatPaginator } from '@angular/material/paginator';


@Component({
  selector: 'app-purchase-history',
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.scss']
})
export class PurchaseHistoryComponent {
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  displayedColumns: string[] = ['productName', 'points', 'quantity', 'date']
  rewardList!: any;
  userId!: number;
  private subscriptions = new Subscription(); // to track all subscriptions
  userData: any;
  dataSource:any;

  constructor(private rewardService: RewardService, private route: ActivatedRoute,
    private snackbarService: SnackbarService,private dashboardsService: DashboardsService
  ) { }

  ngOnInit(): void {
    this.getLoggedInUserInfo();
  }

  ngAfterViewInit(): void {
    //  checking if dataSource is initialized before trying to assign the paginator.
    if (this.dataSource) {
      this.dataSource.paginator = this.paginator;
    }
  }

  getLoggedInUserInfo() {
    // Assuming dashboardsService is already injected in your constructor
    this.dashboardsService.getLoggedInUserInfo().subscribe({
      next: (response: any) => {
        this.userData = response;
        this.userId = this.userData.userId; // Assuming the userId is directly under userData
        this.loadPurchaseHistory(); // Loading purchase history after getting userId
      },
      error: (error: any) => {
        console.log(error);
        // Error handling
      }
    });
  }

  private loadPurchaseHistory() {
    const rewardSubscription = this.rewardService.getPurchaseHistoryByUserId(this.userId)
      .subscribe({
        next: (res:any) => {
          console.log(res);
          if (res.length > 0) {
            res.sort((a: any, b: any) => {
              return new Date(b.date).getTime() - new Date(a.date).getTime();
            });
            this.dataSource = new MatTableDataSource(res);
            this.dataSource.paginator = this.paginator;
          } else {
            this.dataSource = new MatTableDataSource(); // Ensuring the table refreshes with empty data
            this.snackbarService.openSnackBar("No purchase history found.", GlobalConstants.error);
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

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

}