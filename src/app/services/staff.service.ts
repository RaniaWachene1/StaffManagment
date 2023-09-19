import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { StaffArea } from '../interface/staff.interface';
import { catchError } from 'rxjs/operators';

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
  getEntryTime(): Observable<Map<string, number>> {
    return this.httpClient.get<Map<string, number>>('http://localhost:8080/api/formatted-by-firstname').pipe(
      map((data: any) => {
        const transformedData: Map<string, number> = new Map();
        for (const key in data) {
          if (data.hasOwnProperty(key)) {
            transformedData.set(key, data[key].length);
          }
        }
        return transformedData;
      })
    );
  }
  getLeaveTime(): Observable<Map<string, number>> {
    return this.httpClient.get<Map<string, number>>('http://localhost:8080/api/formatted-by-firstname-Out').pipe(
      map((data: any) => {
        const transformedData: Map<string, number> = new Map();
        for (const key in data) {
          if (data.hasOwnProperty(key)) {
            transformedData.set(key, data[key].length);
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

    



  
  private baseUrl = 'http://localhost:8080/api'; 

  getTotalNumberOfVByWeek(): Observable<number> {
    const apiUrl = `${this.baseUrl}/totalvisitors`;
    return this.httpClient.get<number>(apiUrl);
  }
  getEarliestArrivalTime() {
    return this.httpClient.get<string>(`${this.baseUrl}/earliest-arrival-time`, {
      responseType: 'text' as 'json',
    });
  }
  getLatestLeavingTimeOut() {
    return this.httpClient.get<string>(`${this.baseUrl}/earliest-leaving-time`, {
      responseType: 'text' as 'json',
    });
  }
  
getTotalNumberOfVisitorsByFirstName(firstName: string): Observable<number> {
  const url = `${this.baseUrl}/totalNumberOfVByFirstName/${firstName}`;
  return this.httpClient.get<number>(url);
}
getTotalNumberOut(): Observable<number> {
  const apiUrl = `${this.baseUrl}/totalStaffOut`;
  return this.httpClient.get<number>(apiUrl);
}

getDistinctFirstNames(): Observable<string[]> {
  return this.httpClient.get<string[]>(`${this.baseUrl}/distinctFirstNames`).pipe(
    map((response: string[]) => {
      // Parse the JSON objects and extract the "FirstName" field
      const names: string[] = response.map((jsonObject) => {
        const parsedObject = JSON.parse(jsonObject);
        return parsedObject.FirstName;
      });
      return names;
    })
  );
}
fetchStayDurationData(): Observable<{ [key: string]: number }> {
  const apiUrl = `${this.baseUrl}/stayDurationByFirstName`;
  return this.httpClient.get<{ [key: string]: number }>(apiUrl);
}

getDistinctAreas(): Observable<string[]> {
  return this.httpClient.get<string[]>(`${this.baseUrl}/distinctAreas`);
}
getDistinctHourT(): Observable<string[]> {
  return this.httpClient.get<string[]>(`${this.baseUrl}/distinctHourT`);
}
getDistinctHour(): Observable<string[]> {
  return this.httpClient.get<string[]>(`${this.baseUrl}/distinctHour`);
}
getDistinctMonth(): Observable<string[]> {
  return this.httpClient.get<string[]>(`${this.baseUrl}/distinctMonth`);
}
getDistinctMonthT(): Observable<string[]> {
  return this.httpClient.get<string[]>(`${this.baseUrl}/distinctMonthT`);
}
getDistinctYear(): Observable<string[]> {
  return this.httpClient.get<string[]>(`${this.baseUrl}/distinctYear`);
}
getDistinctWeek(): Observable<string[]> {
  return this.httpClient.get<string[]>(`${this.baseUrl}/distinctWeek`);
}
updateChartData(
  selectedHour: string,
  selectedWeek: string,
  selectedMonth: string,
  selectedYear: string,
  selectedArea: string,
  selectedHourName: string,
  selectedMonthName: string,
  selectedFirstName: string
): Observable<any> {
  const jwtToken = this.authService.getJwtToken();
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${jwtToken}`
  });

  // Define an empty object to store the query parameters
  const queryParams: any = {};

  // Add filter criteria based on what is selected
  if (selectedHour) {
    queryParams.hour = selectedHour;
  }
  if (selectedWeek) {
    queryParams.week = selectedWeek;
  }
  if (selectedMonth) {
    queryParams.month = selectedMonth;
  }
  if (selectedYear) {
    queryParams.year = selectedYear;
  }
  if (selectedArea) {
    queryParams.area = selectedArea;
  }
  if (selectedHourName) {
    queryParams.hourName = selectedHourName;
  }
  if (selectedMonthName) {
    queryParams.monthName = selectedMonthName;
  }
  if (selectedFirstName) {
    queryParams.firstName = selectedFirstName;
  }

  // Construct the API URL with the query parameters
  const apiUrl = `${this.baseUrl}/filter`;

  console.log('API URL:', apiUrl);
  console.log('Query Parameters:', queryParams);

  return this.httpClient.get<any>(apiUrl, { headers, params: queryParams }).pipe(
    catchError((error) => {
      console.error('HTTP Request Error:', error);
      throw error;
    })
  );
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

getStayDuration(): Observable<Map<string, number>> {
  return this.httpClient
    .get<Map<string, number>>('http://localhost:8080/api/stayDuration')
    .pipe(
      map((data:any) => {
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
getEntryTime1(): Observable<Map<string, number>> {
  return this.httpClient.
  get<Map<string, number>>('http://localhost:8080/api/formatted-by-firstname').
  pipe(
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

}