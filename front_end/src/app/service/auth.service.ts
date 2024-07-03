import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { AuthData } from '../interface/auth-data.interface';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.authApi;
  private jwtHelper = new JwtHelperService();

  private authSub = new BehaviorSubject<AuthData | null>(null);
  user$ = this.authSub.asObservable();
  private timeOut!: any;

  constructor(private http: HttpClient, private router:Router) { }

  login(data: {codiceIstituto: string, email: string, password: string}) {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post<AuthData>(`${this.apiUrl}/login`, data, { headers: headers}).pipe(
      tap(async (data) => {
        this.authSub.next(data);
        localStorage.setItem('user', JSON.stringify(data))
        this.router.navigate(["/dashboard/home"])
      }),
      catchError(this.errors)
    )
  }

  logout() {
    this.authSub.next(null);
    localStorage.removeItem('user');
    this.router.navigate(["/"])
  }

  async restore() {
    const userJson = localStorage.getItem('user')
    if (!userJson) {
      return
    }

    const user:AuthData = JSON.parse(userJson);
    this.authSub.next(user);
    this.autoLogout(user);
  }

  private autoLogout(data:AuthData) {
    const dataExp = this.jwtHelper.getTokenExpirationDate(data.accessToken) as Date;
    const msExp = dataExp.getTime() - new Date().getTime();
    this.timeOut = setTimeout(() => {
      this.logout();
    }, msExp)
  }
  
  private errors(err: any) {
      return throwError(err.error.message)
  }
}
