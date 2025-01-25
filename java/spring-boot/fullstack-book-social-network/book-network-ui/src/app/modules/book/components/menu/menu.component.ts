import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'src/app/services/keycloak/keycloak.service';
import { TokenService } from 'src/app/services/token/token.service';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { ToastrService } from 'ngx-toastr';
import { Notification } from './notification';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent implements OnInit {
  constructor(
    private tokenService: TokenService,
    private keycloakService: KeycloakService,
    private toastrService: ToastrService
  ) {}
  userFullName: string = '';
  socketClient: any = null;
  private notificationSubscription: any;
  unreadNotificationsCount: number = 0;
  notifications: Array<Notification> = [];

  ngOnInit(): void {
    if (this.tokenService.fullName) {
      this.userFullName = this.tokenService.fullName.split(' ')[0];
    }
    this.navigationHandler();
    if (this.keycloakService.keycloak.tokenParsed?.sub) {
      let ws = new SockJS('http://localhost:8088/api/v1/ws');
      this.socketClient = Stomp.over(ws);
      this.socketClient.connect(
        { 'Authorization:': 'Bearer ' + this.keycloakService.keycloak.token },
        () => {
          this.notificationSubscription = this.socketClient.subscribe(
            `/user/${this.keycloakService.keycloak.tokenParsed?.sub}/notifications`,
            (message: any) => {
              const notification = JSON.parse(message.body);
              if (notification) {
                this.notifications.unshift(notification);
                switch (notification.status) {
                  case 'BORROWED':
                    this.toastrService.info(
                      notification.message,
                      notification.bookTitle
                    );
                    break;
                  case 'RETURNED':
                    this.toastrService.warning(
                      notification.message,
                      notification.bookTitle
                    );
                    break;
                  case 'RETURN_APPROVED':
                    this.toastrService.success(
                      notification.message,
                      notification.bookTitle
                    );
                    break;
                }
                this.unreadNotificationsCount++;
              }
            }
          );
        }
      );
    }
  }

  private navigationHandler() {
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach((link) => {
      if (window.location.href.endsWith(link.getAttribute('href') || '')) {
        link.classList.add('active');
      }
      link.addEventListener('click', () => {
        linkColor.forEach((l) => l.classList.remove('active'));
        link.classList.add('active');
      });
    });
  }

  async logout() {
    // localStorage.removeItem('token');
    // window.location.reload();
    await this.keycloakService.logout();
  }

  get userName() {
    return this.keycloakService.keycloak.tokenParsed?.['given_name'];
  }
}
