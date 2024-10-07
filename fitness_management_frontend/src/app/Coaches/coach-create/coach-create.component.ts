import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import {CoachService} from "../../services/coach.service";
import {Personne} from "../../models/personne.model";

@Component({
  selector: 'app-coach-create',
  templateUrl: './coach-create.component.html',
  styleUrls: ['./coach-create.component.css']
})
export class CoachCreateComponent implements OnInit {
  coachForm: FormGroup;
  errorMessage: string = '';
  profileImage: File | null = null;
  coverImage: File | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private coachService: CoachService,
    private router: Router
  ) {
    this.coachForm = this.formBuilder.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      genre: ['', Validators.required],
      role: ['ROLE_COACH', Validators.required]
    });
  }

  ngOnInit(): void {}

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
      const coachData: Personne = this.coachForm.value;

      // Convert the form data to a JSON string
      const coachDataJson = JSON.stringify(coachData);
      formData.append('personne', coachDataJson);

      if (this.profileImage) {
        formData.append('profileImage', this.profileImage, this.profileImage.name);
      }
      if (this.coverImage) {
        formData.append('coverImage', this.coverImage, this.coverImage.name);
      }

      this.coachService.registerCoach(formData).subscribe({
        next: () => {
          this.router.navigate(['/coaches']);
        },
        error: (error) => {
          this.errorMessage = 'Error creating coach. Please try again.';
          console.error('There was an error!', error);
        }
      });
    }
  }
}
