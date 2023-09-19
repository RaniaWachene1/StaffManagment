import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataset, ChartConfiguration , LabelItem, Color} from 'chart.js';
import { StaffArea } from 'src/app/interface/staff.interface';
import { StaffService } from 'src/app/services/staff.service';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NativeDateAdapter } from '@angular/material/core';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { FormControl } from '@angular/forms';
import { formatDate } from '@angular/common'; 
import { FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { catchError, debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { ChangeDetectorRef } from '@angular/core';
import { map, throwError } from 'rxjs';
import { NgZone } from '@angular/core';




export const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'YYYY-MM-DD',
  },
  display: {
    dateInput: 'YYYY-MM-DD',
    monthYearLabel: 'MMM YYYY',
    dateA11yLabel: 'YYYY-MM-DD',
    monthYearA11yLabel: 'MMMM YYYY'
  },
};

@Component({
  selector: 'app-dash',
  templateUrl: './dash.component.html',
  styleUrls: ['./dash.component.css'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ]
})

export class DashComponent implements OnInit {
selectedWeekControl: FormControl = this.formBuilder.control(null);
selectedMonthControl = new FormControl();
selectedYearControl = new FormControl();
selectedAreaControl = new FormControl();
selectedHourNameControl = new FormControl();
selectedMonthNameControl = new FormControl();
selectedWeekC = new FormControl();
selectedHourC= new FormControl();
hourList: string[] = [];
yearList: string[] = [];
areaList: string[] = [];
hourNameList: string[] = [];
monthNameList: string[] = [];
monthList: string[] = [];
weeksList: string[] = [];
  selectedWeek: Date | undefined;
  visitorCountByWeek: number = 0;
  selectedFirstName: string | undefined;
  firstNamesList: string[] = [];

  selectedDate: FormControl = new FormControl();
  dropdownList: { item_id: number; item_text: string; }[] = [];
  selectedItems: { item_id: number; item_text: string; }[] = []; 
  dropdownSettings: IDropdownSettings = {};
  totalStaffInStoreAtCurrentTime: number = 0;
  visitorCountByFirstName: number = 0;
  selectedFirstNameControl: FormControl = new FormControl();
  totalNumberOfVByFirstName: number = 0; 
  stayDurationData: Map<string, number> = new Map();
  public barChartLegend = true;
  public barChartPlugins = [];
 
  public barChartType: ChartType = 'bar';

  public barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: [
      { data: [], label: 'Total Attendance by Week' }, 
    ]
  }
  public barChartConfig: ChartConfiguration<'bar'> = {
    type: 'bar',
    data: this.barChartData, 
    options: {
      scales: {
        x: {
          type: 'time',
          time: {
            unit: 'day'
          },
          
        },
        y: {
          beginAtZero: true
        }
      }
    }
  };
  public pieChartOptions: ChartOptions<'pie'> = {
    responsive: false,
  };


  public pieChartLegend = true;
  public pieChartPlugins = [];
  
  public pieChartLabels: string[] = [];
  public pieChartDatasets = [ {
    data: [ 300, 500, 100 ]
  } ];
  

 
  public lineChartData: ChartDataset[] = [];

  public lineChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

  public lineChartColors: Color[] = [
    
  ];
  
  

  public lineChartLegend = true;
  public lineChartType: ChartType = 'line';


  public doughnutChartLabels: string[] = [ ];
  public doughnutChartDatasets: ChartConfiguration<'doughnut'>['data']['datasets'] = [
      { data: [ ], label: '' },
    ];

  public doughnutChartOptions: ChartConfiguration<'doughnut'>['options'] = {
    responsive: false
  };
  earliestArrivalTime: string | undefined;
  earliestLeavingTime: string | undefined;

