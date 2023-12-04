# CoffeeLog

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 16.2.0.

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


<!-- Created a server for BE -->
1. new dir server
2. npm init
<!-- nedb is db and rest is api -->
3. npm i express-nedb-rest
4. npm i cors
4. nedb needs _id property to know that is it a new element or not and it uses that as a primary key by default
so while editing we can pass the id, but not while inserting
5. so for that in coffee component ts file in save method, we will save it without id, so we will extract the id and insert hte rest of the property
6. npm run serve

7. issue - navigator.share is not a function - maybe it can be used only with https
8. ng add @angular/pwa
9. heart of pwa - manifest file
10. if we have multiple pwa apps in the same domain, add id at the top of the manifest file
old - "start_url": "./",
modified - "start_url": "./?utm_source=pwa",
ng serve -c production
npx web-push generate-vapid-keys - this will generate public and private key, everytime run
Public Key:
BOjkorKkwBA3PE5PO2BiFWaAnKAz8gMtJTkukuk0317gx-FzhniBstPoHgNkEDvSg8rvgBmgQ6a-2JGime4jGwI

Private Key:
tL01EIUzTnSwlKfvwRXIFUpSajU337AuQtuaIDynRfw