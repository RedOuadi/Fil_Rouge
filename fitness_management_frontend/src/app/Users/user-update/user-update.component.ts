import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { Personne } from '../../models/personne.model';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.css']
})
export class UserUpdateComponent implements OnInit {
  userForm: FormGroup;
  userId: number;
  currentUser: Personne | null = null;
  errorMessage: string = '';
  profileImage: File | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.userForm = this.formBuilder.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      prenom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      genre: ['', Validators.required],
      role: ['', Validators.required],
      // motDePasse is intentionally left out of the form for security reasons
      // Only include it if explicitly required for updates
    });
    this.userId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    this.userService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.currentUser = user;
        this.userForm.patchValue({
          nom: user.nom,
          prenom: user.prenom,
          email: user.email,
          genre: user.genre,
          role: user.role
        });
      },
      error: (error) => {
        console.error('Error loading user', error);
        this.errorMessage = 'Error loading user details';
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
    return this.currentUser?.profileImage?.imageUrl || null;
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      const formData = new FormData();

      const userJson = JSON.stringify({
        nom: this.userForm.get('nom')?.value,
        prenom: this.userForm.get('prenom')?.value,
        email: this.userForm.get('email')?.value,
        genre: this.userForm.get('genre')?.value,
        role: this.userForm.get('role')?.value
      });

      formData.append('personne', new Blob([userJson], { type: 'application/json' }));

      if (this.profileImage) {
        formData.append('profileImage', this.profileImage);
      }

      this.userService.updateUser(this.userId, formData).subscribe({
        next: (response) => {
          console.log('User updated successfully', response);
          this.router.navigate(['/users']);
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
