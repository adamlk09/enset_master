import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms'; // <-- AJOUTER ReactiveFormsModule
import { Material } from '../../material';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-login-page',
  standalone: true, // <-- IMPORTANT
  imports: [
    CommonModule,
    ReactiveFormsModule, // <-- AJOUTER
    Material
  ],
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})

export class LoginPageComponent {
  loginForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['user', [Validators.required]],
      password: ['password', [Validators.required]] // Mettez le mot de passe que vous avez défini dans le backend
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: () => {
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          this.errorMessage = 'Invalid username or password. Please try again.';
          console.error(err);
        }
      });
    }
  }
}
