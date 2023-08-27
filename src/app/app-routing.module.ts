import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './back/login/login.component';
import { DashComponent } from './back/dash/dash.component';
import { AllTemplateComponent } from './back/all-template/all-template.component';
import { FormComponent } from './back/form/form.component';
import { ListComponent } from './back/list/list.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent }, 
  {
    path: '',
    component: AllTemplateComponent,
    children: [
      {
        path: 'dashboard',
        component: DashComponent,
      }
    ]
  },
  {
    path: '',
    component: AllTemplateComponent,
    children: [
      {
        path: 'form',
        component: FormComponent,
      }
    ]
  },
  {
    path: '',
    component: AllTemplateComponent,
    children: [
      {
        path: 'list',
        component: ListComponent,
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
