import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CoachService } from "../../services/coach.service";
import { Personne } from "../../models/personne.model";

@Component({
  selector: 'app-coach-create',
  templateUrl: './coach-create.component.html',
  styleUrls: ['./coach-create.component.css']
})
export class CoachCreateComponent implements OnInit {
  coachForm: FormGroup;
  errorMessage: string = '';
  profileImage: File | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private coachService: CoachService,
    private router: Router
  ) {
    this.coachForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      prenom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      genre: ['', Validators.required],
      // Role is set to ROLE_COACH by default in the backend
    });
  }

  ngOnInit(): void {}

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.profileImage = input.files[0];
    }
  }

  onSubmit(): void {
    if (this.coachForm.valid) {
      const formData = new FormData();

      const coachJson = JSON.stringify({
        nom: this.coachForm.get('nom')?.value,
        prenom: this.coachForm.get('prenom')?.value,
        email: this.coachForm.get('email')?.value,
        motDePasse: this.coachForm.get('password')?.value,
        genre: this.coachForm.get('genre')?.value,
        role: 'ROLE_COACH'
      });

      formData.append('personne', new Blob([coachJson], { type: 'application/json' }));

      if (this.profileImage) {
        formData.append('profileImage', this.profileImage);
      }

      this.coachService.registerCoach(formData).subscribe({
        next: (response) => {
          console.log('Coach registered successfully', response);
          this.router.navigate(['/coaches']);
        },
        error: (error) => {
          console.error('Registration error', error);
          this.errorMessage = error.error?.message || 'An error occurred during registration';
        }
      });
    } else {
      this.errorMessage = 'Please fill in all required fields correctly.';
    }
  }

  protected readonly Object = Object;
}
