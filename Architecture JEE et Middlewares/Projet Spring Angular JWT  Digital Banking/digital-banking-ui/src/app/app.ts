import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './core/services/auth';
import { Material } from './material';
import { Navbar } from './layout/navbar/navbar';
import { SidebarComponent } from './layout/sidebar/sidebar';

@Component({
  selector: 'app-root',
  standalone: true, // <-- IMPORTANT
  imports: [
    CommonModule,
    RouterOutlet,
    Material,
    Navbar,  // <-- AJOUTER
    SidebarComponent  // <-- AJOUTER
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isAuthenticated$: Observable<boolean>;

  constructor(private authService: AuthService) {
    this.isAuthenticated$ = this.authService.isAuthenticated$;
  }
}
