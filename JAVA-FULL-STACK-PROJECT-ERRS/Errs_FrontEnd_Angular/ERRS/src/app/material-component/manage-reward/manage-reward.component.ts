import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { RewardService } from 'src/app/services/reward.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { RewardComponent } from '../dialog/reward/reward.component';
import { ConfirmationComponent } from '../dialog/confirmation/confirmation.component';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-manage-reward',
  templateUrl: './manage-reward.component.html',
  styleUrls: ['./manage-reward.component.scss']
})
export class ManageRewardComponent implements OnInit{
  @ViewChild(MatPaginator) paginator!: MatPaginator;


  displayedColumns: string[] = ['name','categoryName','description','manufacturer','quantityAvailable','imageThumbnailUrl','termsAndConditions','points','edit']
  dataSource:any;
  //length:any;
  responseMessage:any;

  constructor(private rewardService:RewardService,
    private ngxService:NgxUiLoaderService,
    private dialog:MatDialog,
    private snackbarService:SnackbarService,
    private router:Router){}

    ngOnInit(): void {
      this.ngxService.start();
      this.tableData();

    }

    ngAfterViewInit(): void {
      //  checking if dataSource is initialized before trying to assign the paginator.
      if (this.dataSource) {
        this.dataSource.paginator = this.paginator;
      }
    }

    tableData(){
      this.rewardService.getRewards().subscribe({
        next:(response:any)=>{
          this.ngxService.stop();
          this.dataSource = new MatTableDataSource(response);
            //  setting the paginator after the dataSource is initialized or updated.
        this.dataSource.paginator = this.paginator;
        },error:(error:any)=>{
          this.ngxService.stop();
          console.log(error.error?.message);
          if(error.error?.message){
            this.responseMessage = error.error?.message;
          }
          else{
            this.responseMessage = GlobalConstants.genericError;
          }
          this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
        }
      })
    }

    applyFilter(event:Event){
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }

    handleAddAction(){
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = {
        action: "Add"
      };
      dialogConfig.width = "850px";
      const dialogRef = this.dialog.open(RewardComponent,dialogConfig);
      this.router.events.subscribe(() => {
        dialogRef.close();
      });

      const sub = dialogRef.componentInstance.onAddReward.subscribe((response)=>{
        this.tableData();
      })
    }

    handleEditAction(values:any){
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = {
        action: "Edit",
        data:values
      };
      dialogConfig.width = "850px";
      const dialogRef = this.dialog.open(RewardComponent,dialogConfig);
      this.router.events.subscribe(() => {
        dialogRef.close();
      });

      const sub = dialogRef.componentInstance.onEditReward.subscribe((response)=>{
        this.tableData();
      })
    }

    handleDeleteAction(values:any){
      const dialogConfig = new MatDialogConfig();
      dialogConfig.data = {
        message: 'delete '+values.name+ 'product',
        confirmation:true
      }
      const dialogRef = this.dialog.open(ConfirmationComponent,dialogConfig);
      const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((response)=>{
        this.ngxService.start();
        this.deleteProduct(values.id);
        dialogRef.close();
      })
    }

    deleteProduct(id:any){
      this.rewardService.delete(id).subscribe({
        next:(response:any)=>{
          this.ngxService.stop();
          this.tableData();
          this.responseMessage = response?.message;
          this.snackbarService.openSnackBar(this.responseMessage,"success");
        },error:(error:any)=>{
          this.ngxService.stop();
          console.log(error);
          if(error.error?.message){
            this.responseMessage = error.error?.message;
          }
          else{
            this.responseMessage = GlobalConstants.genericError;
          }
          this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
        }
      })
    }

    onChange(status:any,id:any){
      this.ngxService.start();
      var data = {
        status: status.toString(),
        id:id
      }

      this.rewardService.updateStatus(data).subscribe({
        next:(response:any)=>{
          this.ngxService.stop();
          this.responseMessage = response?.message;
          this.snackbarService.openSnackBar(this.responseMessage,"success");
        },error:(error:any)=>{
          this.ngxService.stop();
          console.log(error);
          if(error.error?.message){
            this.responseMessage = error.error?.message;
          }
          else{
            this.responseMessage = GlobalConstants.genericError;
          }
          this.snackbarService.openSnackBar(this.responseMessage,GlobalConstants.error);
        }
      })
    }
}
