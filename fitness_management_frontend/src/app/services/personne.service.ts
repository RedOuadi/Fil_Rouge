// src/app/services/personne.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Personne } from '../models/personne.model';

@Injectable({
  providedIn: 'root'
})
export class PersonneService {
  private apiUrl = 'http://localhost:8084/api/personnes';

  constructor(private http: HttpClient) {}

  // Get total number of users
  getTotalUsers(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count-users`);
  }

  // Get total number of coaches
  getTotalCoaches(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count-coaches`);
  }

  // Fetch the list of users
  getUsers(): Observable<Personne[]> {
    return this.http.get<Personne[]>(`${this.apiUrl}/users`);
  }

  // Fetch the list of all coaches
  getCoaches(): Observable<Personne[]> {
    return this.http.get<Personne[]>(`${this.apiUrl}/coaches`);
  }

  // Register a new user
  register(formData: FormData): Observable<Personne> {
    return this.http.post<Personne>(`${this.apiUrl}/register`, formData);
  }

  // User login
  login(credentials: { email: string; motDePasse: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  // JWT token management
  setToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  // Save the current user in local storage
  setCurrentUser(user: any): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  // Retrieve the role of the current user
  getCurrentUserRole(): string | null {
    const user = localStorage.getItem('currentUser');
    return user ? JSON.parse(user).role : null;
  }
}
