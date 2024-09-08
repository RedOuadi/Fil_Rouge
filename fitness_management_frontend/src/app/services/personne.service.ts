import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonneService {
  private apiUrl = 'http://localhost:8084/api/users';

  constructor(private http: HttpClient) {}

  getTotalUsers(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/users`);
  }

  getTotalCoaches(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/coaches`);
  }
}
