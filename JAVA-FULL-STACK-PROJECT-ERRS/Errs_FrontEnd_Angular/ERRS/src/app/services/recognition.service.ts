import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecognitionService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }


  addRecognition(recognition: any) {
    return this.httpClient.post(this.url+ "/recognition/add",recognition, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }

  getRecognition() {
    return this.httpClient.get(this.url +"/recognition/getRecognition");
  }

  getRecognitionById(recognitionId: number) {
    return this.httpClient.get(this.url + "/recognition/getRecognitionById/"+recognitionId);
    
  }

  editRecognition(recognitionId: number, recognition: any) {
    return this.httpClient.put(this.url + "/recognition/updateRecognition/" + recognitionId, recognition, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }

  assignRecognition(assignedRecognition: any) {
    return this.httpClient.post(this.url + "/RecognitionHandler/assignedRecognition", assignedRecognition, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }

  getRecognitionByUserId(userId: number) {
    return this.httpClient.get(this.url + "/RecognitionHandler/getAssignedRecognition/" + userId);
  }

  deleteRecognition(recognitionId: number) {
    return this.httpClient.delete(this.url + "/recognition/deleteRecognition/" + recognitionId ,{
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }

  getAssignedRecognitionCountByUserId(userId: number) {
    return this.httpClient.get(this.url + "/getAssignedRecognitionByCount/" + userId);
  }

  getAllRecognitions() {
    return this.httpClient.get(this.url + "/RecognitionHandler/getAllRecognitions", {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }

}
