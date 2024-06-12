import { Component, TemplateRef, inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ModalDismissReasons, NgbDatepickerModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthData } from 'src/app/interface/auth-data.interface';
import { Studente } from 'src/app/interface/studente.interface';
import { User } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/service/auth.service';
import { StudentiService } from 'src/app/service/studenti.service';

@Component({
  selector: 'app-gestione-studenti',
  templateUrl: './gestione-studenti.component.html',
  styleUrls: ['./gestione-studenti.component.scss']
})
export class GestioneStudentiComponent {
  private modalService = inject(NgbModal);
  closeResult = '';
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

  open(content: TemplateRef<any>) {
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
