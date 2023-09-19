import { Component, OnInit } from '@angular/core';
import { ListService } from 'src/app/services/list.service';
import { Router } from '@angular/router';
import {MatDialogConfig, MatDialogModule} from '@angular/material/dialog';
import { FormComponent } from '../form/form.component';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatDialog } from '@angular/material/dialog';

import { EditComponent } from 'src/app/back/edit/edit.component';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from 'src/app/services/auth.service';

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
    private matDialog: MatDialog,
    private toastr: ToastrService,private authService: AuthService

  ) {}
  

  
    displayedMembers: any[] = []; // Members to display on the current page
    pageSize: number = 5; // Number of members to display per page
    currentPage: number = 1; // Current page number
    totalPages: number = 3; // Total number of pages
    pages: number[] = []; // Array of page numbers for pagination
  
    ngOnInit(): void {
      this.listService.listMembers().subscribe(
        (data: any[]) => {
          this.members = data;
          this.calculatePagination();
        },
        (error) => {
          console.error("Error fetching member data:", error);
        }
      );
    }
  
    calculatePagination(): void {
      this.totalPages = Math.ceil(this.members.length / this.pageSize);
      this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
      this.updateDisplayedMembers();
    }
  
    updateDisplayedMembers(): void {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      this.displayedMembers = this.members.slice(startIndex, endIndex);
    }
  
    goToPage(page: number): void {
      if (page >= 1 && page <= this.totalPages) {
        this.currentPage = page;
        this.updateDisplayedMembers();
      }
    }
  

  getImageUrl(imageName: string): string {
    const imageUrl = `assets/back/images/${imageName}`;
    console.log('Generated Image URL:', imageUrl);
    return imageUrl;
  }
  
  

  deleteMember(member: any): void {
    const confirmDelete = confirm(`Are you sure you want to delete ${member.firstName} ${member.lastName}?`);
    if (confirmDelete) {
      this.listService.deleteMemberById(member.id).subscribe(
        (response) => {
          // Remove the deleted member from the local array
          this.members = this.members.filter(m => m !== member);
          this.toastr.success('Member deleted successfully', 'Success');
        },
        (error) => {
          console.error("Error deleting member:", error);
          this.toastr.error('Failed to delete member', 'Error');
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
  logout() {
    this.authService.logout().subscribe(
      () => {
        // Clear any local user data or tokens here
        // For example, you can remove user data from local storage
        localStorage.removeItem('currentUser');

        // Redirect to the login page or another appropriate page
        this.router.navigate(['/login']);
      },
      error => {
        console.error('Logout error:', error);
        // Handle error, show a message, etc.
      }
    );
  }
  
  
  }
  






