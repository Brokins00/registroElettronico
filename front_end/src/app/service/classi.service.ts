import { Injectable } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { AnnoScolastico } from '../interface/anno-scolastico.interface';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { Festivita } from '../interface/festivita.interface';
import { Indirizzo } from '../interface/indirizzo.interface';
import { IndirizzoScolastico } from '../interface/indirizzo-scolastico.interface';
import { Classe } from '../interface/classe.interface';

@Injectable({
  providedIn: 'root'
})
export class ClassiService {
  private generalApi = environment.generalApi;
  private anniSub = new BehaviorSubject<AnnoScolastico[] | null>(null)
  anni$ = this.anniSub.asObservable();
  private indirizziSub = new BehaviorSubject<IndirizzoScolastico[] | null>(null)
  indiscol$=this.indirizziSub.asObservable();
  private classeSub = new BehaviorSubject<Classe[] | null>(null)
  classi$=this.classeSub.asObservable();
  constructor(private http: HttpClient, private router:Router, private authSrv: AuthService) {
    this.authSrv.user$.subscribe(data => {
      if (data == null) {
        this.anniSub.next(null);
      }
    })
    this.getAllAnni().subscribe((data2: AnnoScolastico[]) => {
      this.anniSub.next(data2);
    });
    this.getAllIndirizzi().subscribe((data2: IndirizzoScolastico[]) => {
      this.indirizziSub.next(data2);
    })
    this.getAllClassi().subscribe((data2: Classe[]) => {
      this.classeSub.next(data2);
    })
  }

  private getAllIndirizzi() {
    return this.http.get<IndirizzoScolastico[]>(`${this.generalApi}/indirizzi-scolastici`)
  }

  private getAllClassi() {
    return this.http.get<Classe[]>(`${this.generalApi}/classi`)
  }

  private getAllAnni() {
    return this.http.get<AnnoScolastico[]>(`${this.generalApi}/anni-scolastici`)
  }

  saveAnno(data: {
    inizio?: string,
    fine?: string,
    festivita: Festivita[]
  }) {
    return this.http.post<number>(`${this.generalApi}/anni-scolastici`, data).pipe(
      tap(data3 => {
        if (data3) {
          data.festivita.forEach(festivita => {
            festivita.annoScolastico = data3;
            this.saveFestivita(festivita)
          })
        }
        this.getAllAnni().subscribe((data2) => {
          this.anniSub.next(data2);
        });
      })
    )
  }

  saveClasse(data: {
    nome: string,
    indirizzo: number,
    codiceIstituto: string,
    annoScolastico: string
  }) {
    return this.http.post<number>(`${this.generalApi}/classi`, data)
  }

  saveIndirizzoScolastico(data: {
    nomeIndirizzo: string,
    codiceIstituto: string
  }) {
    return this.http.post<number>(`${this.generalApi}/indirizzi-scolastici`, data)
  }

  saveFestivita(data:Festivita) {
    return this.http.post<number>(`${this.generalApi}/festivita`, data)
  }
}
