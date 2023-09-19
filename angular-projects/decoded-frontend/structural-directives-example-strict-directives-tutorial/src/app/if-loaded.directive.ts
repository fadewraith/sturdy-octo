import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

// s23 - copied from angular doc
// s24 - make type as generic 'T'
export type Loaded<T> = { type: 'loaded', data: T };
export type Loading = { type: 'loading' };
export type LoadingState<T> = Loaded<T> | Loading;

// s25 - copied from angular doc
export interface Person {
  name: string;
}

@Directive({
  selector: '[ifLoaded]'
})
export class IfLoadedDirective<T> {

  // s26 - a - copied from angular doc and declare the type as 'T' for generic type
  @Input('ifLoaded') set state(state: LoadingState<T>) {
    // s28 - 
    if(state.type === "loaded") {
      this.viewContainerRef.createEmbeddedView(this.tpl);
    } else {
      this.viewContainerRef.clear();
    }
  }
  
  // s26 - b - copied from angular doc and declare the type as 'T' for generic type
  // ngTemplateGuard_ifLoaded - 'ifLoaded' should match with @Input type name
  static ngTemplateGuard_ifLoaded<T>(dir: IfLoadedDirective<T>, expr: LoadingState<T>): expr is Loaded<T> { return true; };

  // s27 - inject
  constructor(
    private viewContainerRef: ViewContainerRef,
    private tpl: TemplateRef<any>) { }

}
