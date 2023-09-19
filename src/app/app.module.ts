import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module'; 
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { LoginComponent } from './back/login/login.component';
import { AllTemplateComponent } from './back/all-template/all-template.component';
import { BackModule } from './back/back.module';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import {  ThemeService } from 'ng2-charts';


import { MatNativeDateModule } from '@angular/material/core';
import { MomentDateModule } from '@angular/material-moment-adapter';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from "@angular/material/form-field";
import { CommonModule } from '@angular/common';
import { ToastrModule } from 'ngx-toastr';


@NgModule({
  declarations: [
    AppComponent,
    AllTemplateComponent,
  ],
  imports: [
    BackModule,
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule,
    NgMultiSelectDropDownModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    BrowserModule, 
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MomentDateModule,
    FormsModule,CommonModule,MatDialogModule,ToastrModule.forRoot(),
  ],
  providers: [ThemeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
