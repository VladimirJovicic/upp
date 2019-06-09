import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class TasksService {

  url : string = 'http://localhost:8080/api/task';

  constructor(private httpClient: HttpClient) { }

  getAll() {
    return this.httpClient.get(this.url + '/getAll');
  }

  getFormFromTask(taskId: string): any {
    return this.httpClient.get(this.url + '/' + taskId);
  }

  executeTask(formValue : any, taskId: string): any {
    return this.httpClient.post(this.url + '/executeTask/' + taskId, formValue);
  }

  executeTaskReviewers(formValue : any, taskId: string): any {
    return this.httpClient.post(this.url + '/executeTaskReviewers/' + taskId, formValue);
  }

  getScientificPaper(taskId: string) {
    return this.httpClient.get(this.url + '/getScientificPaper/'+ taskId);
  }

  getBadFormattingMessage(taskId: string) {
    return this.httpClient.get(this.url + '/getBadFormattingMessage/'+ taskId);
  }

  getReviews(taskId: string){
    return this.httpClient.get(this.url + '/getReviews/'+ taskId);
  }

  getBigOrSmallChangeFeedback(taskId: string) {
    return this.httpClient.get(this.url + '/getBigOrSmallChangeFeedback/'+ taskId);
  }

  getPdfText(taskId: string) {
    return this.httpClient.get(this.url + '/getPdfText/'+ taskId);
  }

  getScientificAreas() {
    return this.httpClient.get(this.url + '/getScientificAreas');
  }

  getReviewers(taskId:string) {
    return this.httpClient.get(this.url + '/getReviewers/'+taskId);
  }

}
