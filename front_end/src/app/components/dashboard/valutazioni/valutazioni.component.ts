import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnnoScolastico } from 'src/app/interface/anno-scolastico.interface';
import { Classe } from 'src/app/interface/classe.interface';
import { IndirizzoScolastico } from 'src/app/interface/indirizzo-scolastico.interface';
import { Materia } from 'src/app/interface/materia.interface';
import { Studente } from 'src/app/interface/studente.interface';
import { User } from 'src/app/interface/user.interface';
import { Valutazione } from 'src/app/interface/valutazione.interface';
import { AuthService } from 'src/app/service/auth.service';
import { ClassiService } from 'src/app/service/classi.service';

@Component({
  selector: 'app-valutazioni',
  templateUrl: './valutazioni.component.html',
  styleUrls: ['./valutazioni.component.scss']
})
export class ValutazioniComponent implements OnInit {
  private modalService = inject(NgbModal);
  
  anni: AnnoScolastico[] | null = null;
  annoSelezionato: number | null = null;
  indirizzi: IndirizzoScolastico[] | null = null;

  classi: Classe[] | null = null;
  classeSelezionata: number | null = null;
  filteredClassi: Classe[] = [];

  studenti: Studente[] | null = null;
  filteredStudenti: Studente[] = [];
  studenteSelezionato: number | null = null;

  valutazioni: Valutazione[] | null = null;
  materie: Materia[] | null = null;
  model: {
    voto?: number,
    tipologia?: string,
    descrizione?: string,
    data?: string,
    annoScolasticoId?: number,
    studenteId?: number,
    docenteId?: number,
    materiaId?: number
  } = {
    materiaId: -1,
    voto: -1,
    tipologia: 'cortesia'
  };

  user: User | undefined;

  constructor(private classeSrv: ClassiService, private authSrv: AuthService) {}

  ngOnInit(): void {
    console.log('entrato')
    this.classeSrv.anni$.subscribe((anni) => {
      console.log('anni', anni)
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

    setTimeout(() => {
      this.classeSrv.reload()
    }, 200)
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

  selectClasse(classe: string | null) {
    this.classeSelezionata = Number(classe);
    if (isNaN(this.classeSelezionata)) {
      this.classeSelezionata = null;
      this.filteredStudenti = []
    } else {
      this.filteredStudenti = [];
      if (this.classi) {
        this.classi[this.classeSelezionata].studenti.forEach((studente) => {
          this.filteredStudenti.push(studente);
        })
        this.materie = this.classi[this.classeSelezionata].indirizzo.materie;
      }
    }
  }
  openStudente(content: TemplateRef<any>, index?:number) {
    if (index == null) return;
    let studente = this.filteredStudenti[Number(index)];
    if (!studente || studente.id == undefined) return;
    this.studenteSelezionato = Number(index);
    if (this.annoSelezionato == null || this.anni == null) return;
    let annoId = this.anni[this.annoSelezionato].id;
    if (annoId == undefined) return;
    this.classeSrv.getValutazioniByStudenteAndAnno(studente.id, annoId).subscribe((valutazioni: Valutazione[]) => {
      this.valutazioni = valutazioni;
      this.modalService.open(content, {ariaLabelledBy: 'valutazione', size: 'xl'}).result.then((result) => {
        // Gestione del risultato della modale
      }, (reason) => {
        // Gestione della chiusura della modale
      });
    });
  }
  aggiungiValutazione(content: TemplateRef<any>) {
    this.modalService.open(content, {ariaLabelledBy: 'crea-valutazione', size: 'xl'}).result.then((result) => {
      
    }, (reason) => {
      
    });
  }
  saveValutazione() {
    if (this.studenti == null || this.studenteSelezionato == null || this.anni == null || this.annoSelezionato == null) return;
    this.model.materiaId = Number(this.model.materiaId);
    this.model.studenteId = this.studenti[this.studenteSelezionato].id;
    this.model.docenteId = this.user?.id;
    this.model.voto = Number(this.model.voto)
    this.model.annoScolasticoId = this.anni[this.annoSelezionato].id
    this.classeSrv.saveValutazione(this.model).subscribe((data) => {
      console.log('valutazione con id '+data+" creata")
    })
  }
}