updateBarChartOptions(xAxisTitle: string, yAxisTitle: string) {
  this.barChartConfig.options = {
    responsive: true,
    scales: {
      x: {
        title: { display: true, text: xAxisTitle },
        type: 'time',
        time: {
          unit: 'day'
        },
      },
      y: {
        title: { display: true, text: yAxisTitle },
        beginAtZero: true,
      },
    },
  };
}


  public barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    scales: {
      x: { title: { display: true, text: 'Week' } },
      y: { title: { display: true, text: 'Total Attendance' } },
    },
  };

   totalEmployees: number | null = null;
    employeesByArea: Array<StaffArea> = [];
  lineChartLabels: string[] | undefined;
  distinctHours$: any;
  totalVisitors: number | undefined;
  staffOut: number | undefined;
  leaving: number | null = null;
  Time: string | null = null; 


 
  constructor(private staffService: StaffService, private formBuilder: FormBuilder,
    private authService: AuthService,private zone: NgZone, private router: Router,private cdr: ChangeDetectorRef) {}
  arr:any[]=[]
  
  
  
    ngOnInit(): void {
      this.EntryTimeD();
      this.EntryTime();
    this.StayDuration();
      this.fetchTotalVisitors();
      this.fetchStayDurationData();
      this.getTotalAttendByWeek();
      this.fetchStayDurationData();
      this.fetchStayDurationLine();
      this.getTotalNumberOfEmployees();
      this.selectedFirstName = '';
      this.staffService.getTotalNumberOfVByWeek().subscribe(
        (total: number) => {
          this.totalVisitors = total;
        },
        (error) => {
          console.error('Error fetching total visitors:', error);
        }
      );
      this.staffService.getTotalNumberOut().subscribe(
        (totalO: number) => {
          this.staffOut = totalO;
        },
        (error) => {
          console.error('Error fetching total visitors:', error);
        }
      );    
      this.staffService.getEarliestArrivalTime().subscribe(
        (response) => {
          this.earliestArrivalTime = response; 
        },
        (error) => {
          console.error('Error fetching earliest arrival time:', error);
        }
      );
      this.staffService.getLatestLeavingTimeOut().subscribe(
        (response) => {
          this.earliestLeavingTime = response; 
        },
        (error) => {
          console.error('Error fetching earliest arrival time:', error);
        }
      );
    
 
      this.selectedItems = [];
      this.applyFilters();
    
    
      this.loadDistinctAreas();
      this.staffService.getDistinctAreas().subscribe((areas) => {
        this.areaList = areas;
      });
      this.staffService.getDistinctHour().subscribe((hours) => {
        this.hourNameList = hours;
      });
      this.staffService.getDistinctMonth().subscribe((months) => {
        this.monthNameList = months;
      });
      this.staffService.getDistinctYear().subscribe((years) => {
        this.yearList = years;
      });
      this.staffService.getDistinctMonthT().subscribe((monthsT) => {
        this.monthList = monthsT;
      });
      this.staffService.getDistinctWeek().subscribe((weeks) => {
        this.weeksList = weeks;
      });
      this.staffService.getDistinctHourT().subscribe((hours) => {
        this.hourList = hours;
      });
 
      this.staffService.getDistinctFirstNames().subscribe(names => {
        this.firstNamesList = names;});
       
      
      this.dropdownSettings = {
        singleSelection: false,
        idField: 'item_id',
        textField: 'item_text',
        selectAllText: 'Select All',
        unSelectAllText: 'UnSelect All',
        itemsShowLimit: 3,
        allowSearchFilter: true
      };
  
      this.dropdownList = [
        { item_id: 1, item_text: 'Mumbai' },
      ];
      this.selectedItems = [];
      this.selectedItems = [];

    }

    onItemSelect(item: any) {
      console.log(item);
    }
 
    onSelectAll(items: any) {
      console.log(items);
    }
    // Define an array of method names
selectedMethod: string | undefined;
selectedMethodD: string | undefined;
selectedMethodL: string | undefined;
selectedMethodPie: string | undefined ;
methodDList: string[] = ['Stay Duration', 'Attendance', 'Entry', 'Exit','Distribution of employees'];
methodList: string[] = ['Stay Duration', 'Attendance', 'Entry', 'Exit','Distribution of employees'];
methodLineList: string[] = ['Stay Duration', 'Attendance', 'Entry', 'Exit','Distribution of employees'];

methodPieList: string[] = ['Stay Duration', 'Attendance', 'Entry', 'Exit','Distribution of employees'];





