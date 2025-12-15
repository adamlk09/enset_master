import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8084'; // L'URL de votre backend

  // BehaviorSubject pour suivre l'état d'authentification en temps réel
  private authState = new BehaviorSubject<boolean>(this.hasToken());
  isAuthenticated$ = this.authState.asObservable();

  constructor(private http: HttpClient, private router: Router) { }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/auth/login`, credentials).pipe(
      tap((response: any) => {
        // Stocker les tokens après une connexion réussie
        localStorage.setItem('access_token', response.accessToken);
        localStorage.setItem('refresh_token', response.refreshToken);
        this.authState.next(true); // Mettre à jour l'état d'authentification
      })
    );
  }

  logout(): void {
    // Supprimer les tokens
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    this.authState.next(false); // Mettre à jour l'état
    this.router.navigate(['/login']); // Rediriger vers la page de connexion
  }

  getAccessToken(): string | null {
    return localStorage.getItem('access_token');
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('access_token');
  }
}
