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
  
}
