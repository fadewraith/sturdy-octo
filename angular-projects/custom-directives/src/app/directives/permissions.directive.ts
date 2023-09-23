import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { PermissionService } from '../services/permission.service';

@Directive({
  selector: '[appPermissions]',
})
export class PermissionsDirective {
  //  When this property is set in the template, the check() method is called.
  @Input() public set appPermissions(permissions: string | string[]) {
    this.check(permissions);
  }

  constructor(
    private _templateRef: TemplateRef<any>,
    private _viewContainer: ViewContainerRef,
    private _permissions: PermissionService
  ) {}

  /**
   * The check() method is a private method that checks the type of the permissions parameter. If it is a string, it calls the verify() method of the PermissionService to check if the user has the specified permission. If it is an array of strings, it iterates through the permissions and checks each one using the verify() method. The updateView() method is then called with the result
   */
  check(permissions: string | string[]): void {
    switch (typeof permissions) {
      case "string":
        this.updateView(this._permissions.verify(permissions));
        break;
      case "object":
        let hasPermission = false;
        permissions.forEach(permission => {
          if (!hasPermission) {
            hasPermission = this._permissions.verify(permission);
          }
        });
        this.updateView(hasPermission);
        break;
    }
  }
  // this method is used to update the view for the UI
  /**
   * 
   * @param hasPermission The updateView() method takes a boolean parameter called hasPermission. If hasPermission is true, it creates an embedded view using the createEmbeddedView() method of the ViewContainerRef, passing in the TemplateRef. If hasPermission is false, it clears the view using the clear() method of the ViewContainerRef
   */
  updateView(hasPermission: boolean): void {
    if(hasPermission) {
      // he template associated with the directive is rendered and displayed.
      this._viewContainer.createEmbeddedView(this._templateRef);
    } else {
      // clears the content rendered within the view container, removing the associated template.
      this._viewContainer.clear();
    }
  }
}
