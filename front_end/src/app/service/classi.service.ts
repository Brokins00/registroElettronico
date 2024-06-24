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
import { Studente } from '../interface/studente.interface';

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
  private studenteSub = new BehaviorSubject<Studente[] | null>(null)
  studenti$ = this.studenteSub.asObservable();
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
    this.getAllStudenti().subscribe((data2: Studente[]) => {
      this.studenteSub.next(data2);
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

  private getAllStudenti() {
    return this.http.get<Studente[]>(`${this.generalApi}/studenti`)
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
            this.saveFestivita(festivita).subscribe();
          })
        }
        this.getAllAnni().subscribe((data2) => {
          this.anniSub.next(data2);
        });
      })
    )
  }

  deleteClasse(data: number) {
    this.http.delete(`${this.generalApi}/classi/${data}`).subscribe(() => {
      this.reload();
    })
  }

  updateClasse(data: {
    nome: string|undefined,
    indirizzo: number,
    studenti: Studente[],
    codiceIstituto: string | undefined,
    annoScolastico: number | undefined
  }, id: number | undefined) {
    console.log(data, id)
    return this.http.put<Classe>(`${this.generalApi}/classi/${id}`, data).pipe(
      tap(data3 => {
        if (data3) {
          let nuoviStudenti: number[] = []
          if (data.studenti) {
            data.studenti.forEach(studente => {
              let found = false;
              data3.studenti.forEach(studente2 => {
                if (studente2.id == studente.id) {
                  found = true
                }
              })
              if (!found) {
                if (studente.id) {
                  nuoviStudenti.push(studente.id)
                }
              }
            })
          }
          if (data3.id && nuoviStudenti.length > 0) {
            this.patchClasseStudenti(nuoviStudenti, data3.id).subscribe(() => {
              this.reload();
            });
          }
        }
      })
    )
  }

  updateAnno(data: {
    inizio?: string,
    fine?: string,
    festivita?: Festivita[],
    studenti?: Studente[]
  }, id: number|undefined) {
    return this.http.put<AnnoScolastico>(`${this.generalApi}/anni-scolastici/${id}`, data).pipe(
      tap(data3 => {
        if (data3) {
          if (data.festivita) {
            data.festivita.forEach(festivita => {
              festivita.annoScolastico = data3.id;
              this.updateFestivita(festivita).subscribe()
            })
          }
          let nuoviStudenti: number[] = []
          if (data.studenti) {
            data.studenti.forEach(studente => {
              let found = false;
              data3.studenti.forEach(studente2 => {
                if (studente2.id == studente.id) {
                  found = true
                }
              })
              if (!found) {
                if (studente.id) {
                  nuoviStudenti.push(studente.id)
                }
              }
            })
          }
          if (data3.id && nuoviStudenti.length > 0) {
            this.patchAnnoStudenti(nuoviStudenti, data3.id).subscribe(() => {
              this.reload();
            });
          }
        }
      })
    )
  }

  reload() {
    this.getAllClassi().subscribe((data2: Classe[]) => {
      this.classeSub.next(data2);
    });
    this.getAllIndirizzi().subscribe((data2: IndirizzoScolastico[]) => {
      this.indirizziSub.next(data2);
    });
    this.getAllStudenti().subscribe((data2: Studente[]) => {
      this.studenteSub.next(data2);
    })
    this.getAllAnni().subscribe((data2: AnnoScolastico[]) => {
      this.anniSub.next(data2);
    });
  }

  patchAnnoStudenti(studenti: number[], id: number) {
    return this.http.patch<AnnoScolastico>(`${this.generalApi}/anni-scolastici/${id}/studenti`, {studenti: studenti})
  }

  patchClasseStudenti(studenti: number[], id: number) {
    return this.http.patch<Classe>(`${this.generalApi}/classi/${id}/studenti`, {studenti: studenti})
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

  updateFestivita(data: Festivita) {
    return this.http.put<Festivita>(`${this.generalApi}/festivita`, data);
  }
}
