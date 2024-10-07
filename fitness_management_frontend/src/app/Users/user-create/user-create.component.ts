import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from "../../services/user.service";
import { Personne } from "../../models/personne.model";

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.css']
})
export class UserCreateComponent implements OnInit {
  userForm: FormGroup;
  errorMessage: string = '';
  profileImage: File | null = null;
  coverImage: File | null = null;

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
      role: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    console.log('User object:', this.userForm.value);
    this.userService.registerUser(this.userForm.value).subscribe(
    () => {
      console.log('Form valid:', this.userForm.valid);
      console.log('Form errors:', this.userForm.errors);
      Object.keys(this.userForm.controls).forEach(key => {
        const control = this.userForm.get(key);
        console.log(`${key} valid:`, control?.valid, 'errors:', control?.errors);
      });
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
    console.log('Form submitted. Valid:', this.userForm.valid);
    if (this.userForm.valid) {
      const formData = new FormData();
      const userData = this.userForm.value as Omit<Personne, 'id' | 'profileImage'>;

      formData.append('nom', userData.nom);
      formData.append('prenom', userData.prenom);
      formData.append('email', userData.email);
      formData.append('genre', userData.genre);
      formData.append('role', userData.role);
      formData.append('password', userData.motDePasse);

      if (this.profileImage) {
        formData.append('profileImage', this.profileImage, this.profileImage.name);
      }
      if (this.coverImage) {
        formData.append('coverImage', this.coverImage, this.coverImage.name);
      }
      this.userService.registerUser(this.userForm.value).subscribe({
        next: (response) => {
          console.log('User registered successfully', response);
          // Handle success
        },
        error: (error) => {
          console.error('Registration error', error);
          if (error.error instanceof ErrorEvent) {
            this.errorMessage = `Error: ${error.error.message}`;
          } else {
            this.errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
          }
        }
      });

    }
  }
}
