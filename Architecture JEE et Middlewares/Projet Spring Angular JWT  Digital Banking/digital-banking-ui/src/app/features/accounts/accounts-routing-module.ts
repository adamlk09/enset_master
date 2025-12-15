import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountsComponent } from './accounts'; // Import du nouveau composant

const routes: Routes = [
  // Quand on arrive sur /accounts, on affiche AccountsComponent
  { path: '', component: AccountsComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountsRoutingModule { }
