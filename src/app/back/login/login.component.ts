import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { StaffService } from 'src/app/services/staff.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  loginError: boolean = false;

  constructor(private authService: AuthService,private service:StaffService, private router: Router) {}

  onSubmit() {
    this.authService.login(this.username, this.password).subscribe((response:any)=>{
           
        if (response) {
          
        
          console.log('Navigating to dashboard...');
          this.router.navigate(['/dashboard']);}
    },(error:any)=>{
      alert(error.message)
      alert(error.error)
    })
   
        
        
  }
  
}
