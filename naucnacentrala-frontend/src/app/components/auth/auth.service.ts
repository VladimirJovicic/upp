import { Router, ActivatedRoute } from '@angular/router';
import { Injectable } from '@angular/core';
import { Response  } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { map } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';

const headers = new HttpHeaders().set(
  'Content-Type','application/json'
);

const jwtHelper = new JwtHelperService();

@Injectable()
export class AuthService {
  token: string;
  currentlyLoggedInUser: string;
  url: string = 'http://localhost:8080/api/authentication';

  constructor(private router: Router,
    private httpClient: HttpClient) {}

  signupUser(newUser: any) {

    return this.httpClient
    .post(this.url + '/signup', newUser, {headers: headers})
    .pipe(map((response : Response) => {
      const data = response.text();
      return data; 
    }));
  }

  signinUser(email: string, password: string) {

    var user = {
      "username" : email,
      "password" : password
    };

    return this.httpClient
    .post(this.url + '/signin', JSON.stringify(user), {headers: headers});
  }

  logout() {
     // TODO LOGOUT FROM SERVER
     localStorage.removeItem('token');
     this.token = null;
     this.currentlyLoggedInUser = null;
     console.log('kraj');
     this.router.navigate(['/signin']);//, {relativeTo: this.route});
  }

  getToken() {
    // firebase.auth().currentUser.getToken()
    //   .then(
    //     (token: string) => this.token = token
    //   );
    // return this.token;
  }

  isAuthenticated() {
    return !jwtHelper.isTokenExpired(localStorage.getItem('token'));
  }

  testMe() {
    return this.httpClient.get('http://localhost:8080/api/magazine/getAll', {headers: headers});
  }

  getAllTasks() {
    return this.httpClient.get('http://localhost:8080/api/task/getAll', {headers: headers});
  }
}
