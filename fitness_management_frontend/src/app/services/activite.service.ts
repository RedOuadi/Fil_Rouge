import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Activite } from '../models/activite.model';

@Injectable({
  providedIn: 'root',
})
export class ActiviteService {
  private apiUrl = 'http://localhost:8084/api/activites';  // L'URL de votre backend

  constructor(private http: HttpClient) {}

  getAllActivites(): Observable<Activite[]> {
    return this.http.get<Activite[]>(this.apiUrl);
  }

  getActiviteById(id: number): Observable<Activite> {
    return this.http.get<Activite>(`${this.apiUrl}/${id}`);
  }

  getActivitesByUtilisateurId(utilisateurId: number): Observable<Activite[]> {
    return this.http.get<Activite[]>(`${this.apiUrl}/utilisateur/${utilisateurId}`);
  }



  createActivite(activite: Activite, imageFile?: File, videoFile?: File): Observable<Activite> {
    const formData = new FormData();
    formData.append('activite', JSON.stringify(activite));
    if (imageFile) {
      formData.append('imageFile', imageFile);
    }
    if (videoFile) {
      formData.append('videoFile', videoFile);
    }

    return this.http.post<Activite>(`${this.apiUrl}/create`, formData);
  }

  updateActivite(id: number, activite: Activite, imageFile?: File, videoFile?: File): Observable<Activite> {
    const formData = new FormData();
    formData.append('activite', JSON.stringify(activite));
    if (imageFile) {
      formData.append('imageFile', imageFile);
    }
    if (videoFile) {
      formData.append('videoFile', videoFile);
    }

    return this.http.put<Activite>(`${this.apiUrl}/${id}`, formData);
  }

  deleteActivite(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
