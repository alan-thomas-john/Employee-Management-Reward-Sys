import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardsService {
  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  getDetails(){
    return this.httpClient.get(this.url+"/dashboard/details")
  }

  getLoggedInUserInfo() {
    return this.httpClient.get(this.url + "/dashboard/user-info");
  }
}
