import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ListService {
  private apiUrl = 'http://localhost:8080/api'; // Update with your API URL

  constructor(private http: HttpClient) { }

  listMembers(): Observable<any> {
    const url = `${this.apiUrl}/members/all`;
    return this.http.get(url);
  }

  getMemberById(id: number): Observable<any> {
    const url = `${this.apiUrl}/members/${id}`;
    return this.http.get(url);
  }

  editMemberById(id: number, memberData: any): Observable<any> {
    const url = `${this.apiUrl}/members/edit/${id}`;
    return this.http.put(url, memberData);
  }

  deleteMemberById(id: number): Observable<any> {
    const url = `${this.apiUrl}/members/delete/${id}`;
    return this.http.delete(url);
  }
}
