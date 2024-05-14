import { Component } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { RecognitionService } from 'src/app/services/recognition.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-manage-recognition',
  templateUrl: './manage-recognition.component.html',
  styleUrls: ['./manage-recognition.component.scss']
})
export class ManageRecognitionComponent {

  displayedColumns: string[] = ['select', 'name', 'email', 'contactNumber', 'points', 'id'];
  assignedRecognition: any;
  recognitionId!: number;
  dataSource: any;
  responseMessage: any;
  selection = new SelectionModel<any>(true, []);



  constructor(private ngxService: NgxUiLoaderService, private userService: UserService,
    private route: ActivatedRoute,
    private snackbarService: SnackbarService,
    private recognitionService: RecognitionService) { }


  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
    this.route.params.subscribe(params => {
      this.recognitionId = params['recognitionId'];
    })

  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  tableData() {
    this.userService.getUsers().subscribe({
      next: (response: any) => {
        console.log('Response data:', response);
        this.ngxService.stop();
        response.sort((a: { id: number; }, b: { id: number; }) => a.id - b.id);
        this.dataSource = new MatTableDataSource(response);


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

  onSubmit(employeeId: number) {
    console.log('employeeId:', employeeId);
    this.assignedRecognition = {
      date: new Date(),
      user: { userId: employeeId },
      recognition: { recognitionId: this.recognitionId }
    };
    console.log(this.assignedRecognition);

    this.recognitionService.assignRecognition(this.assignedRecognition).subscribe({
      next: (response: any) => {
        console.log(response);
        this.tableData();
        // Showing success message in snackbar
        this.snackbarService.openSnackBar("Successfully assigned recognition points", 'Success');
      },
      error: (error: any) => {
        console.log(error);
        if (error.error?.message) {
          console.log('Error:', error);
          this.responseMessage = error.error.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    });
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach((row: any) => this.selection.select(row));
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  assignPointsToSelectedUsers() {
    // Checking if any users are selected
    if (this.selection.selected.length === 0) {
      this.snackbarService.openSnackBar("No users selected", GlobalConstants.error);
      return;
    }

    // Iterating over selected users
    this.selection.selected.forEach((selectedUser: any) => {
      const assignedRecognition = {
        date: new Date(),
        user: { userId: selectedUser.id }, // 'id' is the property representing user ID
        recognition: { recognitionId: this.recognitionId }
      };

      // Calling the service to assign recognition
      this.recognitionService.assignRecognition(assignedRecognition).subscribe({
        next: (response: any) => {
          console.log(response);
          // Refreshing table data after assigning recognition
          this.tableData();
          // Showing success message in snackbar
          this.snackbarService.openSnackBar("Successfully assigned recognition points", 'Success');
        },
        error: (error: any) => {
          console.log(error);
          if (error.error?.message) {
            console.log('Error:', error);
            this.responseMessage = error.error.message;
          } else {
            this.responseMessage = GlobalConstants.genericError;
          }
          this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
        }
      });
    });

    // Clearing the selection after assigning recognition
    this.selection.clear();
  }



}
