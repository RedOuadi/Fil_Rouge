import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PersonneService } from '../services/personne.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private personneService: PersonneService, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      motDePasse: ['', Validators.required],
    });
  }

  login(): void {
    if (this.loginForm.valid) {
      const { email, motDePasse } = this.loginForm.value;
      const loginData = { email, motDePasse };

      this.personneService.login(loginData).subscribe(
        (response: any) => {
          this.personneService.setToken(response.token);
          this.personneService.setCurrentUser({
            email: email,
            role: response.role,
            motDePasse: motDePasse,
          });

          // Store the user ID and coach ID based on the response
          localStorage.setItem('userId', response.userId || ''); // Store user ID if exists
          localStorage.setItem('coachId', response.coachId || ''); // Store coach ID if exists

          // Debugging: Log the role for checking
          console.log('User role:', response.role);

          // Redirect based on role
          if (response.role === 'ROLE_ADMIN') {
            this.router.navigate(['/dashboard-admin']);
          } else if (response.role === 'ROLE_COACH') {
            this.router.navigate(['/dashboard-coach']); // Coach role redirection
          } else if (response.role === 'ROLE_USER') {
            this.router.navigate(['/dashboard-user']); // User role redirection
          } else {
            console.error('Unknown role:', response.role);
          }
        },
        (error) => {
          console.error('Login error', error);
        }
      );
    }
  }
}
