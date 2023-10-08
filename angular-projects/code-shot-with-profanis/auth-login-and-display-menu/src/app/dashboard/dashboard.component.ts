import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private _authService: AuthService) { }

  ngOnInit(): void {
  }

  hasRole(role: string) {
    return this._authService.user.roles?.includes(role);
  }

}
