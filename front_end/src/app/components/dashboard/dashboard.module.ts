import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { GestioneStudentiComponent } from './gestione-studenti/gestione-studenti.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { GestioneDocentiComponent } from './gestione-docenti/gestione-docenti.component';
import { GestioneClassiComponent } from './gestione-classi/gestione-classi.component';
import { ValutazioniComponent } from './valutazioni/valutazioni.component';
import { CapitalizePipe } from 'src/app/pipe/capitalize.pipe';
import { ValutazioniStudenteComponent } from './valutazioni-studente/valutazioni-studente.component';


@NgModule({
  declarations: [
    DashboardComponent,
    GestioneStudentiComponent,
    HomeComponent,
    NavbarComponent,
    GestioneDocentiComponent,
    GestioneClassiComponent,
    ValutazioniComponent,
    CapitalizePipe,
    ValutazioniStudenteComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    NgbDatepickerModule
  ]
})
export class DashboardModule { }
