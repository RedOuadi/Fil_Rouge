import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProgrammeEntrainementService } from '../../services/programme-entrainement.service';
import { ProgrammeEntrainement } from '../../models/programmeentrainement.model';
import { CoachService } from '../../services/coach.service';
import { Personne } from '../../models/personne.model';

@Component({
  selector: 'app-programme-list',
  templateUrl: './programme-list.component.html',
  styleUrls: ['./programme-list.component.css']
})
export class ProgrammeListComponent implements OnInit {
  programmes: ProgrammeEntrainement[] = [];
  isLoading = true;
  coach: Personne | null = null;

  constructor(
    private programmeService: ProgrammeEntrainementService,
    private coachService: CoachService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const coachIdStr = localStorage.getItem('coachId');
    let coachId: number | null = null;

    if (coachIdStr) {
      coachId = +coachIdStr;
    }

    if (!coachId) {
      console.error('L\'ID du coach est manquant ou invalide');
    } else {
      this.loadCoachDetails(coachId);
      this.loadProgrammes(coachId);
    }
  }

  // Method to redirect to exercises of a specific programme
  viewExercises(programId: number) {
    console.log('Attempting to navigate to exercises for program:', programId);
    this.router.navigate(['/coach/exercice-list', programId]).then(
      (success) => {
        if (success) {
          console.log('Navigation successful');
        } else {
          console.error('Navigation failed');
        }
      },
      (error) => {
        console.error('Navigation error:', error);
      }
    );
  }

  // Method to load coach details
  loadCoachDetails(coachId: number): void {
    this.coachService.getCoachById(coachId).subscribe({
      next: (data) => {
        this.coach = data; // Store the coach details
        console.log('Détails du coach:', this.coach); // Optional: Log the coach details
      },
      error: (error) => {
        console.error('Erreur lors du chargement des détails du coach', error);
      }
    });
  }

  // Method to load programmes for the coach
  loadProgrammes(coachId: number): void {
    this.programmeService.getProgrammesByCoachId(coachId).subscribe({
      next: (data) => {
        this.programmes = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des programmes', error);
        this.isLoading = false;
      }
    });
  }

  // Method to redirect to create programme page
  redirectToCreateProgramme(): void {
    this.router.navigate(['/coach/programme-create']); // Adjust the route based on your actual configuration
  }

// Method to redirect to update programme page
  redirectToUpdateProgramme(programId: number): void {
    this.router.navigate(['/coach/programme-update', programId]); // Pass the program ID to the update form
  }

  deleteProgramme(programId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce programme ?')) {
      this.programmeService.deleteProgramme(programId).subscribe({
        next: () => {
          console.log('Programme supprimé');
          this.loadProgrammes(this.coach?.id!); // Reload the list after deletion
        },
        error: (err) => {
          console.error('Erreur lors de la suppression du programme', err);
        }
      });
    }
  }
}
