import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Personne} from "../models/personne.model";

@Injectable({
  providedIn: 'root'
})
export class PersonneService {
  private apiUrl = 'http://localhost:8084/api/users';

  constructor(private http: HttpClient) {}

  getTotalUsers(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count-users`);
  }

  getTotalCoaches(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count-coaches`);
  }

  getUsers(): Observable<Personne[]> {
    return this.http.get<Personne[]>(`${this.apiUrl}/users`);
  }

  // Fetch the list of all coaches
  getCoaches(): Observable<Personne[]> {
    return this.http.get<Personne[]>(`${this.apiUrl}/coaches`);
  }
  register(user: Personne): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  // Connexion de l'utilisateur
  login(credentials: { email: string, motDePasse: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  // Gestion des jetons (JWT)
  setToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  // Redirection après connexion


  // Sauvegarder l'utilisateur courant
  setCurrentUser(user: any): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  // Récupérer le rôle actuel
  getCurrentUserRole(): string | null {
    const user = localStorage.getItem('currentUser');
    return user ? JSON.parse(user).role : null;
  }
}

