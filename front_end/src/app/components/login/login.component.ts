import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    codiceScuola: string = "";
    username: string = "";
    password: string = "";

    constructor(private authSrv: AuthService, private route: Router) {}

    areInputEmpty():boolean {
      return !((this.codiceScuola.length > 0) && (this.username.length > 0) && (this.password.length > 0))
    }

    submit() {
      try {
        console.log(this.codiceScuola)
        this.authSrv.login({codiceIstituto: this.codiceScuola, email: this.username, password: this.password}).subscribe((data) => {
          console.log(data)
          alert("Login avvenuto con successo")
        })
      } catch(error) {
        console.error(error)
      }
    }
}