updateBarChartData(): void {
  this.barChartData.datasets = []; // Clear the datasets array
  this.barChartData.labels = [];   // Clear the labels array

  this.staffService.fetchStayDurationData().subscribe(
    (data: { [key: string]: number }) => {
      this.barChartData.labels = Object.keys(data);
      this.barChartData.datasets = [
        {
          data: Object.values(data),
          label: 'Stay Duration',
        },
      ];
    },
    (error) => {
      console.error('Error fetching:', error);
    }
  );
}





public updatePieChartData(data: Map<string, number>): void {
  const dataObject: { [key: string]: number } = {};

  // Convert Map to object
  data.forEach((value, key) => {
    dataObject[key] = value;
  });

  const keys = Object.keys(dataObject);
  const values = Object.values(dataObject);

  this.pieChartLabels = keys;
  this.pieChartDatasets[0].data = values;
}


    StayDuration() {
      this.barChartData.datasets = []; // Clear the datasets array
      this.barChartData.labels = [];   // Clear the labels array
    
      this.staffService.fetchStayDurationData().subscribe(
        (data: { [key: string]: number }) => { // Change the parameter type
          this.barChartData.labels = Object.keys(data);
          this.barChartData.datasets = [
            {
              data: Object.values(data),
              label: 'Total Attendance',
            },
          ];
        },
        (error) => {
          console.error('Error fetching:', error);
        }
      );
    }

    getTotalNumberOfEmployees() {
      this.staffService.getTotalNumberOfEmployees().subscribe(
        (total: number) => {
          this.totalEmployees = total;
        },
        (error) => {
          console.error('Error fetching total employees:', error);
        }
      );
    }
   
  
    private fetchTotalVisitors() {
      this.staffService.getTotalNumberOfVByWeek().subscribe(
        (total: number) => {
          this.totalVisitors = total;
        },
        (error) => {
          console.error('Error fetching total visitors:', error);
        }
      );
    }
 
    logout() {
      this.authService.logout().subscribe(
        () => {
          // Clear any local user data or tokens here
          // For example, you can remove user data from local storage
          localStorage.removeItem('currentUser');
  
          // Redirect to the login page or another appropriate page
          this.router.navigate(['/login']);
        },
        error => {
          console.error('Logout error:', error);
          // Handle error, show a message, etc.
        }
      );
    }

    loadDistinctAreas() {
      this.staffService.getDistinctAreas().subscribe(
        areas => {
          this.areaList = areas;
        },
        error => {
          console.error('Error loading distinct areas:', error);
        }
      );
    }
    
    applyFilters(): void {
      const selectedHour = this.selectedHourC.value;
      const selectedWeek = this.selectedWeekC.value;
      const selectedMonth = this.selectedMonthControl.value;
      const selectedYear = this.selectedYearControl.value;
      const selectedArea = this.selectedAreaControl.value;
      const selectedHourName = this.selectedHourNameControl.value;
      const selectedMonthName = this.selectedMonthNameControl.value;
      const selectedFirstName = this.selectedFirstNameControl.value;
    
      console.log('Filter Criteria:', {
        selectedHour,
        selectedWeek,
        selectedMonth,
        selectedYear,
        selectedArea,
        selectedHourName,
        selectedMonthName,
        selectedFirstName,
      });
    
      // Make API call to update the bar chart and card data
      this.staffService.updateChartData(
        selectedHour,
        selectedWeek,
        selectedMonth,
        selectedYear,
        selectedArea,
        selectedHourName,
        selectedMonthName,
        selectedFirstName
      ).subscribe(
        (updatedData: any) => {
          console.log('Updated Data (API Response):', updatedData);
          if (!updatedData) {
            console.log('API Response is empty.');
            return; 
          }
          this.earliestArrivalTime= updatedData.Enter;
          this.earliestLeavingTime= updatedData.Exit;
          this.totalVisitors = updatedData.longResult;
          this.staffOut = updatedData.staffOut;
      
          this.lineChartLabels = Object.keys(updatedData.mapResult);
            this.lineChartData[0].data =Object.values(updatedData.mapResult);
            this.pieChartLabels = Object.keys(updatedData.mapResult);
            this.pieChartDatasets[0].data =Object.values(updatedData.mapResult);
            this.doughnutChartLabels = Object.keys(updatedData.mapResult);
            this.doughnutChartDatasets= Object.values(updatedData.mapResult);
          if (this.selectedMethod === 'Attendance') {
            this.barChartData.datasets[0].data = Object.values(updatedData.mapResult);
            this.barChartData.labels = Object.keys(updatedData.mapResult);
            this.lineChartLabels = Object.keys(updatedData.mapResult);
            this.lineChartData[0].data =Object.values(updatedData.mapResult);
            this.totalVisitors = updatedData.longResult;
            this.staffOut = updatedData.staffOut;
            this.earliestArrivalTime= updatedData.entryTime;
            this.pieChartLabels = Object.keys(updatedData.mapResult);
          this.pieChartDatasets[0].data =Object.values(updatedData.mapResult);
          this.doughnutChartLabels = Object.keys(updatedData.mapResult);
          this.doughnutChartDatasets[0].data= Object.values(updatedData.mapResult);
          } else if (this.selectedMethod === 'StayDuration') {
            if (updatedData.stay) {
              const stayDataArray = Object.entries(updatedData.stay).map(([label, data]) => ({
                label,
                data,
              }));
            
              this.totalVisitors = updatedData.longResult;
              this.staffOut = updatedData.staffOut;
              this.earliestArrivalTime= updatedData.entryTime;
              this.barChartData.labels = stayDataArray.map(item => item.label);
              this.barChartData.datasets[0].data = stayDataArray.map(item => item.data as number);
            } else {
              console.error('updatedData.stay is undefined or null');
            }
            
            if (updatedData.stay) {
              const pieChartData = Object.entries(updatedData.stay).map(([label, data]) => ({
                label,
                data,
              }));
          
              this.pieChartDatasets = [{ data: pieChartData.map(item => item.data as number) }];
              this.pieChartLabels = pieChartData.map(item => item.label);
              
              // ...
            } else {
              // Handle the case when updatedData.stay is undefined or null
              console.error('updatedData.stay is undefined or null');
              // You can choose to set default values for pie chart data or perform other actions here
            }
          }
          
          console.log('API Response:', updatedData);
          this.cdr.detectChanges();
        },
        (error: any) => {
          console.error('HTTP Request Error:', error);
        }
      );
    }
    
