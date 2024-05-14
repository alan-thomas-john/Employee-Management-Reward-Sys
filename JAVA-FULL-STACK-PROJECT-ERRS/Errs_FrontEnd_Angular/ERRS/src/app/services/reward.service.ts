import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RewardService {

  url = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  add(data: any) {
    return this.httpClient.post(this.url + "/product/add", data, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  update(data: any) {
    return this.httpClient.post(this.url + "/product/update", data, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  getRewards() {
    return this.httpClient.get(this.url + "/product/get");
  }

  updateStatus(data: any) {
    return this.httpClient.post(this.url + "/product/updateStatus", data, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  buyReward(purchase: any) {
    return this.httpClient.post(this.url + "/purchase/order", purchase, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      responseType: 'text'
    });
  }

  getPurchaseHistoryByUserId(userId: number) {
    return this.httpClient.get(this.url + "/purchase/order/" + userId);
  }

  delete(id: any) {
    return this.httpClient.post(this.url + "/product/delete/" + id, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  getRewardsByCategory(id: any) {
    return this.httpClient.get(this.url + "/product/getByCategory/" + id);
  }

  getById(id: any) {
    return this.httpClient.get(this.url + "/product/getById/" + id);
  }
}
