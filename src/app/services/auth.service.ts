import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

   jwtToken: any; 
  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/signin`, { username, password })
  }
  // .pipe(
  //   finalize(() => {
  //     console.log('Navigating to dashboard...');
  //     this.router.navigate(['/dashboard']);
  //     this.jwtToken = ;
  //     console.log('JWT Token:', this.jwtToken); 
  //   })
  // );
  
  getJwtToken(): string | null {
    return this.jwtToken;
  }
  
}
