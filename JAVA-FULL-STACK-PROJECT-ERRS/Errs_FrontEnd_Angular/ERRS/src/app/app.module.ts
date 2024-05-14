import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatIconModule} from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatMenuModule} from '@angular/material/menu';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
//import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialModule } from './shared/material-module';
import { SharedModule } from './shared/shared-module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TopRewardsComponent } from './top-rewards/top-rewards.component';
import { HomeComponent } from './home/home.component';
//import { DashboardComponent } from './dashboard/dashboard.component';
import { AppHeaderComponent } from './layout/header/header.component';
import { FullComponent } from './layout/full/full.component';
import { AppSidebarComponent } from './layout/sidebar/sidebar.component';
//import { MaterialComponentComponent } from './material-component/material-component.component';
import {  HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { SignupComponent } from './signup/signup.component';
import { NgxUiLoaderConfig, NgxUiLoaderModule, SPINNER } from 'ngx-ui-loader';
import { LoginComponent } from './login/login.component';
import { TokenInterceptorInterceptor } from './services/token-interceptor.interceptor';
import { ConfirmationComponent } from './material-component/dialog/confirmation/confirmation.component';


const ngxUiLoaderConfig: NgxUiLoaderConfig = {
  text : "Loading...",
  textColor: "#FFFFF",
  textPosition: "center-center",
  bgsColor:"#7b1fa2",
  fgsColor:"a7b1fa2",
  fgsType:SPINNER.squareJellyBox,
  fgsSize:100,
  hasProgressBar:false
}

@NgModule({
  declarations: [
    AppComponent,
    TopRewardsComponent,
    HomeComponent,
    AppHeaderComponent,
    FullComponent,
    AppSidebarComponent,
    //MaterialComponentComponent,
    SignupComponent,
    LoginComponent,
    ConfirmationComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatIconModule,
    FormsModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatToolbarModule,
    MatSidenavModule,
    MaterialModule,
    SharedModule ,
    HttpClientModule,
    NgxUiLoaderModule.forRoot(ngxUiLoaderConfig)
    
  ],
  providers: [
    HttpClientModule,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorInterceptor,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
