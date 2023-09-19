import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormService } from 'src/app/services/form.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent {
  selectedImage: File | null = null;
  imgURL: string | null = null; // Initialize imgURL to null

  memberForm: FormGroup = this.formBuilder.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    gender: '',
    birthday: '',
    address: '',
    email: '',
    phone: '',
    hireDate: '',
    zone: ''
  });

  constructor(
    private formBuilder: FormBuilder,
    private formService: FormService,
    private toastr: ToastrService,private authService: AuthService, private router: Router
  ) {}

  onSubmit(): void {
    const memberData = this.memberForm.value;

    if (this.memberForm.valid && this.selectedImage) {
      const formData = new FormData();
      formData.append('firstName', memberData.firstName);
      formData.append('lastName', memberData.lastName);
      formData.append('gender', memberData.gender);
      formData.append('birthday', memberData.birthday);
      formData.append('address', memberData.address);
      formData.append('email', memberData.email);
      formData.append('phone', memberData.phone);
      formData.append('hireDate', memberData.hireDate);
      formData.append('zone', memberData.zone);
      formData.append('image', this.selectedImage);

      this.formService.addMember(formData).subscribe(
        response => {
          this.toastr.success('Member added successfully');
          this.memberForm.reset();
          this.selectedImage = null;
        },
        error => {
          this.toastr.error('Failed to add member');
        }
      );
    }
  }

  onSelectFile(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
      this.selectedImage = target.files[0];
    }
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
  logout() {
    this.authService.logout().subscribe(
      () => {
        
        localStorage.removeItem('currentUser');

        this.router.navigate(['/login']);
      },
      error => {
        console.error('Logout error:', error);
   
      }
    );
  } 
  
  ngOnInit() {
    this.applyValidationStyles();
  }
}
