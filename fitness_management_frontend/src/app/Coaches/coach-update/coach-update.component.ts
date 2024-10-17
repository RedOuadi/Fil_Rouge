import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CoachService } from '../../services/coach.service';
import { Personne } from '../../models/personne.model';

@Component({
  selector: 'app-coach-update',
  templateUrl: './coach-update.component.html',
  styleUrls: ['./coach-update.component.css']
})
export class CoachUpdateComponent implements OnInit {
  coachForm: FormGroup;
  coachId: number;
  currentCoach: Personne | null = null;
  errorMessage: string = '';
  profileImage: File | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private coachService: CoachService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.coachForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      prenom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      genre: ['', Validators.required],
      // Role is not included in the form as it's fixed for coaches
    });
    this.coachId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.loadCoach();
  }

  loadCoach(): void {
    this.coachService.getCoachById(this.coachId).subscribe({
      next: (coach) => {
        this.currentCoach = coach;
        this.coachForm.patchValue({
          nom: coach.nom,
          prenom: coach.prenom,
          email: coach.email,
          genre: coach.genre,
        });
      },
      error: (error) => {
        console.error('Error loading coach', error);
        this.errorMessage = 'Error loading coach details';
      }
    });
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.profileImage = input.files[0];
    }
  }

  getProfileImageUrl(): string | null {
    return this.currentCoach?.profileImage?.imageUrl || null;
  }

  onSubmit(): void {
    if (this.coachForm.valid) {
      const formData = new FormData();

      const coachJson = JSON.stringify({
        nom: this.coachForm.get('nom')?.value,
        prenom: this.coachForm.get('prenom')?.value,
        email: this.coachForm.get('email')?.value,
        genre: this.coachForm.get('genre')?.value,
        role: 'ROLE_COACH'
      });

      formData.append('personne', new Blob([coachJson], { type: 'application/json' }));

      if (this.profileImage) {
        formData.append('profileImage', this.profileImage);
      }

      this.coachService.updateCoach(this.coachId, formData).subscribe({
        next: (response) => {
          console.log('Coach updated successfully', response);
          this.router.navigate(['/coaches']);
        },
        error: (error) => {
          console.error('Update error', error);
          this.errorMessage = error.error?.message || 'An error occurred during update';
        }
      });
    } else {
      this.errorMessage = 'Please fill in all required fields correctly.';
    }
  }

  protected readonly Object = Object;
}
