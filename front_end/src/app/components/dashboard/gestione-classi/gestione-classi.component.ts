import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnnoScolastico } from 'src/app/interface/anno-scolastico.interface';
import { Classe } from 'src/app/interface/classe.interface';
import { IndirizzoScolastico } from 'src/app/interface/indirizzo-scolastico.interface';
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
  user: User | undefined;
  annoSelezionato: number | null = null;
    inEdit = false;
    model : {
      inizio?: string,
      fine?: string,
      codiceIstituto?: string,
      festivita: {inizio: string, fine: string, descrizione: string}[],
    } = {
      festivita: []
    }

    model2 : {
      nome?: string,
      indirizzo?: string,
      studenti: number
    } = {
      studenti: 0
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
        })
    }

    selectYear(anno: string | null) {
      console.log(anno)
      this.annoSelezionato = Number(anno);
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
      // form.value.codiceIstituto = this.user?.istituto.codiceUnivoco
      // console.log(form.value, this.model.id)
      // this.studenteSrv.updateStudente(form.value, this.model.id).subscribe((data) => {
      //   console.log('Docente aggiornato')
      // })
    }

    openAnno(content: TemplateRef<any>, index?:number) {
      // if (index !== undefined && this.docenti) {
      //   this.model.inizio = this.docenti[index].nome;
      //   this.model.cognome = this.docenti[index].cognome;
      //   this.model.dataDiNascita = this.docenti[index].dataDiNascita;
      //   this.model.codiceFiscale = this.docenti[index].codiceFiscale;
      //   this.model.email = this.docenti[index].email;
      //   this.model.via = this.docenti[index].indirizzo.via;
      //   this.model.numeroCivico = this.docenti[index].indirizzo.numeroCivico;
      //   this.model.citta = this.docenti[index].indirizzo.citta;
      //   this.model.provincia = this.docenti[index].indirizzo.provincia;
      //   this.model.cap = this.docenti[index].indirizzo.cap;
      //   this.model.id = this.docenti[index].id
      //   this.inEdit = true;
      // } else {
      //   this.inEdit = false;
      //   this.model = {}
      // }
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
      // if (index !== undefined && this.docenti) {
      //   this.model.inizio = this.docenti[index].nome;
      //   this.model.cognome = this.docenti[index].cognome;
      //   this.model.dataDiNascita = this.docenti[index].dataDiNascita;
      //   this.model.codiceFiscale = this.docenti[index].codiceFiscale;
      //   this.model.email = this.docenti[index].email;
      //   this.model.via = this.docenti[index].indirizzo.via;
      //   this.model.numeroCivico = this.docenti[index].indirizzo.numeroCivico;
      //   this.model.citta = this.docenti[index].indirizzo.citta;
      //   this.model.provincia = this.docenti[index].indirizzo.provincia;
      //   this.model.cap = this.docenti[index].indirizzo.cap;
      //   this.model.id = this.docenti[index].id
      //   this.inEdit = true;
      // } else {
      //   this.inEdit = false;
      //   this.model = {}
      // }
      this.modalService.open(content, {ariaLabelledBy: 'creazione-classe'}).result.then((result) => {
      
      }, (reason) => {
        
      });
    }
}
