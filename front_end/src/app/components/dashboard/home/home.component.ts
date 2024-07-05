import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { AnnoScolastico } from 'src/app/interface/anno-scolastico.interface';
import { Classe } from 'src/app/interface/classe.interface';
import { Docente } from 'src/app/interface/docente.interface';
import { Studente } from 'src/app/interface/studente.interface';
import { User } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/service/auth.service';
import { ClassiService } from 'src/app/service/classi.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  user!: User | undefined;
  docenti: Docente[] | null = null;
  studenti: Studente[] | null = null;
  classi: Classe[] | null = null;
  anni: AnnoScolastico[] | null = null;
  constructor(private router: Router, private authSrv: AuthService, private classeSrv: ClassiService, private cdr: ChangeDetectorRef) {
    
  }
  ngOnInit(): void {
    this.authSrv.user$.subscribe(data => {
      this.user = data?.user;
    })
    this.classeSrv.docenti$.subscribe((docenti) => {
      this.docenti = docenti;
    })
    this.classeSrv.studenti$.subscribe((studenti) => {
      this.studenti = studenti;
    })
    this.classeSrv.classi$.subscribe((classi) => {
      this.classi = classi;
    })
    this.classeSrv.anni$.subscribe((anni) => {
      this.anni = anni;
    })
    this.classeSrv.reload();
  }
}
