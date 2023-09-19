# AngularMultipleAppsOnPage

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.2.6.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.


================================

<!-- source - https://www.youtube.com/watch?v=c1mp_xIGChc -->

1. check the main.ts file and remove the ChatModule from everywhere
2. bootstrap: [ChatComponent] - write this in chat-app.module.ts file
3. index.html -> <app-chat></app-chat>
4. Since we separated the application, we bootstrapped it separately, we will get the error NullInjectorError
5. import the 'BrowserModule' in chat-app module file
6. remove the common module now
7. import the BrowserAnimationsModule
8. platformBrowserDynamic injector is parent for application root injectors and we can configure the service 'ChatStateService' - @Injectable({
  providedIn: 'platform'
  }) instead of providedIn: 'root' and we will get the same service instance, otherwise there will be 2 different service instance
9. for main application we need to manually inject the change detection strategy inside app component file - private cd: ChangeDetectorRef, private appRef: ApplicationRef, private platformRef: PlatformRef, private ngZone: NgZone
10. choose either of them - this.cd.detectChanges(); or this.appRef.tick(); in app component file
11. here we are bootstrapping the chatappmodule in app component file - this.platformRef.bootstrapModule(ChatAppModule);
12. Comment it inside the main.ts file - // platform.bootstrapModule(ChatAppModule) //   .catch(err => console.error(err));
13. Inject the NgZone in app component file
