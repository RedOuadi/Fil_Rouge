import { Component, OnInit } from '@angular/core';
import { ObjectifFitnessService } from '../../services/objectif-fitness.service';
import { ObjectifFitness } from '../../models/objectiffitness.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-objectif-list',
  templateUrl: './objectif-list.component.html',
  styleUrls: ['./objectif-list.component.css']
})
export class ObjectifListComponent implements OnInit {
  objectifs: ObjectifFitness[] = [];
  isLoading = true;
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

    if (!this.utilisateurId) {
      console.error("L'ID de l'utilisateur est manquant ou invalide");
    } else {
      this.loadObjectifs(this.utilisateurId);
    }
  }

  loadObjectifs(utilisateurId: number): void {
    this.objectifService.getObjectifsByUtilisateurId(utilisateurId).subscribe({
      next: (data) => {
        this.objectifs = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des objectifs', error);
        this.isLoading = false;
      }
    });
  }

  deleteObjectif(objectifId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet objectif ?')) {
      this.objectifService.deleteObjectif(objectifId).subscribe({
        next: () => {
          console.log('Objectif supprimé');
          this.loadObjectifs(this.utilisateurId!);
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de l\'objectif', err);
        }
      });
    }
  }

  redirectToCreateObjectif(): void {
    this.router.navigate(['/user/objectif-create']);
  }
  redirectToUpdateObjectif(objectifId: number): void {
    this.router.navigate(['/user/objectif-update', objectifId]);
  }

}
