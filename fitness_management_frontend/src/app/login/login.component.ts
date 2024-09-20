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
          // Set the token and user information in the service or local storage
          this.personneService.setToken(response.token);
          this.personneService.setCurrentUser({
            email: email,
            role: response.role,
            motDePasse: motDePasse,
          });

          // Redirect based on role
          if (response.role === 'ROLE_ADMIN') {
            this.router.navigate(['/Dashbord-admin']);
          } else if (response.role === 'ROLE_TECHNICIEN') {
            this.router.navigate(['/dashboard-technicien']); // Adjust the route as needed
          } else {
            this.router.navigate(['/dashboard-user']); // Adjust the route as needed
          }
        },
        (error) => {
          console.error('Login error', error);
        }
      );
    }
  }
}
