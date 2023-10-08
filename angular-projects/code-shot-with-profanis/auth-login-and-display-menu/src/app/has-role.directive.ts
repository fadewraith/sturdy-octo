import { Directive, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthService } from './auth.service';

@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective {

  @Input() set appHasRole(role: string) {
    if(this._authService.hasRole(role)) {
      this._viewContainerRef.createEmbeddedView(this._templateRef);
    } else {
      this._viewContainerRef.clear();
    }
  }

  constructor(private _templateRef: TemplateRef<any>, private _viewContainerRef: ViewContainerRef, private _authService: AuthService) { }

  // ngOnInit (): void {
  //   // show the content
  //   this._viewContainerRef.createEmbeddedView(this._templateRef);

  //   // hide the content
  //   // this._viewContainerRef.clear();
  // }

}
