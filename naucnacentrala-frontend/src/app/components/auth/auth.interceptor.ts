import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { AuthService } from "./auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private authService: AuthService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {

        if(this.authService.isAuthenticated()) {
            const copiedReq = req.clone({headers: req.headers.set('Content-Type','application/json').append('Authorization', 'Bearer ' + localStorage.getItem('token'))});
            console.log('Authenticated!');
            return next.handle(copiedReq);
        }
        console.log('Not authenticated!');
        return next.handle(req);
    }
}