import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnnoScolastico } from 'src/app/interface/anno-scolastico.interface';
import { Classe } from 'src/app/interface/classe.interface';
import { IndirizzoScolastico } from 'src/app/interface/indirizzo-scolastico.interface';
import { Studente } from 'src/app/interface/studente.interface';
import { User } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/service/auth.service';
import { ClassiService } from 'src/app/service/classi.service';

@Component({
  selector: 'app-gestione-classi',
  templateUrl: './gestione-classi.component.html',
  styleUrls: ['./gestione-classi.component.scss']
})
export class GestioneClassiComponent implements OnInit {
  private modalService = inject(NgbModal);
  anni: AnnoScolastico[] | null = null;
  indirizzi: IndirizzoScolastico[] | null = null;
  classi: Classe[] | null = null;
  filteredClassi: Classe[] = [];
  studenti: Studente[] | null = null;
  filteredStudenti: Studente[] | null = null;
  filteredStudenti2: Studente[] | null = null;
  user: User | undefined;
  annoSelezionato: number | null = null;
  classeSelezionata: number | null = null;
    inEdit = false;
    inEditClasse = false;
    inEditAnno = false;
    model : {
      inizio?: string,
      fine?: string,
      codiceIstituto?: string,
      festivita: {inizio: string, fine: string, descrizione: string}[],
      studenti?: Studente[]
    } = {
      festivita: []
    }

    model2 : {
      nome?: string,
      indirizzo?: string,
      studenti: Studente[]
    } = {
      studenti: []
    }

    model3 : {
      inizio?: Date,
      fine?: Date,
      descrizione?: string
    } = {}

    constructor(private classeSrv: ClassiService, private authSrv: AuthService) {}

    ngOnInit(): void {
        this.classeSrv.anni$.subscribe((anni) => {
          this.anni = anni;
        })

        this.authSrv.user$.subscribe((user) => {
            this.user = user?.user;
        })

        this.classeSrv.indiscol$.subscribe((indirizzi) => {
          this.indirizzi = indirizzi;
        })
  
        this.classeSrv.classi$.subscribe((classi) => {
          this.classi = classi;
          this.filteredClassi = [];
        })
        
        this.classeSrv.studenti$.subscribe((studenti) => {
          this.studenti = studenti;
          this.filteredStudenti = [];
          
        })
      }
      
    selectYear(anno: string | null) {
      this.annoSelezionato = Number(anno);
      if (isNaN(this.annoSelezionato)) {
        this.annoSelezionato = null;
        this.filteredClassi = []
      } else {
        this.filteredClassi = []
        if (this.anni) {
          this.anni[this.annoSelezionato].classi?.forEach((classe) => {
            this.filteredClassi.push(classe)
          })
        }
      }
    }

    onSubmitFestivita(form: NgForm) {
      this.model.festivita.push(form.value);
    }

    removeFestivita(index:number) {
      this.model.festivita.splice(index, 1)
    }

    updateFestivita(form: NgForm) {

    }

    onSubmitIndirizzo(form: NgForm) {
      form.value.codiceIstituto = this.user?.istituto.codiceUnivoco;

      this.classeSrv.saveIndirizzoScolastico(form.value).subscribe((data) => {
        console.log('Creato Indirizzo Scolastico con id '+data)
      });
    }

    updateIndirizzo(form: NgForm) {

    }

    onSubmitClasse(form: NgForm) {
      form.value.codiceIstituto = this.user?.istituto.codiceUnivoco;
      form.value.annoScolastico = this.annoSelezionato;
      this.classeSrv.saveClasse(form.value).subscribe((data) => {
        console.log('Classe create con id '+data)
      })
    }

    updateClasse(form: NgForm) {
      if (this.anni && this.annoSelezionato !== null && this.classeSelezionata !== null) {
        let data: {
          nome: string | undefined,
          indirizzo: number,
          studenti: Studente[],
          codiceIstituto: string | undefined,
          annoScolastico: number | undefined
        } = {
          nome: this.model2.nome,
          indirizzo: Number(this.model2.indirizzo),
          studenti: this.model2.studenti,
          codiceIstituto: this.user?.istituto.codiceUnivoco,
          annoScolastico: this.anni[this.annoSelezionato].id
        }
        this.classeSrv.updateClasse(data, this.anni[this.annoSelezionato].classi[this.classeSelezionata].id).subscribe((data) => {
        })
      }
    }

    addStudenteToClasse(id: string) {
      let idNumero = Number(id);
      let found = false;
      this.studenti?.forEach((studente, index) => {
        if (found) return;
        if (studente.id == idNumero) {
          found = true
          idNumero = index
        }
      })
      if (!isNaN(idNumero) && this.studenti && this.studenti[idNumero]) {
        this.model2.studenti?.push(this.studenti[idNumero])
        this.filteredStudenti2 = []
        this.studenti?.forEach((studente2) => {
          if (this.anni && this.model2.studenti !== null) {
            let found2 = false;
            this.model2.studenti?.forEach((studente) => {
              if (studente.id == studente2.id) {
                found2 = true
              }
            })
            if (!found2) {
              this.filteredStudenti?.push(studente2)
            }
          }
        })
      }
    }

