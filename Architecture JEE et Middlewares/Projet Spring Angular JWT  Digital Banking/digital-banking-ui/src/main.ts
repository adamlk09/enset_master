import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { importProvidersFrom } from '@angular/core';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts'; // <-- L'IMPORT CORRECT POUR NG2-CHARTS

import { AppComponent } from './app/app';
import { routes } from './app/app.routes';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './app/core/interceptors/jwt-interceptor';
import { AuthService } from './app/core/services/auth';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi()), // Configuration HttpClient
    provideCharts(withDefaultRegisterables()),   // Configuration NgCharts

    // Fournisseurs pour l'authentification et l'intercepteur
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ]
}).catch(err => console.error(err));
