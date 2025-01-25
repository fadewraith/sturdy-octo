# BookNetworkUi

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 16.1.0.

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

src->openapi->openapi.json -> is basically a json of swagger ui of below link -
http://localhost:8088/api/v1/v3/api-docs

ng-openapi-gen - docs
package.json -> scripts -> ng openapi gen

==================

npx ng g m book --routing

- error
  Schematic input does not validate against the Schema: {"skipTests":true,"routing":true,"name":"book","path":"src/app/modules","project":"book-network-ui"}
  Errors:

Data path "" must NOT have additional properties(skipTests).
soln ->
remove the spectests for module from angular.json

npm i @auth0/angular-jwt

==================
keycloak
install keycloak using npm

1. create keycloaks service
2. add in app.module.ts
3. change in auth guard service and if any other places, like here htp-interceptor

==================

websocket -
add in package.json -
"net": "^1.0.2",
"quill": "^2.0.2",
"sockjs-client": "^1.6.1",
"stompjs": "^2.3.3",

add in dev dependencies -
"@types/sockjs-client": "^1.5.4",
"@types/stompjs": "^2.3.9",

after implementing websocket in main componenet, getting global error in console -
to fix it, created a separate file -
window-global-fix.ts
(window as any).global = window;
add this file in angular.json inside polyfills array -
include it in tsconfig app json file -
"files": [
"src/main.ts",
"src/window-global-fix.ts"
],

add in angular.json -
"scripts": [
"node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"
]
