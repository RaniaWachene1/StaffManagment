import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataset, ChartConfiguration , LabelItem, Color} from 'chart.js';
import { StaffArea } from 'src/app/interface/staff.interface';
import { StaffService } from 'src/app/services/staff.service';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { NativeDateAdapter } from '@angular/material/core';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { FormControl } from '@angular/forms';
import { formatDate } from '@angular/common'; 
import { FormBuilder } from '@angular/forms';


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

  selectedWeek: Date | undefined;
  visitorCountByWeek: number = 0;
  selectedFirstName: string | undefined;
  totalVisitors: number | undefined;
  firstNamesList: string[] = [];
  selectedDate: FormControl = new FormControl();
  dropdownList: { item_id: number; item_text: string; }[] = []; // Define the type explicitly
  selectedItems: { item_id: number; item_text: string; }[] = []; // Define the type explicitly
  dropdownSettings: IDropdownSettings = {};
  totalStaffInStoreAtCurrentTime: number = 0;
  visitorCountByFirstName: number = 0;
  selectedFirstNameControl: FormControl = new FormControl();
  totalNumberOfVByFirstName: number = 0; // Define the property
  stayDurationData: Map<string, number> = new Map();

  public barChartLegend = true;
  public barChartPlugins = [];
 
  public barChartType: ChartType = 'bar';

  public barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [], // Initialize empty labels array
    datasets: [
      { data: [], label: 'Total Attendance by Week' }, // Use the label you want
    ]
  }
  public barChartConfig: ChartConfiguration<'bar'> = {
    type: 'bar',
    data: this.barChartData, // You should ensure that barChartData is defined properly
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


 public pieChartOptions: ChartOptions = {
    responsive: false,
    
  };
  public pieChartLabels: string[] = ['Download Sales', 'In-Store Sales', 'Mail Sales'];
  public pieChartData: ChartDataset[] = [
    {
      data: [300, 500, 100],
      
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
      ],
      borderColor: [
        'rgba(255,99,132,1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)'
      ],
      borderWidth: 1,
      fill: false
    },
  ];
  public pieChartType: ChartType = 'pie';
  public pieChartLegend = true;
  public pieChartPlugins = [];

 
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
   
  
  constructor(private staffService: StaffService, private formBuilder: FormBuilder) {}
  arr:any[]=[]
  
    ngOnInit(): void {
     
       
  

      this.getTotalAttendByWeek();
      console.log("data "+this.getTotalAttendByWeek())
      this.staffService.getDistinctFirstNames().subscribe(names => {
        this.firstNamesList = names;});
        this.fetchStayDurationData();
      this.getTotalNumberOfEmployeesByArea();
      this.getTotalNumberOfEmployees();
      this.selectedFirstName = '';
      this.updateTotalVisitors();
      
      this.dropdownSettings = {
        singleSelection: false,
        idField: 'item_id',
        textField: 'item_text',
        selectAllText: 'Select All',
        unSelectAllText: 'UnSelect All',
        itemsShowLimit: 3,
        allowSearchFilter: true
      };
  
      // Initialize dropdownList with data
      this.dropdownList = [
        { item_id: 1, item_text: 'Mumbai' },
        // Add more items as needed
      ];
      this.selectedItems = [];
      this.selectedItems = [];
    
      
      
    



    }

    onSelectedWeekChange(): void {
      if (this.selectedWeekControl.value) {
        const formattedWeek = formatDate(this.selectedWeekControl.value, 'yyyy-MM-dd', 'en-US');
        this.getVisitorCountByWeek(formattedWeek);
      }}
   
    getTotalNumberOfEmployeesByArea() {
      this.doughnutChartDatasets = [];
      this.doughnutChartLabels = [];
    
      this.staffService.getTotalNumberOfEmployeesByArea().subscribe(
        (data: Map<string, number>) => {
          this.doughnutChartLabels = Array.from(data.keys());
          this.doughnutChartDatasets = [
            {
              data: Array.from(data.values()),
              backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(255, 159, 64, 0.2)'
              ],
            },
          ];
        },
        (error) => {
          console.error('Error fetching employees by area:', error);
        }
      );
    }
    onItemSelect(item: any) {
      console.log(item);
    }
  
    // Method to handle select all event
    onSelectAll(items: any) {
      console.log(items);
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
    getTotalAttendByWeek() {
      this.barChartData.datasets = []; // Clear the datasets array
      this.barChartData.labels = [];   // Clear the labels array
  
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
    getVisitorCountByWeek(week: string): void {
      if (week) {
        this.staffService.getTotalNumberOfVByWeek(week).subscribe(
          (count: number) => {
            this.visitorCountByWeek = count; // Update the visitorCountByWeek with the fetched count
          },
          (error) => {
            console.error('Error fetching visitor count:', error);
          }
        );
      }
    }

    updateTotalVisitors() {
      if (this.selectedFirstName) {
        this.staffService.getTotalNumberOfVisitorsByFirstName(this.selectedFirstName)
          .subscribe(
            total => {
              console.log('Total visitors:', total);
              this.totalNumberOfVByFirstName = total;
            },
            error => {
              console.error('Error fetching total visitors:', error);
            }
          );
      } else {
        this.totalNumberOfVByFirstName = 0;
      }
    }
    
    fetchStayDurationData(): void {
      this.staffService.getDistinctFirstNames().subscribe(firstNames => {
        this.lineChartLabels = firstNames;
    
        const chartData: ChartDataset[] = [];
        firstNames.forEach(firstName => {
          this.staffService.fetchStayDurationData(firstName).subscribe(stayDurations => {
            chartData.push({ data: stayDurations, label: firstName });
          });
        });
    
        this.lineChartData = chartData; // Assign the new array to lineChartData
      });
    }
    
  }
  

  
  
  
  
    

  

 
  


  

  
  
 
  

  

