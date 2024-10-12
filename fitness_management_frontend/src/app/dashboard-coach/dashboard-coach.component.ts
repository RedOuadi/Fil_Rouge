import { Component, OnInit } from '@angular/core';
import { PersonneService } from '../services/personne.service'; // Import the service to access the user details

@Component({
  selector: 'app-dashboard-coach',
  templateUrl: './dashboard-coach.component.html',
  styleUrls: ['./dashboard-coach.component.css']
})
export class DashboardCoachComponent implements OnInit {
  activeSection: string = 'red';

  constructor(private personneService: PersonneService) {}
  coachId: number | null = null;

  ngOnInit(): void {
    const coachIdStr = localStorage.getItem('coachId');
    let coachId: number | null = null;

    if (coachIdStr) {
      coachId = +coachIdStr; // Convert the string ID to a number
    }

    if (!coachId) {
      console.error('Coach ID is missing or invalid');
    } else {
      console.log('Coach ID:', coachId);
      // Now you can use coachId in API requests or other logic
    }
  }

  setActive(section: string) {
    this.activeSection = section;
  }
}
