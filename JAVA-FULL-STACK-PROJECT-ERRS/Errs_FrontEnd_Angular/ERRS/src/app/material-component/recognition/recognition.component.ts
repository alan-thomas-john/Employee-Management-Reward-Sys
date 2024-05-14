import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { RecognitionService } from 'src/app/services/recognition.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-recognition',
  templateUrl: './recognition.component.html',
  styleUrls: ['./recognition.component.scss']
})
export class RecognitionComponent implements OnInit, AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  recognitionList = new MatTableDataSource<any>(); // Using MatTableDataSource



  winningPerfomer!: any;
  displayedColumns: string[] = ['name', 'points', 'edit', 'delete', 'assign'];
  responseMessage: any;


  constructor(private recognitionService: RecognitionService,
    private userService: UserService, private dialog: MatDialog,
    private snackbarService: SnackbarService) {

  }
  ngAfterViewInit() {
    this.recognitionList.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.loadRecognitionList();
    this.userService.getWinningPerfomer().subscribe({
      next: (response: any) => {
        console.log(response);
        this.winningPerfomer = response;
      }, error: (error: any) => {
        console.error('Error fetching winning performers:', error);
      }
    })
  }

  deleteRecognition(recognitionId: number) {
    this.recognitionService.deleteRecognition(recognitionId).subscribe({
      next: (response: any) => {
        console.log(response);
        // Handling successful deletion, such as refreshing the data
        this.loadRecognitionList(); // Reloading recognition list after deletion
        console.log('Recognition deleted successfully');
      },
      error: (error: any) => {
        console.log(error.error?.message);
        if (error.error?.message) {
          console.log('Error:', error);
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    });
  }


  loadRecognitionList(): void {
    this.recognitionService.getRecognition().subscribe({
      next: (response: any) => {
        console.log(response);
        this.recognitionList.data = response;
      },
      error: (error: any) => {
        console.error('Error fetching recognition list:', error);
      }
    });
  }





}
