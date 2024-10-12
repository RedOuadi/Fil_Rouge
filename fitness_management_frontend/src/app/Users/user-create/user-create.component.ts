import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from "../../services/user.service";
import { Personne } from "../../models/personne.model";
import {Component, OnInit} from "@angular/core";

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent implements OnInit {
  userForm: FormGroup;
  errorMessage: string = '';
  profileImage: File | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.userForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      prenom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      genre: ['', Validators.required],
      role: ['ROLE_UTILISATEUR', Validators.required]  // Set default role
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
    if (this.userForm.valid) {
      const formData = new FormData();

      // Convert form data to JSON string and append as a Blob
      const userJson = JSON.stringify({
        nom: this.userForm.get('nom')?.value,
        prenom: this.userForm.get('prenom')?.value,
        email: this.userForm.get('email')?.value,
        motDePasse: this.userForm.get('password')?.value,
        genre: this.userForm.get('genre')?.value,
        role: this.userForm.get('role')?.value
      });

      formData.append('personne', new Blob([userJson], { type: 'application/json' }));

      if (this.profileImage) {
        formData.append('profileImage', this.profileImage);
      }

      this.userService.registerUser(formData).subscribe({
        next: (response) => {
          console.log('User registered successfully', response);
          this.router.navigate(['/users']);
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
