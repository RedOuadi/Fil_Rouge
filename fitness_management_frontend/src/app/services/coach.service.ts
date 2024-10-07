import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Personne } from '../models/personne.model';

@Injectable({
  providedIn: 'root'
})
export class CoachService {
  private apiUrl = 'http://localhost:8084/api/personnes/coaches';

  constructor(private http: HttpClient) {}

  getCoaches(): Observable<Personne[]> {
    return this.http.get<Personne[]>(`${this.apiUrl}`).pipe(
      catchError(this.handleError)
    );
  }

  getCoachById(id: number): Observable<Personne> {
    return this.http.get<Personne>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  registerCoach(coachData: FormData): Observable<Personne> {
    return this.http.post<Personne>(`${this.apiUrl}/register`, coachData).pipe(
      catchError(this.handleError)
    );
  }

  updateCoach(id: number, coachData: FormData): Observable<Personne> {
    return this.http.put<Personne>(`${this.apiUrl}/${id}`, coachData).pipe(
      catchError(this.handleError)
    );
  }

  deleteCoach(id: number): Observable<void> {
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
