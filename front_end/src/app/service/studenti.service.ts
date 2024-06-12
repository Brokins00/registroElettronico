import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Studente } from '../interface/studente.interface';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class StudentiService {
  private generalApi = environment.generalApi;
  private studentiSub = new BehaviorSubject<Studente[] | null>(null);
  studenti$ = this.studentiSub.asObservable();
  constructor(private http: HttpClient, private router:Router, private authSrv: AuthService) {
    this.authSrv.user$.subscribe(data => {
      if (data == null) {
        this.studentiSub.next(null);
      }
    })
    this.getAllStudenti().subscribe((data2) => {
      console.log(data2)
      this.studentiSub.next(data2);
    });
  }

  private getAllStudenti() {
    return this.http.get<Studente[]>(`${this.generalApi}/studenti`)
  }

  saveStudente(data: {
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
    return this.http.post<number>(`${this.generalApi}/studenti`, data).pipe(
      tap(data => {
        this.getAllStudenti().subscribe((data2) => {
          this.studentiSub.next(data2);
        });
      })
    )
  }

  updateStudente(data:{
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
    return this.http.put<Studente>(`${this.generalApi}/studenti/${id}`, data).pipe(
      tap(data => {
        this.getAllStudenti().subscribe((data2) => {
          this.studentiSub.next(data2);
        })
      })
    )
  }
}
