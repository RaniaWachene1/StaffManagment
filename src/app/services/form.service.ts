import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FormService {
  private apiUrl = 'http://localhost:8080/api'; // 

  constructor(private http: HttpClient) { }

  addMember(memberData: FormData): Observable<any> {
    const url = `${this.apiUrl}/members`;
    return this.http.post(url, memberData);
  }
  
}
