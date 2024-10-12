import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ObjectifFitness} from "../models/objectiffitness.model";


@Injectable({
  providedIn: 'root',
})
export class ObjectifFitnessService {
  private apiUrl = 'http://localhost:8084/api/objectifs';  // L'URL de votre backend

  constructor(private http: HttpClient) {}

  getAllObjectifs(): Observable<ObjectifFitness[]> {
    return this.http.get<ObjectifFitness[]>(this.apiUrl);
  }

  getObjectifById(id: number): Observable<ObjectifFitness> {
    return this.http.get<ObjectifFitness>(`${this.apiUrl}/${id}`);
  }

  createObjectif(objectif: ObjectifFitness): Observable<ObjectifFitness> {
    return this.http.post<ObjectifFitness>(this.apiUrl, objectif);
  }

  updateObjectif(id: number, objectif: ObjectifFitness): Observable<ObjectifFitness> {
    return this.http.put<ObjectifFitness>(`${this.apiUrl}/${id}`, objectif);
  }

  deleteObjectif(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
