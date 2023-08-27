import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { StaffArea } from '../interface/staff.interface';
import { AuthService } from 'src/app/services/auth.service'; 

@Injectable({
  providedIn: 'root'
})
export class StaffService {
  token:any=""
  constructor(private httpClient: HttpClient, private authService: AuthService) { }


  checking(){
    console.log("token work from stuff",this.token)
  }


  getTotalAttendByWeek(): Observable<Map<string, number>> {

  
    return this.httpClient.get<Map<string, number>>('http://localhost:8080/api/totalAttendByWeek')
    .pipe(
      map((data: any) => {
        const transformedData: Map<string, number> = new Map();
        for (const key in data) {
          if (data.hasOwnProperty(key)) {
            transformedData.set(key, data[key]);
          }
        }
        return transformedData;
      })
    );
  }
  
  
 getTotalStaffInStoreAtCurrentTime(): Observable<number> {
 return this.httpClient.get<number>('http://localhost:8080/api/totalStaffInStoreAtCurrentTime');}
  


  getTotalNumberOfEmployees(): Observable<number> {
  console.log('JWT Token in StaffService:', this.authService.getJwtToken()); 

  const headers = {
    'Authorization': `Bearer ${this.authService.getJwtToken()}`
  };
  
  return this.httpClient.get<number>('http://localhost:8080/api/totalEmployees', { headers });
  
}

    
  getTotalNumberOfEmployeesByArea(): Observable<Map<string, number>> {
    return this.httpClient
      .get<Map<string, number>>('http://localhost:8080/api/totalEmployeesByArea')
      .pipe(map((data: any) => {
        const transformedData: Map<string, number> = new Map();
        for (const key in data) {
          if (data.hasOwnProperty(key)) {
            transformedData.set(key, data[key]);
          }
        }
        return transformedData;
      }));
  }

  
  private baseUrl = 'http://localhost:8080/api'; 

getTotalNumberOfVByWeek(week: string): Observable<number> {
  const apiUrl = `${this.baseUrl}/totalNumberOfVByWeek/${week}`;
  return this.httpClient.get<number>(apiUrl);
}
getTotalNumberOfVisitorsByFirstName(firstName: string): Observable<number> {
  const url = `${this.baseUrl}/totalNumberOfVByFirstName/${firstName}`;
  return this.httpClient.get<number>(url);
}

getDistinctFirstNames() {
  const url = `${this.baseUrl}/distinctFirstNames`; // 
  return this.httpClient.get<string[]>(url);
}
fetchStayDurationData(firstName: string): Observable<any[]> {
  const apiUrl = `${this.baseUrl}/stayDurationByFirstName`; 
  return this.httpClient.get<any[]>(apiUrl);
}
}