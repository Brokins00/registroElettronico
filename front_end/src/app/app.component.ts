import { Component, OnInit } from '@angular/core';
import { AuthData } from './interface/auth-data.interface';
import { AuthService } from './service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'progetto_completo2';
  user: AuthData|null = null;
  constructor(private authSrv: AuthService, private router:Router) {}

  ngOnInit(): void {
      this.authSrv.restore();
      this.authSrv.user$.subscribe((value) => {
        this.user = value
        if (this.user !== null) {
          this.router.navigate(['/dashboard'])
        }
      })
  }
}
