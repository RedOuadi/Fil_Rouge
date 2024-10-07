import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {CoachService} from "../../services/coach.service";
import {Personne} from "../../models/personne.model";


@Component({
  selector: 'app-coach-update',
  templateUrl: './coach-update.component.html',
  styleUrls: ['./coach-update.component.css']
})
export class CoachUpdateComponent implements OnInit {
  coachForm: FormGroup;
  coachId: number;
  errorMessage: string = '';
  profileImage: File | null = null;
  coverImage: File | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private coachService: CoachService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.coachForm = this.formBuilder.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      genre: ['', Validators.required],
      role: ['ROLE_COACH', Validators.required]
    });
    this.coachId = 0;
  }

  ngOnInit(): void {
    this.coachId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadCoach();
  }

  loadCoach(): void {
    this.coachService.getCoachById(this.coachId).subscribe({
      next: (coach) => {
        this.coachForm.patchValue(coach);
      },
      error: (error) => {
        this.errorMessage = 'Error loading coach. Please try again.';
        console.error('There was an error!', error);
      }
    });
  }

  onFileChange(event: Event, type: 'profile' | 'cover'): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      if (type === 'profile') {
        this.profileImage = input.files[0];
      } else {
        this.coverImage = input.files[0];
      }
    }
  }

  onSubmit(): void {
    if (this.coachForm.valid) {
      const formData = new FormData();
      const coachData: Personne = { ...this.coachForm.value, id: this.coachId };

      // Convert the form data to a JSON string
      const coachDataJson = JSON.stringify(coachData);
      formData.append('personne', coachDataJson);

      if (this.profileImage) {
        formData.append('profileImage', this.profileImage, this.profileImage.name);
      }
      if (this.coverImage) {
        formData.append('coverImage', this.coverImage, this.coverImage.name);
      }

      this.coachService.updateCoach(this.coachId, formData).subscribe({
        next: () => {
          this.router.navigate(['/coaches']);
        },
        error: (error) => {
          this.errorMessage = 'Error updating coach. Please try again.';
          console.error('There was an error!', error);
        }
      });
    }
  }
}
