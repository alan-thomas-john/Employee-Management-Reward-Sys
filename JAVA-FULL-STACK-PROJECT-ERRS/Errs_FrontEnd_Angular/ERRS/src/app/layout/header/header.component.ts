import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConfirmationComponent } from 'src/app/material-component/dialog/confirmation/confirmation.component';
import { AuthService } from 'src/app/services/auth.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class AppHeaderComponent implements OnInit{

  role:any;
  userDetails: any;

  constructor(private router:Router,
    private dialog:MatDialog,
    private authService:AuthService) {
  }

  ngOnInit(): void {
    this.userDetails = this.authService.getLoggedInUserDetails();
}

  logout(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.data = {
      message:'Logout',
      confirmation:true
    };
    const dialogRef = this.dialog.open(ConfirmationComponent,dialogConfig);
    const sub = dialogRef.componentInstance.onEmitStatusChange.subscribe((response)=>{
      dialogRef.close();
      localStorage.clear();
      this.router.navigate(['/']);
    })
  }
}
