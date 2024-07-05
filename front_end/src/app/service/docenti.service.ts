import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Docente } from '../interface/docente.interface';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class DocentiService {
  private generalApi = environment.generalApi;
  private docentiSub = new BehaviorSubject<Docente[] | null>(null);
  docenti$ = this.docentiSub.asObservable();
  constructor(private http: HttpClient, private router:Router, private authSrv: AuthService) {
    this.authSrv.user$.subscribe(data => {
      if (data == null) {
        this.docentiSub.next(null);
      }
    })
    this.getAllDocenti().subscribe((data2) => {
      console.log(data2)
      this.docentiSub.next(data2);
    });
  }

  private getAllDocenti() {
    return this.http.get<Docente[]>(`${this.generalApi}/docenti`)
  }

  saveDocente(data: {
    nome: string,
    cognome: string,
    dataDiNascita: Date,
    codiceFiscale: string,
    email: string,
    password: string,
    codiceIstituto: string,
    via: string,
    numeroCivico: string,
    citta: string,
    provincia: string,
    cap: string
  }) {
    return this.http.post<number>(`${this.generalApi}/docenti`, data).pipe(
      tap(data => {
        this.getAllDocenti().subscribe((data2) => {
          this.docentiSub.next(data2);
        });
      })
    )
  }

  updateDocente(data:{
    nome: string,
    cognome: string,
    dataDiNascita: Date,
    codiceFiscale: string,
    email: string,
    password: string,
    codiceIstituto: string,
    via: string,
    numeroCivico: string,
    citta: string,
    provincia: string,
    cap: string
  }, id: number | undefined) {
    return this.http.put<Docente>(`${this.generalApi}/docenti/${id}`, data).pipe(
      tap(data => {
        this.getAllDocenti().subscribe((data2) => {
          this.docentiSub.next(data2);
        })
      })
    )
  }
}
