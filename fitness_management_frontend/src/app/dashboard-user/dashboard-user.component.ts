import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-user',
  templateUrl: './dashboard-user.component.html',
  styleUrls: ['./dashboard-user.component.css']
})
export class DashboardUserComponent implements OnInit {
  activeSection: string = 'red';
  userId: number | null = null;

  ngOnInit(): void {
    const userIdStr = localStorage.getItem('userId');
    let userId: number | null = null;

    if (userIdStr) {
      userId = +userIdStr; // Convert the string ID to a number
    }

    if (!userId) {
      console.error('User ID is missing or invalid');
    } else {
      console.log('User ID:', userId);
      // Now you can use userId in API requests or other logic
    }
  }

  setActive(section: string) {
    this.activeSection = section;
  }
}

