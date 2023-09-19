# StructuralDirectivesExample

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 13.1.2.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

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


=====================================

1. ng g directive hideAfter
2. attribute directive - we attach 'hideAfter' name in the <h2></h2> element
3. structural directive - we attach '*hideAfter'
4. '*' is a syntactic sugar
5. when we attach the empty structual directives, it disappears from the template file. ng-template are lazy and we have to explicitly tell angular to render it
6. <ng-template hideAfter> code </ng-template>
7. hide-after.directive.ts - comments
8. we cannot provide [test]="value" in structural directives, it is applied to ng-template, angular considers [test] as a property of DOM element, rather than input of directive. this will work - 
    <ng-template hideAfter [test]="value"> code </ng-template>
9. it works like this - *hideAfter="5000" - @Input in structural directive
10. read the comments in hide-after.directive.ts file
11. ng g d ifLoaded
12. https://angular.io/guide/structural-directives#making-in-template-type-requirements-more-specific-with-template-guards
13. https://www.typescriptlang.org/docs/handbook/2/narrowing.html#using-type-predicates
14. after s22 continued from if-loaded.directive.ts file - read comments