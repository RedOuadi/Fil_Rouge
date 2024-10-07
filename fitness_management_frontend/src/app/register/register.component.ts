import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Router } from '@angular/router';
import {PersonneService} from "../services/personne.service";

@Component({
  selector: 'app-register',
  template: `
    <div class="container mx-auto p-4">
      <h2 class="text-2xl font-bold mb-4">Register</h2>

      <form [formGroup]="registerForm" (ngSubmit)="onSubmit()" class="max-w-md">
        <div class="mb-4">
          <label for="email" class="block mb-2">Email</label>
          <input
            id="email"
            type="email"
            formControlName="email"
            class="w-full p-2 border rounded"
          />
          <div *ngIf="registerForm.get('email')?.invalid && registerForm.get('email')?.touched" class="text-red-500 text-sm mt-1">
            <div *ngIf="registerForm.get('email')?.errors?.['required']">Email is required</div>
            <div *ngIf="registerForm.get('email')?.errors?.['email']">Invalid email format</div>
          </div>
        </div>

        <div class="mb-4">
          <label for="nom" class="block mb-2">Last Name</label>
          <input
            id="nom"
            type="text"
            formControlName="nom"
            class="w-full p-2 border rounded"
          />
          <div *ngIf="registerForm.get('nom')?.invalid && registerForm.get('nom')?.touched" class="text-red-500 text-sm mt-1">
            Last name is required
          </div>
        </div>

        <div class="mb-4">
          <label for="prenom" class="block mb-2">First Name</label>
          <input
            id="prenom"
            type="text"
            formControlName="prenom"
            class="w-full p-2 border rounded"
          />
          <div *ngIf="registerForm.get('prenom')?.invalid && registerForm.get('prenom')?.touched" class="text-red-500 text-sm mt-1">
            First name is required
          </div>
        </div>

        <div class="mb-4">
          <label for="genre" class="block mb-2">Gender</label>
          <select
            id="genre"
            formControlName="genre"
            class="w-full p-2 border rounded"
          >
            <option value="">Select gender</option>
            <option value="HOMME">Male</option>
            <option value="FEMME">Female</option>
          </select>
          <div *ngIf="registerForm.get('genre')?.invalid && registerForm.get('genre')?.touched" class="text-red-500 text-sm mt-1">
            Gender is required
          </div>
        </div>

        <div class="mb-4">
          <label for="motDePasse" class="block mb-2">Password</label>
          <input
            id="motDePasse"
            type="password"
            formControlName="motDePasse"
            class="w-full p-2 border rounded"
          />
          <div *ngIf="registerForm.get('motDePasse')?.invalid && registerForm.get('motDePasse')?.touched" class="text-red-500 text-sm mt-1">
            <div *ngIf="registerForm.get('motDePasse')?.errors?.['required']">Password is required</div>
            <div *ngIf="registerForm.get('motDePasse')?.errors?.['minlength']">Password must be at least 6 characters</div>
          </div>
        </div>

        <div class="mb-4">
          <label for="profileImage" class="block mb-2">Profile Image</label>
          <input
            type="file"
            id="profileImage"
            (change)="onFileSelected($event)"
            accept="image/*"
            class="w-full p-2 border rounded"
          />
        </div>

        <button
          type="submit"
          [disabled]="registerForm.invalid || isLoading"
          class="bg-blue-500 text-white px-4 py-2 rounded disabled:bg-gray-400"
        >
          {{ isLoading ? 'Registering...' : 'Register' }}
        </button>
      </form>
    </div>
  `
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  selectedFile: File | null = null;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private personneService: PersonneService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      genre: ['', Validators.required],
      motDePasse: ['', [Validators.required, Validators.minLength(6)]],
      role: ['ROLE_UTILISATEUR'] // Default role
    });
  }

  ngOnInit(): void {}

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.isLoading = true;

      const formData = new FormData();
      const personneJson = JSON.stringify(this.registerForm.value);
      formData.append('personne', personneJson);

      if (this.selectedFile) {
        formData.append('profileImage', this.selectedFile);
      }

      this.personneService.register(formData).subscribe({
        next: (response) => {
          console.log('Registration successful', response);
          // Navigate to login page or show success message
          this.router.navigate(['/login']);
        },
        error: (error) => {
          console.error('Registration failed', error);
          // Handle error (show error message to user)
        },
        complete: () => {
          this.isLoading = false;
        }
      });
    }
  }
}
