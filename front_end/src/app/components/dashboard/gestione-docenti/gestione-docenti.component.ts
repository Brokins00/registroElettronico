import { Component, TemplateRef, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Docente } from 'src/app/interface/docente.interface';
import { User } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/service/auth.service';
import { DocentiService } from 'src/app/service/docenti.service';

@Component({
  selector: 'app-gestione-docenti',
  templateUrl: './gestione-docenti.component.html',
  styleUrls: ['./gestione-docenti.component.scss']
})
export class GestioneDocentiComponent {
  private modalService = inject(NgbModal);
  closeResult = '';
  model: {
    id?: number,
    nome?: string,
    cognome?: string,
    dataDiNascita?: Date,
    codiceFiscale?: string,
    email?: string,
    password?: string,
    via?: string,
    numeroCivico?: string,
    citta?: string,
    provincia?: string,
    cap?: string
  } = {};
  inEdit: boolean = false;
  private user!: User | undefined;
  docenti!: Docente[] | null;
  data!:string;

  constructor(private docenteSrv: DocentiService, private authSrv: AuthService) {
    let today = new Date();
    let dd:number|string = today.getDate();
    let mm:number|string = today.getMonth()+1;
    let yyyy = today.getFullYear();

    if (dd<10) {
      dd="0"+dd;
    }

    if (mm < 10) {
      mm = "0"+mm;
    }
    this.data = yyyy+"-"+mm+"-"+dd;
    this.authSrv.user$.subscribe(data => {
      this.user = data?.user;
    })
    this.docenteSrv.docenti$.subscribe((data) => {
      this.docenti = data;
    })
  }

  open(content: TemplateRef<any>, index?:number) {
    if (index !== undefined && this.docenti) {
      console.log(index, this.docenti[index])
      this.model.nome = this.docenti[index].nome;
      this.model.cognome = this.docenti[index].cognome;
      this.model.dataDiNascita = this.docenti[index].dataDiNascita;
      this.model.codiceFiscale = this.docenti[index].codiceFiscale;
      this.model.email = this.docenti[index].email;
      this.model.via = this.docenti[index].indirizzo.via;
      this.model.numeroCivico = this.docenti[index].indirizzo.numeroCivico;
      this.model.citta = this.docenti[index].indirizzo.citta;
      this.model.provincia = this.docenti[index].indirizzo.provincia;
      this.model.cap = this.docenti[index].indirizzo.cap;
      this.model.id = this.docenti[index].id
      this.inEdit = true;
    } else {
      this.inEdit = false;
      this.model = {}
    }
    this.modalService.open(content, {ariaLabelledBy: 'creazione-utenti'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  onSubmit(form: NgForm) {
    form.value.codiceIstituto = this.user?.istituto.codiceUnivoco
    this.docenteSrv.saveDocente(form.value).subscribe((data) => {
      console.log('Creato docente '+data)
    })
  }

  update(form: NgForm) {
    form.value.codiceIstituto = this.user?.istituto.codiceUnivoco
    console.log(form.value, this.model.id)
    this.docenteSrv.updateDocente(form.value, this.model.id).subscribe((data) => {
      console.log('Docente aggiornato')
    })
  }

  private getDismissReason(reason: any): string {
		switch (reason) {
			case ModalDismissReasons.ESC:
				return 'by pressing ESC';
			case ModalDismissReasons.BACKDROP_CLICK:
				return 'by clicking on a backdrop';
			default:
				return `with: ${reason}`;
		}
	}
}
