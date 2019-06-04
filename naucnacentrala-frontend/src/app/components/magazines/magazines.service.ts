import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable()
export class MagazinesService {

  url : string = 'http://localhost:8080/api/magazine';

  constructor(private httpClient: HttpClient) { }

  getAll() {
    return this.httpClient.get(this.url + '/getAll');
  }

  startPublishProcess(magazineId : string) {
    return this.httpClient.get(this.url + '/' + magazineId);
  }
  
}
