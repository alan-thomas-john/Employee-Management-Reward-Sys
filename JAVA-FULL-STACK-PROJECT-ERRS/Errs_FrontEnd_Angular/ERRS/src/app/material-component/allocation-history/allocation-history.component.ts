import { Component,ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { Subscription } from 'rxjs';
import { DashboardsService } from 'src/app/services/dashboards.service';
import { RecognitionService } from 'src/app/services/recognition.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-allocation-history',
  templateUrl: './allocation-history.component.html',
  styleUrls: ['./allocation-history.component.scss']
})
export class AllocationHistoryComponent {
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  displayedColumns: string[] = ['recognitionName', 'date', 'points', 'userName', 'contactNumber', 'email'];
  private subscriptions = new Subscription(); // to track all subscriptions
  userData: any;
  dataSource:any;

  constructor(private ngxService: NgxUiLoaderService , private route: ActivatedRoute,
    private snackbarService: SnackbarService,
    private recognitionService: RecognitionService,
  ) { }

  ngOnInit(): void {
    //this.ngxService.start();
    this.loadAllocationHistory();
  }

  private loadAllocationHistory() {
    const rewardSubscription = this.recognitionService.getAllRecognitions()
      .subscribe({
        next: (res: any) => {
          console.log(res);
          if (res.length > 0) {
            res.sort((a: any, b: any) => {
              return new Date(b.date).getTime() - new Date(a.date).getTime();
            });
            this.dataSource = new MatTableDataSource(res);
            this.dataSource.paginator = this.paginator;
              // Defining the filter predicate
              this.dataSource.filterPredicate = (data:any, filter: string) => {
                return data.user.name.toLowerCase().includes(filter);
              };
          } else {
            this.dataSource = new MatTableDataSource([]);
            this.snackbarService.openSnackBar("No Allocation history found.", GlobalConstants.error);
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

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
