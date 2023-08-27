import { Component, OnInit } from '@angular/core';
import { ListService } from 'src/app/services/list.service';
import { Router } from '@angular/router';
import {MatDialogConfig, MatDialogModule} from '@angular/material/dialog';
import { FormComponent } from '../form/form.component';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';

import { EditComponent } from 'src/app/back/edit/edit.component';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  members: any[] = [];

  constructor(
    private listService: ListService,
    private router: Router,
    private matDialog: MatDialog // Inject MatDialog
  ) {}
  


  ngOnInit(): void {
    this.listService.listMembers().subscribe(
      (data: any[]) => {
        this.members = data;
      },
      (error) => {
        console.error("Error fetching member data:", error);
      }
    );
  }
  
  deleteMember(member: any): void {
    const confirmDelete = confirm(`Are you sure you want to delete ${member.firstName} ${member.lastName}?`);
    if (confirmDelete) {
      this.listService.deleteMemberById(member.id).subscribe(
        (response) => {
          // Remove the deleted member from the local array
          this.members = this.members.filter(m => m !== member);
          console.log(`Deleted ${member.firstName} ${member.lastName}`);
        },
        (error) => {
          console.error("Error deleting member:", error);
        }
      );
    }
  }
  editMember(member: any): void {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = '50%';
  
    // Open the dialog using the MatDialog instance
    const dialogRef = this.matDialog.open(EditComponent, dialogConfig);
  
    // Pass data to the dialog component
    dialogRef.componentInstance.data = { member }; // Wrap member in an object
  
    dialogRef.afterClosed().subscribe((result: any) => {
      if (result) {
        console.log('Dialog closed with result:', result);
        // Update member data or perform other actions here
      }
    });
  }
  
  
  
  }
  






