import { Component } from '@angular/core';
import { PermissionService } from './services/permission.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'directives-services-pipes';
  name = 'World!';
  showPermission: boolean = false;

  users: String[] = [
    'Admin User',
    'Read User',
    'Write User',
    'Remove User',
    'Multiple Permissions',
  ];

  constructor(private _permission: PermissionService) {}

  adminPermissions: string[] = ['read', 'write', 'remove'];
  multiplePermissions: string[] = ['admin', 'not admin'];

  // method is used to show the permission on click of the navbar in the UI
  grantUserPermission(user: String, index: Number): void {
    // console.warn(`${user} - ${index}`);
    user = user.split(' ')[0];
    if (user === 'Admin') {
      this.userGrantedPermission(this.adminPermissions);
    } else if (user === 'Read') {
      this.userGrantedPermission('read');
    } else if (user === 'Write') {
      this.userGrantedPermission('write');
    } else if (user === 'Remove') {
      this.userGrantedPermission('remove');
    } else if (user === 'Multiple') {
      this.userGrantedPermission(this.multiplePermissions);
    }
  }



  // setting the uer permission and displaying the permission
  userGrantedPermission(permission: string | string[]): void {
    this.showPermission = false;
    this._permission.setUserPermission(permission);
    setTimeout(() => {
      this.showPermission = true;
    }, 100);
  }
}
