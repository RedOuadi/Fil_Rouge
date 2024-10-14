import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProgrammeEntrainement } from '../../models/programmeentrainement.model';
import { ProgrammeEntrainementService } from '../../services/programme-entrainement.service';

@Component({
  selector: 'app-programme-create',
  templateUrl: './programme-create.component.html',
  styleUrls: ['./programme-create.component.css']
})
export class ProgrammeCreateComponent implements OnInit {
  programme: ProgrammeEntrainement = {
    id: 0,
    nom: '',
    description: '',
    coachId: 0, // Will be set later
    exercices: []
  };
  coachId: number | null = null; // To store coach ID from local storage

  constructor(
    private programmeService: ProgrammeEntrainementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const coachIdStr = localStorage.getItem('coachId');
    if (coachIdStr) {
      this.coachId = +coachIdStr;
    }
  }

  createProgramme(): void {
    if (this.coachId) {
      this.programme.coachId = this.coachId; // Set the coach ID
      this.programmeService.createProgramme(this.programme).subscribe({
        next: (newProgramme) => {
          console.log('Programme créé:', newProgramme);
          this.router.navigate(['/coach/programme-list']); // Navigate back to the program list
        },
        error: (err) => {
          console.error('Erreur lors de la création du programme', err);
        }
      });
    } else {
      console.error('Impossible de créer le programme. L\'ID du coach est manquant.');
    }
  }
}
