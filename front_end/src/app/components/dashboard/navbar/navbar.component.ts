import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  user!: User | undefined;
  constructor(private router: Router, private authSrv: AuthService) {
    
  }
  ngOnInit(): void {
    this.authSrv.user$.subscribe(data => {
      this.user = data?.user;
    })
  }

  logout() {
    this.authSrv.logout();
  }
}
