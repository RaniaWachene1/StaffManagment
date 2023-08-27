import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  memberForm: any; // Define memberForm property

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<EditComponent>
  ) {}

  ngOnInit(): void {
    this.memberForm = {
      firstName: this.data.member.firstName,
      lastName: this.data.member.lastName,
      // Add more form fields here
    };
  }

  submitForm(): void {
    // Rest of your submitForm logic
  }

  closeDialog(): void {
    this.dialogRef.close(); // Close the dialog
  }
}
