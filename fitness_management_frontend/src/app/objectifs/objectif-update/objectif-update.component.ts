import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ObjectifFitness } from '../../models/objectiffitness.model';
import { ObjectifFitnessService } from '../../services/objectif-fitness.service';

@Component({
  selector: 'app-objectif-update',
  templateUrl: './objectif-update.component.html',
  styleUrls: ['./objectif-update.component.css']
})
export class ObjectifUpdateComponent implements OnInit {
  objectif: ObjectifFitness | null = null;
  isLoading = true;

  constructor(
    private objectifService: ObjectifFitnessService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const objectifId = +this.route.snapshot.paramMap.get('id')!;
    this.loadObjectif(objectifId);
  }

  loadObjectif(objectifId: number): void {
    this.objectifService.getObjectifById(objectifId).subscribe({
      next: (data) => {
        this.objectif = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement de l\'objectif', err);
        this.isLoading = false;
      }
    });
  }

  updateObjectif(): void {
    if (this.objectif) {
      this.objectifService.updateObjectif(this.objectif.id, this.objectif).subscribe({
        next: (updatedObjectif) => {
          console.log('Objectif mis à jour:', updatedObjectif);
          this.router.navigate(['/user/objectif-list']);
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour de l\'objectif', err);
        }
      });
    }
  }
}
