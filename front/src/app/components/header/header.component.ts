import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  private loggedUser: string = "";

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.loggedUser = localStorage.getItem("username")
  }

  onLogout() {
    this.authService.logout();
  }

}
