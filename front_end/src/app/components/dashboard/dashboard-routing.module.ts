import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { AuthGuard } from 'src/app/guard/auth.guard';
import { GestioneStudentiComponent } from './gestione-studenti/gestione-studenti.component';
import { HomeComponent } from './home/home.component';
import { GestioneDocentiComponent } from './gestione-docenti/gestione-docenti.component';
import { GestioneClassiComponent } from './gestione-classi/gestione-classi.component';

const routes: Routes = [
  { path: '',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
      { path: 'studenti', component: GestioneStudentiComponent, canActivate: [AuthGuard]},
      { path: 'docenti', component: GestioneDocentiComponent, canActivate: [AuthGuard]},
      { path: 'classi', component: GestioneClassiComponent, canActivate: [AuthGuard]}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
