import { Component, OnInit } from '@angular/core';
import { Personne } from '../../models/personne.model';
import { CoachService } from '../../services/coach.service';

@Component({
  selector: 'app-coach-list',
  templateUrl: './coach-list.component.html',
  styleUrls: ['./coach-list.component.css']
})
export class CoachListComponent implements OnInit {
  coaches: Personne[] = [];
  errorMessage: string = '';
  searchTerm: string = '';

  constructor(private coachService: CoachService) {}

  ngOnInit(): void {
    this.loadCoaches();
  }

  loadCoaches(): void {
    this.coachService.getCoaches().subscribe({
      next: (data) => {
        this.coaches = data;
      },
      error: (error) => {
        this.errorMessage = 'Error loading coaches. Please try again later.';
        console.error('There was an error!', error);
      }
    });
  }

  deleteCoach(id: number): void {
    if (confirm('Are you sure you want to delete this coach?')) {
      this.coachService.deleteCoach(id).subscribe({
        next: () => {
          this.coaches = this.coaches.filter(coach => coach.id !== id);
        },
        error: (error) => {
          this.errorMessage = 'Error deleting coach. Please try again later.';
          console.error('There was an error!', error);
        }
      });
    }
  }

  getProfileImageUrl(coach: Personne): string {
    return coach.profileImage?.imageUrl || 'assets/default-profile.png';
  }

  filteredCoaches(): Personne[] {
    return this.coaches.filter(coach =>
      coach.nom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      coach.prenom.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      coach.email.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
