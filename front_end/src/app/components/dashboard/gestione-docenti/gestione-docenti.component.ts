import { Component, TemplateRef, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { Studente } from 'src/app/interface/studente.interface';
import { User } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/service/auth.service';
import { StudentiService } from 'src/app/service/studenti.service';

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
  studenti!: Studente[] | null;
  data!:string;

  constructor(private studenteSrv: StudentiService, private authSrv: AuthService) {
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
    this.studenteSrv.studenti$.subscribe((data) => {
      this.studenti = data;
    })
  }

  open(content: TemplateRef<any>, index?:number) {
    if (index !== undefined && this.studenti) {
      console.log(index, this.studenti[index])
      this.model.nome = this.studenti[index].nome;
      this.model.cognome = this.studenti[index].cognome;
      this.model.dataDiNascita = this.studenti[index].dataDiNascita;
      this.model.codiceFiscale = this.studenti[index].codiceFiscale;
      this.model.email = this.studenti[index].email;
      this.model.via = this.studenti[index].indirizzo.via;
      this.model.numeroCivico = this.studenti[index].indirizzo.numeroCivico;
      this.model.citta = this.studenti[index].indirizzo.citta;
      this.model.provincia = this.studenti[index].indirizzo.provincia;
      this.model.cap = this.studenti[index].indirizzo.cap;
      this.model.id = this.studenti[index].id
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
    this.studenteSrv.saveStudente(form.value).subscribe((data) => {
      console.log('Creato studente '+data)
    })
  }

  update(form: NgForm) {
    form.value.codiceIstituto = this.user?.istituto.codiceUnivoco
    console.log(form.value, this.model.id)
    this.studenteSrv.updateStudente(form.value, this.model.id).subscribe((data) => {
      console.log('Studente aggiornato')
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
