import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ExerciceService } from '../../../services/exercice.service';
import { Exercice } from '../../../models/exercice.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-exercice-create',
  templateUrl: './exercice-create.component.html',
  styleUrls: ['./exercice-create.component.css']
})
export class ExerciceCreateComponent implements OnInit {
  exerciceForm: FormGroup;
  imageFile: File | null = null;
  videoFile: File | null = null;
  isLoading = false;
  programmeId: number | null = null; // Add programmeId

  constructor(
    private fb: FormBuilder,
    private exerciceService: ExerciceService,
    private router: Router,
    private route: ActivatedRoute // Add ActivatedRoute
  ) {
    this.exerciceForm = this.fb.group({
      nom: ['', Validators.required],
      description: ['', Validators.required],
      duree: [0, [Validators.required, Validators.min(1)]],
      niveau: ['', Validators.required],
      caloriesBrulees: [0, Validators.required],
    });
  }

  ngOnInit(): void {
    // Retrieve the programmeId from the route params
    this.route.paramMap.subscribe(params => {
      const programId = params.get('programmeId');
      if (programId) {
        this.programmeId = +programId;
      } else {
        console.error('No program ID found');
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
    if (this.exerciceForm.invalid || !this.programmeId) return;

    this.isLoading = true;
    const exercice: Exercice = this.exerciceForm.value;
    exercice.programmeId = this.programmeId; // Assign the programmeId

    this.exerciceService.createExercice(exercice, this.imageFile || undefined, this.videoFile || undefined).subscribe({
      next: () => {
        this.router.navigate(['/coach/exercice-list', this.programmeId]);
      },
      error: (err) => {
        console.error('Failed to create exercise', err);
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}
