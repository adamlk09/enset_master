import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; // <-- AJOUTER
import { Material } from '../../material';

@Component({
  selector: 'app-navbar',
  standalone: true, // <-- IMPORTANT
  imports: [
    CommonModule,
    RouterModule, // <-- AJOUTER
    Material
  ],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})

export class Navbar {
  @Output() toggleSidebar = new EventEmitter<void>();
  isDarkMode = false;

  constructor() {
    this.isDarkMode = document.body.classList.contains('dark-theme');
  }

  toggleTheme(): void {
    this.isDarkMode = !this.isDarkMode;
    document.body.classList.toggle('dark-theme', this.isDarkMode);
  }
}
