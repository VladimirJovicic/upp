import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit() {
  }

  onSignin(form: NgForm) {

    const email = form.value.email;
    const password = form.value.password;

    this.authService.signinUser(email, password)
    .subscribe(
      data => {
        
        let jwt = data['token'];
        let jwtData = jwt.split('.')[1];
        let decodedJwtJsonData = window.atob(jwtData);
        let decodedJwtData = JSON.parse(decodedJwtJsonData);

        this.authService.currentlyLoggedInUser = decodedJwtData.sub;
        this.authService.token = data['token'];

        localStorage.setItem('token', data['token']);
        this.router.navigate(['/magazines']);
      },
      error => {
        console.log(error);
        alert('Problem with your login credentials, please try again!');
      }
    );
  }

}
