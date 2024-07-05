import { Component, inject } from '@angular/core';
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
  selector: 'app-valutazioni-studente',
  templateUrl: './valutazioni-studente.component.html',
  styleUrls: ['./valutazioni-studente.component.scss']
})
export class ValutazioniStudenteComponent {
  anni: AnnoScolastico[] | null = null;
  annoSelezionato: number | null = null;

  valutazioni: Valutazione[] | null = null;

  user: User | undefined;

  constructor(private classeSrv: ClassiService, private authSrv: AuthService) {}

  ngOnInit(): void {
    this.classeSrv.anni$.subscribe((anni) => {
      this.anni = anni;
      this.annoSelezionato = null;
    })

    this.authSrv.user$.subscribe((user) => {
        this.user = user?.user;
    })
  }

  selectYear(anno: string | null) {
    this.annoSelezionato = Number(anno);
    if (isNaN(this.annoSelezionato)) {
      this.annoSelezionato = null;
    } else {
      if (this.anni) {
        this.classeSrv.getValutazioniByStudenteAndAnno(this.user?.id as number, this.anni[this.annoSelezionato].id as number).subscribe((data) => {
          this.valutazioni = data;
        })
      }
    }
  }
}
