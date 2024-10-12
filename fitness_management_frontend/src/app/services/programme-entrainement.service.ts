import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ProgrammeEntrainement} from "../models/programmeentrainement.model";


@Injectable({
  providedIn: 'root',
})
export class ProgrammeEntrainementService {
  private apiUrl = 'http://localhost:8084/api/programmes';  // L'URL de votre backend

  constructor(private http: HttpClient) {}

  getAllProgrammes(): Observable<ProgrammeEntrainement[]> {
    return this.http.get<ProgrammeEntrainement[]>(this.apiUrl);
  }
  getProgrammesByCoachId(coachId: number): Observable<ProgrammeEntrainement[]> {
    return this.http.get<ProgrammeEntrainement[]>(`${this.apiUrl}/coach/${coachId}`);
  }

  getProgrammeById(id: number): Observable<ProgrammeEntrainement> {
    return this.http.get<ProgrammeEntrainement>(`${this.apiUrl}/${id}`);
  }

  createProgramme(programme: ProgrammeEntrainement): Observable<ProgrammeEntrainement> {
    return this.http.post<ProgrammeEntrainement>(this.apiUrl, programme);
  }

  updateProgramme(id: number, programme: ProgrammeEntrainement): Observable<ProgrammeEntrainement> {
    return this.http.put<ProgrammeEntrainement>(`${this.apiUrl}/${id}`, programme);
  }

  deleteProgramme(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
