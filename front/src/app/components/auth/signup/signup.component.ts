import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  scientificAreas: string[] = ['Science', 'Maths', 'Nature'];

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit() {
  }

  onSignup(form: NgForm) {

    console.log(form);

    const newUser = {
      'email' : form.value.email,
      'password' : form.value.password,
      'userRole' : form.value.userRole,
      'title' : form.value.title,
      'firstName' : form.value.firstName,
      'lastName' : form.value.lastName,
      'city' : form.value.city,
      'state' : form.value.state,
      'scientificAreas' : []
    };

    for(var s of this.scientificAreas)
    {
      if(form.value[s])
        newUser['scientificAreas'].push(s);
    }

    console.log(newUser);

    this.authService.signupUser(JSON.stringify(newUser))
    .subscribe(
    data => {
      if(data === 'true')
        this.router.navigate(['/signin']);
    },
    error => {
      alert("Something went wrong! Please reenter your data.");
      form.reset();
    });
  }

}
