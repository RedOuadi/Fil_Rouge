import { Component, OnInit } from '@angular/core';
import { PersonneService } from '../services/personne.service';
import { Personne } from '../models/personne.model';

@Component({
  selector: 'app-coach-management',
  templateUrl: './coach-management.component.html',
  styleUrls: ['./coach-management.component.css']
})
export class CoachManagementComponent implements OnInit {
  coaches: Personne[] = [];

  constructor(private personneService: PersonneService) {}

  ngOnInit(): void {
    this.personneService.getCoaches().subscribe(coaches => this.coaches = coaches);
  }
}
