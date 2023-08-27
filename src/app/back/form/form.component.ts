import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormService } from 'src/app/services/form.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent {

  memberForm: FormGroup = this.formBuilder.group({
    firstName: '',
    lastName: '',
    gender: '',
    dateOfBirth: '',
    address: '',
    email: '',
    phone: '',
    hireDate: '',
    zone: ''
  });
  
  imgURL: string | null = null; // Initialize imgURL to null
  member: any;

  constructor(
    private formBuilder: FormBuilder,
    private formService: FormService,
    private router: Router
  ) {}

  onSubmit(): void {
    const memberData = this.memberForm.value;
    
    if (this.memberForm.valid) {
      this.formService.addMember(memberData).subscribe(
        response => {
          console.log('Member added successfully:', response);
          // Reset the form after successful submission
          this.memberForm.reset();
          // Redirect to member list or other appropriate page
          this.router.navigate(['/members']);
        },
        error => {
          console.error('Error adding member:', error);
        }
      );
    }
  }

  onSelectFile(event: any): void { // Provide a type for the event parameter
    if (event.target.files.length > 0) {
      // Handle file selection and display image
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imgURL = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }
}
