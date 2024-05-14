import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-manage-employee',
  templateUrl: './manage-employee.component.html',
  styleUrls: ['./manage-employee.component.scss']
})
export class ManageEmployeeComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  displayedColumns: string[] = ['serialNumber', 'name', 'email', 'contactNumber', 'status'];
  dataSource: any;
  responseMessage: any;



  constructor(private ngxService: NgxUiLoaderService,
    private userService: UserService,
    private snackbarService: SnackbarService,
  ) { }

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  // ngAfterViewInit(): void {
  //   // checking if dataSource is initialized before trying to assign the paginator.
  //   if (this.dataSource) {
  //     this.dataSource.paginator = this.paginator;
  //   }
  // }

  tableData() {
    this.userService.getUsers().subscribe({
      next: (response: any) => {
        this.ngxService.stop();
        this.dataSource = new MatTableDataSource(response);
        //  setting the paginator after the dataSource is initialized or updated.
        this.dataSource.paginator = this.paginator;


      }, error: (error: any) => {
        this.ngxService.stop();
        console.log(error);
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
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  getSerialNumber(index: number): number {
    const pageIndex = this.dataSource.paginator.pageIndex;
    const pageSize = this.dataSource.paginator.pageSize;
    return pageIndex * pageSize + index + 1;
  }


  onChange(status: boolean, id: any) {
    this.ngxService.start();
    var data = {
      status: status.toString(),
      id: id
    }
    this.userService.update(data).subscribe({
      next: (response: any) => {
        this.ngxService.stop();
        this.responseMessage = response?.message;
        this.snackbarService.openSnackBar(this.responseMessage, "success");

        // Updating the local data
        const index = this.dataSource.data.findIndex((item: any) => item.id === id);
        if (index !== -1) {
          this.dataSource.data[index].status = status;
          this.dataSource._updateChangeSubscription(); // Refreshing the table
        }
        this.dataSource.paginator = this.paginator;

      }, error: (error: any) => {
        this.ngxService.stop();
        console.log(error);
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
