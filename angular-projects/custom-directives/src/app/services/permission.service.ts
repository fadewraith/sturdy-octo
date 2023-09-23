import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  validPermissions: string[] = ['read', 'write', 'remove'];

  constructor() { }

  /**
   * To set the user permission
   * @param permission - read, write, remove
   * if single permission, convert it into single array.
   */
  setUserPermission(permission: string | string[]): void {
    switch(typeof permission) {
      case "string":
        this.validPermissions = [permission];
        break;
      case 'object':
        this.validPermissions = permission.map(p => p);
        break;
    }
  }

  // this is used to verify the permission, so that only verified permissions should be visible in the UI, for the permission directive, if present return true
  verify(permission: string): boolean {
    return this.validPermissions.some(validPermission => validPermission === permission);
  }
}
