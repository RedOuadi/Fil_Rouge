import { Component, OnInit } from '@angular/core';
import {Personne} from "../../models/personne.model";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: Personne[] = [];
  errorMessage: string = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => {
        this.errorMessage = 'Error loading users. Please try again later.';
        console.error('There was an error!', error);
      }
    });
  }

  deleteUser(id: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.users = this.users.filter(user => user.id !== id);
        },
        error: (error) => {
          this.errorMessage = 'Error deleting user. Please try again later.';
          console.error('There was an error!', error);
        }
      });
    }
  }

  getProfileImageUrl(user: Personne): string {
    return user.profileImage?.imageUrl || 'assets/default-profile.png';
  }
}
