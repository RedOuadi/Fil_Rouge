import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Personne } from '../models/personne.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8084/api/users';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<Personne[]> {
    return this.http.get<Personne[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  getUserById(id: number): Observable<Personne> {
    return this.http.get<Personne>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  registerUser(formData: FormData): Observable<any> {
    return this.http.post(`${this.apiUrl}`, formData)
      .pipe(
        catchError(this.handleError)
      );
  }
  updateUser(id: number, userData: FormData): Observable<Personne> {
    return this.http.put<Personne>(`${this.apiUrl}/${id}`, userData).pipe(
      catchError(this.handleError)
    );
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}
