import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard-coach',
  templateUrl: './dashboard-coach.component.html',
  styleUrls: ['./dashboard-coach.component.css']
})
export class DashboardCoachComponent {

  activeSection: string ='red' ;


  setActive(section: string) {
    this.activeSection = section;
  }


}
