import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashComponent } from './dash/dash.component';
import { NgChartsModule } from 'ng2-charts';
import { Chart} from 'chart.js';
import { registerables} from 'chart.js';
import { DropDownListModule } from '@syncfusion/ej2-angular-dropdowns';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { FormsModule } from '@angular/forms';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatNativeDateModule} from '@angular/material/core';
import { NgxMatSelectSearchModule } from 'ngx-mat-select-search';
import { MatOptionModule } from '@angular/material/core';
import { ReactiveFormsModule } from '@angular/forms';
import { FormComponent } from './form/form.component';
import { LoginComponent } from './login/login.component';
import { MatSelectModule } from '@angular/material/select';
import { ListComponent } from './list/list.component';
import { EditComponent } from './edit/edit.component';



Chart.register(...registerables);
@NgModule({
  declarations: [  DashComponent,FormComponent, LoginComponent, ListComponent, EditComponent  ],
  imports: [
    CommonModule,
     NgChartsModule,
     DropDownListModule,
     NgMultiSelectDropDownModule.forRoot(),
     FormsModule,MatFormFieldModule, MatInputModule, 
     MatDatepickerModule, MatNativeDateModule,NgxMatSelectSearchModule,
   
    MatOptionModule,ReactiveFormsModule,MatSelectModule
  ],
  exports:[ DashComponent , FormComponent, LoginComponent, ListComponent]
})
export class BackModule { }
