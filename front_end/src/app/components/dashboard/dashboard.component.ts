import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  user!: User | undefined;
  constructor(private router: Router, private authSrv: AuthService) {
    
  }
  ngOnInit(): void {
    // this.router.navigate(['/dashboard/home']);
    this.authSrv.user$.subscribe(data => {
      this.user = data?.user;
    })
  }

  logout() {
    this.authSrv.logout();
  }

}
