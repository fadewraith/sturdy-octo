# AngularAppInitializerExample

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.0.2.

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


==================================

<!-- source - https://www.youtube.com/watch?v=MoRScEseTf4 -->

1. inject the InitializerModule 
2. initStatus.runInitializers(); // this is the actual place where the angular initializes the app and it injects the APP_INITIALIZER token
3. when we're returning either the promise or observable, in this case angular will be waiting until either promise is resolved or when observable is completed
4. ng g m initializer -m app - generate the initializer
5. initializer.module.ts
6. generate service - config.service.ts
7. config.service.ts
8. initializer.module.ts - deps: [ConfigService]
9. app.component.ts