    onSubmitAnno(form: NgForm) {
      console.log(this.model)
      this.model.codiceIstituto = this.user?.istituto.codiceUnivoco;
      this.classeSrv.saveAnno(this.model).subscribe((data) => {
        console.log('Creato anno scolastico con id '+data)
      })
      // form.value.codiceIstituto = this.user?.istituto.codiceUnivoco
      // this.studenteSrv.saveStudente(form.value).subscribe((data) => {
      //   console.log('Creato docente '+data)
      // })
    }
  
    updateAnno(form: NgForm) {
      this.model.codiceIstituto = this.user?.istituto.codiceUnivoco;
      if (this.anni && this.annoSelezionato !== null) {
        this.classeSrv.updateAnno(this.model, this.anni[this.annoSelezionato].id).subscribe((data) => {
        })
      }
    }
    addStudenteToAnno(id: string) {
      let idNumero = Number(id);
      let found = false;
      this.studenti?.forEach((studente, index) => {
        if (found) return;
        if (studente.id == idNumero) {
          found = true
          idNumero = index
        }
      })
      if (!isNaN(idNumero) && this.studenti && this.studenti[idNumero]) {
        console.log(this.studenti[idNumero])
        this.model.studenti?.push(this.studenti[idNumero])
        this.filteredStudenti = []
        this.studenti?.forEach((studente2) => {
          if (this.anni && this.model.studenti !== null) {
            let found2 = false;
            this.model.studenti?.forEach((studente) => {
              if (studente.id == studente2.id) {
                found2 = true
              }
            })
            if (!found2) {
              this.filteredStudenti?.push(studente2)
            }
          }
        })
      }
    }

    openAnno(content: TemplateRef<any>, index?:number) {
      if (index !== undefined && this.anni) {
        this.model.festivita = this.anni[index].festivita;
        this.model.inizio = this.anni[index].inizio;
        this.model.fine = this.anni[index].fine;
        this.model.studenti = [...this.anni[index].studenti];
        this.filteredStudenti = []
        this.studenti?.forEach((studente2) => {
          if (this.anni && this.model.studenti !== null) {
            let found = false;
            this.model.studenti?.forEach((studente) => {
              if (studente.id == studente2.id) {
                found = true
              }
            })
            if (this.anni) {
              let found2 = false
              this.anni.forEach((anno) => {
                let studentiAnno = anno.studenti;
                studentiAnno.forEach((studenteAnno) => {
                  if (studenteAnno.id == studente2.id) {
                    found2 = true
                  }
                })
              })
              if (found2) {
                return
              }
            }
            if (!found) {
              this.filteredStudenti?.push(studente2)
            }
          }
        })
        this.inEditAnno = true;
      } else {
        this.inEditAnno = false;
        this.model = {
          festivita: []
        }
      }
      this.modalService.open(content, {ariaLabelledBy: 'creazione-anno'}).result.then((result) => {
      
      }, (reason) => {
        
      });
    }

    openFestivita(content: TemplateRef<any>, index?: number) {
      this.modalService.open(content, {ariaLabelledBy: 'creazione-festivita'}).result.then((result) => {

      }, (reason) => {

      })
    }
    openIndirizzo(content: TemplateRef<any>, index?: number) {
      this.modalService.open(content, {ariaLabelledBy: 'creazione-indirizzo'}).result.then((result) => {

      }, (reason) => {

      })
    }

    openClasse(content: TemplateRef<any>, index?:number) {
      if (index !== undefined && this.classi && this.anni && this.annoSelezionato !== null) {
        this.classeSelezionata = index;
        this.model2.nome = this.classi[index].nome;
        this.model2.indirizzo = this.classi[index].indirizzo.id?.toString();
        this.model2.studenti = [...this.anni[this.annoSelezionato].classi[index].studenti];
        this.filteredStudenti2 = []
        this.studenti?.forEach((studente2) => {
          if (this.anni && this.model2.studenti !== null) {
            let found = false;
            this.model2.studenti?.forEach((studente) => {
              if (studente.id == studente2.id) {
                found = true
              }
            })
            if (this.anni && this.annoSelezionato !== null) {
              let found2 = false
              this.anni[this.annoSelezionato].classi?.forEach((classeAnno) => {
                classeAnno.studenti.forEach((studenteClasse) => {
                  if (studenteClasse.id == studente2.id) {
                    found2 = true
                  }
                })
              })
              console.log(studente2.nome, found2)
              if (found2) {
                return
              }
            }
            if (!found) {
              this.filteredStudenti2?.push(studente2)
            }
          }
        })
        this.inEditClasse = true;
      } else {
        this.inEditClasse = false;
        this.classeSelezionata = null;
        this.model2 = {
          studenti: []
        }
      }
      this.modalService.open(content, {ariaLabelledBy: 'creazione-classe'}).result.then((result) => {
      
      }, (reason) => {
        
      });
    }
}
