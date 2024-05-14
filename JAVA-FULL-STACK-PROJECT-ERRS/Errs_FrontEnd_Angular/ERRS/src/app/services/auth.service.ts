import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private router:Router) { }

  public isAuthenticated():boolean{
    const token = localStorage.getItem('token');
    if(!token){
      this.router.navigate(['/']);
      return false;
    }
    else{
      return true;
    }
  }

  public getLoggedInUserDetails(): any {
    const token = localStorage.getItem('token');
    
    if (token) {
      return this.jwtHelper.decodeToken(token);
    }

    return {
      sub: 'guest',
      role: 'guest',
    };
  }
 
}
