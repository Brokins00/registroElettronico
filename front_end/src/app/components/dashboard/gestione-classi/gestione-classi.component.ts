import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnnoScolastico } from 'src/app/interface/anno-scolastico.interface';
import { Classe } from 'src/app/interface/classe.interface';
import { Docente } from 'src/app/interface/docente.interface';
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
  docenti: Docente[] | null = null;
  filteredStudenti: Studente[] | null = null;
  filteredStudenti2: Studente[] | null = null;
  filteredDocenti: Docente[] | null = null;
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
      studenti?: Studente[],
      docenti?: Docente[]
    } = {
      festivita: []
    }

    model2 : {
      id?: number,
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
          this.annoSelezionato = null;
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

        this.classeSrv.docenti$.subscribe((docenti) => {
          this.docenti = docenti;
          this.filteredDocenti = [];
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
      if (this.anni && this.annoSelezionato != null) {
        form.value.codiceIstituto = this.user?.istituto.codiceUnivoco;
        form.value.annoScolastico = this.anni[this.annoSelezionato].id;
        this.classeSrv.saveClasse(form.value).subscribe((data) => {
          this.classeSrv.reload();
          console.log('Classe create con id '+data)
        })
      }
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

    deleteClasse(form: NgForm) {
      if (this.model2.id) {
        let conferma = confirm('Sei sicuro di voler eliminare questa classe?')
        if (conferma) {
          this.classeSrv.deleteClasse(this.model2.id)
        }
      }
    }

    addStudenteToClasse(id: string) {
      let idNumero = Number(id);
      let found = false;
      this.studenti?.forEach((studente, index) => {
        if (found) return;
        if (!this.filteredStudenti2) return;
        if (studente.id == this.filteredStudenti2[idNumero].id) {
          found = true
          idNumero = index
        }
      })
      if (!isNaN(idNumero) && this.studenti && this.studenti[idNumero]) {
        this.model2.studenti?.push(this.studenti[idNumero])
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
              let found3 = false
              this.anni[this.annoSelezionato].studenti.forEach(studenteAnno => {
                if (studenteAnno.id == studente2.id) {
                  found3 = true
                }
              })
              if (!found3) return;
              this.anni[this.annoSelezionato].classi?.forEach((classeAnno) => {
                classeAnno.studenti.forEach((studenteClasse) => {
                  if (studenteClasse.id == studente2.id) {
                    found2 = true
                  }
                })
              })
              if (found2) {
                return
              }
            }
            if (!found) {
              this.filteredStudenti2?.push(studente2)
            }
          }
        })
      }
    }

    onSubmitAnno(form: NgForm) {
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

    addDocenteToAnno(id: string) {
      let idNumero = Number(id);
      let found = false;
      this.docenti?.forEach((docente, index) => {
        if (found) return;
        if (docente.id == idNumero) {
          found = true;
          idNumero = index
        }
      })
      if (!isNaN(idNumero) && this.docenti && this.docenti[idNumero]) {
        console.log('arrivato', this.model.docenti)
        this.model.docenti?.push(this.docenti[idNumero])
        this.filteredDocenti = []
        this.docenti.forEach((docente2) => {
          if (this.anni && this.model.docenti !== null) {
            let found2 = false;
            this.model.docenti?.forEach((docente) => {
              if (docente.id == docente2.id) {
                found2 = true
              }
            })
            if (!found2) {
              this.filteredDocenti?.push(docente2);
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
        this.model.docenti = [...this.anni[index].docenti]
        this.filteredStudenti = []
        this.filteredDocenti = []
        this.studenti?.forEach((studente2) => {
          if (this.anni && this.model.studenti !== null) {
            let found = false;
            this.model.studenti?.forEach((studente) => {
              if (studente.id == studente2.id) {
                found = true
              }
            })
            if (!found) {
              this.filteredStudenti?.push(studente2)
            }
          }
        })
        this.docenti?.forEach((docente2) => {
          if (this.anni && this.model.docenti !== null) {
            let found = false;
            this.model.docenti?.forEach((docente) => {
              if (docente.id == docente2.id) {
                found = true
              }
            })
            if (!found) {
              this.filteredDocenti?.push(docente2)
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
        this.model2.id = this.classi[index].id;
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
              let found3 = false
              this.anni[this.annoSelezionato].studenti.forEach(studenteAnno => {
                console.log(studenteAnno.id)
                if (studenteAnno.id == studente2.id) {
                  found3 = true
                }
              })
              if (!found3) return;
              this.anni[this.annoSelezionato].classi?.forEach((classeAnno) => {
                classeAnno.studenti.forEach((studenteClasse) => {
                  if (studenteClasse.id == studente2.id) {
                    found2 = true
                  }
                })
              })
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
