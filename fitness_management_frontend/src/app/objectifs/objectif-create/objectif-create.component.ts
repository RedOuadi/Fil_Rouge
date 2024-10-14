import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ObjectifFitness } from '../../models/objectiffitness.model';
import { ObjectifFitnessService } from '../../services/objectif-fitness.service';

@Component({
  selector: 'app-objectif-create',
  templateUrl: './objectif-create.component.html',
  styleUrls: ['./objectif-create.component.css']
})
export class ObjectifCreateComponent implements OnInit {
  objectif: ObjectifFitness = {
    id: 0,
    type: '',
    description: '',
    dateDebut: new Date(),
    dateFin: new Date(),
    atteint: false, // Default value
    utilisateurId: 0 // This will be set later
  };

  utilisateurId: number | null = null;

  constructor(
    private objectifService: ObjectifFitnessService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const utilisateurIdStr = localStorage.getItem('utilisateurId');
    if (utilisateurIdStr) {
      this.utilisateurId = +utilisateurIdStr;
    }
  }

  createObjectif(): void {
    if (this.utilisateurId) {
      this.objectif.utilisateurId = this.utilisateurId; // Set the utilisateurId
      this.objectifService.createObjectif(this.objectif).subscribe({
        next: (newObjectif) => {
          console.log('Objectif créé:', newObjectif);
          this.router.navigate(['/user/objectif-list']);
        },
        error: (err) => {
          console.error('Erreur lors de la création de l\'objectif', err);
        }
      });
    } else {
      console.error('Impossible de créer l\'objectif. L\'ID de l\'utilisateur est manquant.');
    }
  }

}
