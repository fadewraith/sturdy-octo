import { Component } from '@angular/core';
import { AlertTypeEnum } from './alert/types/alertType.enum';
import { AlertService } from './alert/services/alert.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  alertTypes = AlertTypeEnum;
  constructor(private alertService: AlertService) {}

  showAlert(type: AlertTypeEnum) {
    this.alertService.setAlert({
      type,
      text: 'This is our test alert',
    });
  }
}
