# source - https://www.youtube.com/playlist?list=PLhzRPVQgdM8XDD5abg0helsgs_o5nEF06

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 11.0.2.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.


Role based Directive - 

1. Method gets invoked on every change detection lifecycle
2. create directive has-role
3. convert the above created directive into the structural one
4. inject the necessary services in HasRoleDirective
5. implement OnInit
6. inside OnInit show/hide the component using _viewContainerRef.createEmbeddedView & _viewContainerRef.clear
7. in the component use the *appHasRole directive int the template *appHasRole="'User'"
8. create input in appHasRole directive
9. remove the ngOnInit
10. inject authservice
11. create method hasRole(role: string) {
    return this._authService.user.roles?.includes(role);
  } in authservice
12. in has role directive - @Input() set appHasRole(role: string) {
    if(this._authService.hasRole(role)) {
      this._viewContainerRef.createEmbeddedView(this._templateRef);
    } else {
      this._viewContainerRef.clear();
    }
  }
