import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Exercice } from '../models/exercice.model';

@Injectable({
  providedIn: 'root',
})
export class ExerciceService {
  private apiUrl = 'http://localhost:8084/api/exercices';  // L'URL de votre backend

  constructor(private http: HttpClient) {}

  getAllExercices(): Observable<Exercice[]> {
    return this.http.get<Exercice[]>(this.apiUrl);
  }

  getExerciceById(id: number): Observable<Exercice> {
    return this.http.get<Exercice>(`${this.apiUrl}/${id}`);
  }

  getExercicesByProgrammeId(programmeId: number): Observable<Exercice[]> {
    return this.http.get<Exercice[]>(`${this.apiUrl}/programme/${programmeId}`);
  }

  createExercice(exercice: Exercice, imageFile?: File, videoFile?: File): Observable<Exercice> {
    const formData = new FormData();
    formData.append('exercice', JSON.stringify(exercice));
    if (imageFile) {
      formData.append('imageFile', imageFile);
    }
    if (videoFile) {
      formData.append('videoFile', videoFile);
    }
    return this.http.post<Exercice>(`${this.apiUrl}/create`, formData);
  }

  updateExercice(id: number, exercice: Exercice, imageFile?: File, videoFile?: File): Observable<Exercice> {
    const formData = new FormData();
    formData.append('exercice', JSON.stringify(exercice));
    if (imageFile) {
      formData.append('imageFile', imageFile);
    }
    if (videoFile) {
      formData.append('videoFile', videoFile);
    }
    return this.http.put<Exercice>(`${this.apiUrl}/${id}`, formData);
  }

  deleteExercice(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