clearFilters(): void {
  // Reset all filter controls to their initial values or null
  this.selectedHourC.setValue(null);
  this.selectedWeekC.setValue(null);
  this.selectedMonthControl.setValue(null);
  this.selectedYearControl.setValue(null);
  this.selectedAreaControl.setValue(null);
  this.selectedHourNameControl.setValue(null);
  this.selectedMonthNameControl.setValue(null);
  this.selectedFirstNameControl.setValue(null);


  this.selectedItems = [];
  this.getTotalAttendByWeek();
  this.fetchTotalVisitors();
  this.fetchStayDurationData();
  this.StayDurationPie();
  this.applyFilters();
  this.DistributionLine();
  this.ExitD();
  this.staffService.getTotalNumberOut().subscribe(
    (totalO: number) => {
      this.staffOut = totalO;
    },
    (error) => {
      console.error('Error fetching total visitors:', error);
    }
  );    
  this.staffService.getEarliestArrivalTime().subscribe(
    (response) => {
      this.earliestArrivalTime = response; 
    },
    (error) => {
      console.error('Error fetching earliest arrival time:', error);
    }
  );
  this.staffService.getLatestLeavingTimeOut().subscribe(
    (response) => {
      this.earliestLeavingTime = response; 
    },
    (error) => {
      console.error('Error fetching earliest arrival time:', error);
    }
  );
 
}

 // Line chart   
  
 applyChartLine(): void {
  if (this.selectedMethodL === 'Stay Duration') {
    this.fetchStayDurationLine();
  } else if (this.selectedMethodL === 'Attendance') {
    this.AttandenceLine();
  } else if (this.selectedMethodL === 'Entry') {
    this.LineEntryTime();
  } else if (this.selectedMethodL === 'Exit') {
    this.LineLeaveTime();
  } else if (this.selectedMethodL === 'Distribution of employees') {
    this.DistributionLine();
  }
}

 fetchStayDurationLine() {
  this.lineChartData = [{ data: [] }];
  this.lineChartLabels = [];

  this.staffService.fetchStayDurationData().subscribe(
    (data: { [key: string]: number }) => {
      const keys = Object.keys(data);
      const values = Object.values(data);

      this.lineChartLabels = keys;
      this.lineChartData[0].data = values;

      // Update the x and y axis titles
      this.updateBarChartOptions('Custom X Axis Title', 'Custom Y Axis Title');

      // Trigger change detection
      this.cdr.detectChanges();
    },
    (error) => {
      console.error('Error fetching pie:', error);
    }
  );
}
DistributionLine() {
  this.lineChartData = [{ data: [] }];
  this.lineChartLabels = [];
  

  this.staffService.getTotalNumberOfEmployeesByArea().subscribe(
    (data: Map<string, number>) => {
      // Convert the Map to an array of key-value pairs
      const dataArray: { key: string; value: number }[] = Array.from(data, ([key, value]) => ({ key, value }));

      // Extract labels and data for chart
      this.lineChartLabels = dataArray.map(item => item.key);
      this.lineChartData = [
        {
          data: dataArray.map(item => item.value),
          label: 'Distribution',
        },
      ];
    },
    (error) => {
      console.error('Error fetching:', error);
    }
  );
}
AttandenceLine() {
  this.lineChartData = [{ data: [] }];
  this.lineChartLabels = []  ;

  this.staffService.getTotalAttendByWeek().subscribe(
   
    
    (data: Map<string, number>) => {
      this.lineChartLabels = Array.from(data.keys());
      this.lineChartData = [
        {
          data: Array.from(data.values()),
          label: 'Total Attendance',
        },
      ];
    },
    (error) => {
      console.error('Error fetching total attendance by week:', error);
    }
  )
}
LineEntryTime() {
  this.lineChartData = [{ data: [] }];
  this.lineChartLabels = [];

  this.staffService.getEntryTime().subscribe(
   
    
    (data: Map<string, number>) => {
      this.lineChartLabels = Array.from(data.keys());
      this.lineChartData = [
        {
          data: Array.from(data.values()),
          label: 'Entry Number',
        },
      ];
    },
    (error) => {
      console.error('Error fetching total attendance by week:', error);
    }
  )
}
LineLeaveTime() {
  this.lineChartData = [{ data: [] }];
  this.lineChartLabels = [];

  this.staffService.getLeaveTime().subscribe(
   
    
    (data: Map<string, number>) => {
      this.lineChartLabels= Array.from(data.keys());
      this.lineChartData = [
        {
          data: Array.from(data.values()),
          label: 'Exit Number',
        },
      ];
    },
    (error) => {
      console.error('Error fetching total attendance by week:', error);
    }
  )
}
 
     // doughnot chart
     applyChartD(): void {
      this.selectedMethod = 'Distribution of employees'; 
      if (this.selectedMethodD === 'Stay Duration') {
        this.StayDurationD();
      } else if (this.selectedMethodD === 'Attendance') {
        this.AttendanceD();
      } else if (this.selectedMethodD === 'Entry') {
        this.EntryTimeD();
      } else if (this.selectedMethodD === 'Exit') {
        this.ExitD();
      } else if (this.selectedMethodD === 'Distribution of employees') {
        this.DistributionD();
      }
      
    }
    ExitD() {
      this.doughnutChartDatasets =[{ data: [] }];
      this.doughnutChartLabels = [];
  
      this.staffService.getLeaveTime().subscribe(
       
        
        (data: Map<string, number>) => {
          this.doughnutChartLabels = Array.from(data.keys());
          this.doughnutChartDatasets= [
            {
              data: Array.from(data.values()),
              label: 'Leave Time',
            },
          ];
        },
        (error) => {
          console.error('Error fetching total attendance by week:', error);
        }
      )
    }
    AttendanceD() {
      this.doughnutChartDatasets =[{ data: [] }];
      this.doughnutChartLabels = [];
  
      this.staffService.getTotalAttendByWeek().subscribe(
       
        
        (data: Map<string, number>) => {
          this.doughnutChartLabels = Array.from(data.keys());
         this.doughnutChartDatasets =[
     
            {
              data: Array.from(data.values()),
              label: 'Total Attendance',
            },
          ];
        },
        (error) => {
          console.error('Error fetching total attendance by week:', error);
        }
      )
    }
    EntryTimeD() {
      console.log('getTotalNumberOfEmployeesByAreaD() called');
      this.doughnutChartDatasets =[{ data: [] }];
      this.doughnutChartLabels = [];
 
    
      this.staffService.getEntryTime().subscribe(
        (data: Map<string, number>) => {
          console.log('Data received:', data); 
          this.doughnutChartLabels = Array.from(data.keys());
          const dataValues = Array.from(data.values());
    
    
          this.doughnutChartDatasets = [
            {
              data: dataValues
              
            },
          ];
          this.cdr.detectChanges();
        },
        (error) => {
          console.error('Error fetching employees by area:', error);
        }
      );
    }
    DistributionD() {
      this.doughnutChartDatasets = [{ data: [] }];
      this.doughnutChartLabels = [];
    
      this.staffService.getTotalNumberOfEmployeesByArea().subscribe(
        (data: Map<string, number>) => {
          this.doughnutChartLabels = Array.from(data.keys());
          this.doughnutChartDatasets = [
            {
              data: Array.from(data.values()),
              
            },
          ];
        },
        (error) => {
          console.error('Error fetching employees by area:', error);
        }
      );
    }

    StayDurationD() {
      this.doughnutChartDatasets = [{ data: [] }];
      this.doughnutChartLabels = [];
 
    
      this.staffService.getStayDuration().subscribe(
        (data: Map<string, number>) => {
          console.log('Data received:', data); 
          this.doughnutChartLabels = Array.from(data.keys());
          const dataValues = Array.from(data.values());
    
    
          this.doughnutChartDatasets = [
            {
              data: dataValues
              
            },
          ];
          this.cdr.detectChanges();
        },
        (error) => {
          console.error('Error fetching employees by area:', error);
        }
      );
    }
    //Pie
    applyChartPie(): void {
      
      if (this.selectedMethodPie === 'Stay Duration') {
        this.StayDurationPie();
      } else if (this.selectedMethodPie === 'Attendance') {
        this.pieStayDurationData();
      }else if (this.selectedMethodPie === 'Entry') {
        this.EntryTime();
      }else if (this.selectedMethodPie === 'Exit') {
        this.LeaveTimePie();
      }
      else if (this.selectedMethodPie === 'Distribution of employees') {
        this.DistrubtionPie();
      }
    }
    DistrubtionPie() {
      this.pieChartDatasets=[{ data: [] }];
      this.pieChartLabels = [];  
    
      this.staffService.getTotalNumberOfEmployeesByArea().subscribe(
        (data: Map<string, number>) => {
          const dataArray: { key: string; value: number }[] = Array.from(data, ([key, value]) => ({ key, value }));
    
          this.pieChartLabels= dataArray.map(item => item.key);
          this.pieChartDatasets = [
            {
              data: dataArray.map(item => item.value),
             
            },
          ];
        },
        (error) => {
          console.error('Error fetching:', error);
        }
      );
    }
    LeaveTimePie() {
      this.pieChartDatasets = [{ data: [] }];
      this.pieChartLabels = []; 
  
      this.staffService.getLeaveTime().subscribe(
       
        
        (data: Map<string, number>) => {
          this.pieChartLabels = Array.from(data.keys());
          this.pieChartDatasets = [
            {
              data: Array.from(data.values()),
              
            },
          ];
        },
        (error) => {
          console.error('Error fetching total attendance by week:', error);
        }
      )
    }
    pieStayDurationData(): void {
      this.pieChartDatasets = [{ data: [] }];
      this.pieChartLabels = [];
    
      // Call the appropriate service method to fetch attendance data
      this.staffService.getTotalAttendByWeek().subscribe(
        (data: Map<string, number>) => {
          console.log('Attendance data:', data); // Log the received data

          const keys = Array.from(data.keys());
          const values = Array.from(data.values());
    
          this.pieChartLabels = keys;
          this.pieChartDatasets[0].data = values;
    
    
          this.cdr.detectChanges();
        },
        (error) => {
          console.error('Error fetching pie:', error);
        }
      );
    }
    EntryTime(): void {
      this.pieChartDatasets = [{ data: [] }];
      this.pieChartLabels = [];
    
      this.staffService.getEntryTime().subscribe(
        (data: Map<string, number>) => {
          console.log('Attendance data:', data); // Log the received data

          const keys = Array.from(data.keys());
          const values = Array.from(data.values());
    
          this.pieChartLabels = keys;
          this.pieChartDatasets[0].data = values;
    
    
          this.cdr.detectChanges();
        },
        (error) => {
          console.error('Error fetching pie:', error);
        }
      );
    }
    
    StayDurationPie() {
      this.pieChartDatasets = [{ data: [] }];
      this.pieChartLabels = [];
    
      this.staffService.fetchStayDurationData().subscribe(
        (data: { [key: string]: number }) => {
          const keys = Object.keys(data);
          const values = Object.values(data);
    
          this.pieChartLabels = keys;
          this.pieChartDatasets[0].data = values;
    
          
    
          // Trigger change detection
          this.cdr.detectChanges();
        },
        (error) => {
          console.error('Error fetching pie:', error);
        }
      );
    }
    //Bar
    applyChart(): void {
      this.getTotalAttendByWeek();
      if (this.selectedMethod === 'Stay Duration') {
        this.fetchStayDurationData();
      } else if (this.selectedMethod === 'Attendance') {
        this.getTotalAttendByWeek();
      } else if (this.selectedMethod === 'Entry') {
        this.getEntryTime();
      } else if (this.selectedMethod === 'Exit') {
        this.getLeaveTime();
      } else if (this.selectedMethod === 'Distribution of employees') {
        this.BarChartTotalNumberOfEmployeesByArea();
      }
    }
    
    fetchStayDurationData() {
      this.staffService.fetchStayDurationData().subscribe(
        (data: { [key: string]: number }) => {
          const keys = Object.keys(data);
          const values = Object.values(data);
    
          this.zone.run(() => {
            this.barChartData.labels = keys;
            this.barChartData.datasets[0].data = values;
    
            // Update the x and y axis titles
            this.updateBarChartOptions('Custom X Axis Title', 'Custom Y Axis Title');
    
            // Trigger change detection
            this.cdr.detectChanges();
          });
        },
        (error) => {
          console.error('Error fetching pie:', error);
        }
      );
    }
    
   
    BarChartTotalNumberOfEmployeesByArea() {
 
    
      this.staffService.getTotalNumberOfEmployeesByArea().subscribe(
        (data: Map<string, number>) => {
          // Convert the Map to an array of key-value pairs
          const dataArray: { key: string; value: number }[] = Array.from(data, ([key, value]) => ({ key, value }));
    
          // Extract labels and data for chart
          this.barChartData.labels = dataArray.map(item => item.key);
          this.barChartData.datasets = [
            {
              data: dataArray.map(item => item.value),
              label: 'Distribution',
            },
          ];
        },
        (error) => {
          console.error('Error fetching:', error);
        }
      );
    }
    getEntryTime() {
  
      this.staffService.getEntryTime().subscribe(
       
        
        (data: Map<string, number>) => {
          this.barChartData.labels = Array.from(data.keys());
          this.barChartData.datasets = [
            {
              data: Array.from(data.values()),
              label: 'Entry Time',
            },
          ];
        },
        (error) => {
          console.error('Error fetching total attendance by week:', error);
        }
      )
    }
    getLeaveTime() {
      this.barChartData.datasets = [{ data: [] }];
      this.barChartData.labels = [];   // Clear the labels array
  
      this.staffService.getLeaveTime().subscribe(
       
        
        (data: Map<string, number>) => {
          this.barChartData.labels = Array.from(data.keys());
          this.barChartData.datasets = [
            {
              data: Array.from(data.values()),
              label: 'Leave Time',
            },
          ];
        },
        (error) => {
          console.error('Error fetching total attendance by week:', error);
        }
      )
    }
    getTotalAttendByWeek() {
     
  
      this.staffService.getTotalAttendByWeek().subscribe(
       
        
        (data: Map<string, number>) => {
          this.barChartData.labels = Array.from(data.keys());
          this.barChartData.datasets = [
            {
              data: Array.from(data.values()),
              label: 'Total Attendance',
            },
          ];
        },
        (error) => {
          console.error('Error fetching total attendance by week:', error);
        }
      )
    }

  }
    
    