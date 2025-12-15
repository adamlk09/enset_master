import { Routes } from '@angular/router';
import { DashboardComponent } from './features/dashboard/dashboard';
import { LoginPageComponent } from './views/login-page/login-page';
import { AuthGuard } from './core/guards/auth-guard';
import { ProfileComponent } from './features/profile/profile';
import { AccountsComponent } from './features/accounts/accounts';

export const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  {
    path: '', canActivate: [AuthGuard], children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'accounts', component: AccountsComponent },
      {
        path: 'customers',
        // LA CORRECTION CRUCIALE EST ICI :
        loadChildren: () => import('./features/customers/customers.routes').then(m => m.CUSTOMER_ROUTES)
      },
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];
