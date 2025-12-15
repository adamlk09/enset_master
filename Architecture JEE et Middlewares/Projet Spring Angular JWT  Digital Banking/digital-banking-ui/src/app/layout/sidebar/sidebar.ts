import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; // <-- AJOUTER
import { Material } from '../../material';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-sidebar',
  standalone: true, // <-- IMPORTANT
  imports: [
    CommonModule,
    RouterModule, // <-- AJOUTER
    Material
  ],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})

export class SidebarComponent {
  constructor(private authService: AuthService) { }
  // AJOUTEZ CETTE MÉTHODE
  logout(): void {
    console.log('Logging out...');
    // La logique de déconnexion sera ajoutée ici plus tard
    this.authService.logout();
  }
}
