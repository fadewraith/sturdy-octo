import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakService } from 'src/app/services/keycloak/keycloak.service';
import {
  AuthenticationRequest,
  AuthenticationResponse,
} from 'src/app/services/models';
import { AuthenticationService } from 'src/app/services/services';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  // since using keycloak, no need for these
  // authRequest: AuthenticationRequest = { email: '', password: '' };
  // errorMsg: Array<string> = [];

  constructor(
    // private router: Router,
    // private authService: AuthenticationService,
    // private tokenService: TokenService,
    private keycloakService: KeycloakService
  ) {}

  async ngOnInit(): Promise<void> {
    await this.keycloakService.init();
    await this.keycloakService.login();
  }

  // login(): void {
  //   this.errorMsg = [];
  //   this.authService
  //     .authenticate({
  //       body: this.authRequest,
  //     })
  //     .subscribe({
  //       next: (res: AuthenticationResponse) => {
  //         this.tokenService.token = res.token as string;
  //         this.tokenService.fullName = res.fullName as string;
  //         this.router.navigate(['books']);
  //       },
  //       error: (err) => {
  //         if (err.error.validationErrors) {
  //           this.errorMsg = err.error.validationErrors;
  //         } else {
  //           this.errorMsg.push(err.error.errorMsg);
  //         }
  //       },
  //     });
  // }

  // register(): void {
  //   this.router.navigate(['register']);
  // }
}
