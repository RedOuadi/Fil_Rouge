import { Component, OnInit } from '@angular/core';
import { PersonneService } from '../services/personne.service';

@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.css']
})
export class DashboardAdminComponent implements OnInit {
  totalUsers: number = 0;
  totalCoaches: number = 0;

  activeSection: string ='red' ;

  constructor(private personneService: PersonneService) {}

  ngOnInit(): void {
    this.loadCounts();
  }
  setActive(section: string) {
    this.activeSection = section;
  }

  private loadCounts() {
    this.personneService.getTotalUsers().subscribe(count => this.totalUsers = count);
    this.personneService.getTotalCoaches().subscribe(count => this.totalCoaches = count);
  }
}
