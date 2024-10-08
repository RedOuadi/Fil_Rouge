import { Component, OnInit } from '@angular/core';
import { PersonneService } from '../services/personne.service';
import { Personne } from '../models/personne.model';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  users: Personne[] = [];

  constructor(private personneService: PersonneService) {}

  ngOnInit(): void {
    this.personneService.getUsers().subscribe(users => this.users = users);
  }
}
