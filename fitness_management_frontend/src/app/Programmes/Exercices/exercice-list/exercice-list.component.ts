import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { Exercice } from '../../../models/exercice.model';
import { ExerciceService } from '../../../services/exercice.service';

@Component({
  selector: 'app-exercice-list',
  templateUrl: './exercice-list.component.html',
  styleUrls: ['./exercice-list.component.css']
})
export class ExerciceListComponent implements OnInit {
  exercices: Exercice[] = [];
  isLoading = true;
  error: string | null = null;
  programmeId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private exerciceService: ExerciceService,
    private router: Router // Add Router here
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const programId = params.get('programId');
      if (programId) {
        this.programmeId = +programId;
        this.loadExercices(this.programmeId);
      } else {
        console.error('No program ID provided in route parameters');
        this.error = 'Programme ID not provided';
        this.isLoading = false;
      }
    });
  }

  private loadExercices(programId: number): void {
    this.isLoading = true;
    this.error = null;

    this.exerciceService.getExercicesByProgrammeId(programId)
      .pipe(
        catchError(err => {
          console.error('Error fetching exercises:', err);
          this.error = 'Failed to load exercises. Please try again later.';
          return of([] as Exercice[]);
        }),
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe(exercices => {
        this.exercices = exercices;
      });
  }

  // Method to navigate to the create exercice page
  createExercice(): void {
    if (this.programmeId) {
      this.router.navigate(['/coach/exercice-create', this.programmeId]);
    }
  }

  // Method to navigate to the update exercice page
  updateExercice(id: number): void {
    this.router.navigate(['/coach/exercice-update', id]);
  }

  // Method to delete an exercice
  deleteExercice(id: number): void {
    if (confirm('Are you sure you want to delete this exercise?')) {
      this.exerciceService.deleteExercice(id).subscribe({
        next: () => {
          this.exercices = this.exercices.filter(exercice => exercice.id !== id);
        },
        error: (err) => {
          console.error('Error deleting exercise:', err);
        }
      });
    }
  }
}
