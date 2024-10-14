import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ExerciceService } from '../../../services/exercice.service';
import { Exercice } from '../../../models/exercice.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-exercice-update',
  templateUrl: './exercice-update.component.html',
  styleUrls: ['./exercice-update.component.css']
})
export class ExerciceUpdateComponent implements OnInit {
  exerciceForm: FormGroup;
  imageFile: File | null = null;
  videoFile: File | null = null;
  isLoading = false;
  exerciceId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private exerciceService: ExerciceService,
    private router: Router
  ) {
    this.exerciceForm = this.fb.group({
      nom: ['', Validators.required],
      description: ['', Validators.required],
      duree: [0, [Validators.required, Validators.min(1)]],
      niveau: ['', Validators.required],
      caloriesBrulees: [0, Validators.required],
      programmeId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.exerciceId = +params.get('id')!;
      this.loadExercice(this.exerciceId);
    });
  }

  loadExercice(id: number): void {
    this.exerciceService.getExerciceById(id).subscribe({
      next: (exercice) => {
        this.exerciceForm.patchValue(exercice);
      },
      error: (err) => {
        console.error('Error loading exercise:', err);
      }
    });
  }

  onImageSelected(event: any): void {
    this.imageFile = event.target.files[0];
  }

  onVideoSelected(event: any): void {
    this.videoFile = event.target.files[0];
  }

  onSubmit(): void {
    if (this.exerciceForm.invalid || !this.exerciceId) return;

    this.isLoading = true;
    const exercice: Exercice = this.exerciceForm.value;

    // Pass undefined instead of null for imageFile and videoFile
    this.exerciceService.updateExercice(this.exerciceId, exercice, this.imageFile || undefined, this.videoFile || undefined).subscribe({
      next: () => {
        this.router.navigate(['/coach/exercice-list', exercice.programmeId]);
      },
      error: (err) => {
        console.error('Failed to update exercice', err);
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}
