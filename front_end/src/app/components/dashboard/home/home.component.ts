import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  user!: User | undefined;
  constructor(private router: Router, private authSrv: AuthService) {
    
  }
  ngOnInit(): void {
    this.authSrv.user$.subscribe(data => {
      this.user = data?.user;
    })
  }
}
