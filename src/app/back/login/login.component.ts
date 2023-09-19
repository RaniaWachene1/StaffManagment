import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { StaffService } from 'src/app/services/staff.service';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  
  loginForm: FormGroup = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
   
  });
  username: string = '';
  password: string = '';
  loginError: boolean = false;

  constructor(private authService: AuthService,  private formBuilder: FormBuilder,
    private service:StaffService, private router: Router,private toastr: ToastrService) {}

  onSubmit() {
    this.authService.login(this.username, this.password).subscribe((response:any)=>{
           
        if (response) {
          
        
          console.log('Navigating to dashboard...');
          this.router.navigate(['/dashboard']);
          this.toastr.success('Login Successful');}
    },(error:any)=>{
     
      this.toastr.error('Incorrect username or password  !');
    })
   
        
        
  }
  applyValidationStyles() {
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach((form: any) => {
      form.addEventListener('submit', (event: Event) => {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      });
    });
  }
  ngOnInit() {
    this.applyValidationStyles();
  }
  
}
