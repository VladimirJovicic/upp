import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Injectable } from '@angular/core';

import { AuthService } from './auth.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    
    console.log(this.authService.isAuthenticated());
    console.log(this.authService.token);
    console.log(localStorage.getItem('token'));

    if(this.authService.isAuthenticated())
      return true;
    
    alert('Access denied! You must login first in order to access this page.')
    this.router.navigate(['/signin']);
    return false;
  }
}
