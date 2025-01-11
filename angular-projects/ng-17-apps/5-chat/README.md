# AngularstartChat

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 17.2.0.

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

# Notes for setting up firebase. Local demo only. You can add the same firewall rules for production also.

ng generate environments

1. npm install -g firebase-tools
2. firebase login
   NOTE: If you have used firebase recently and run into issues with authentication, you can try running
3. firebase login --reauth
4. npm install firebase
5. npm install rxfire
6. firebase init
   Make the following selections: Firestore, Emulators
   When prompted choose Create a new project and follow the prompts to create your project. Start in test mode
7. Run the following command (again): firebase init
   Make the following selections: Firestore, Emulators
   This time when prompted choose Use an existing project and select the one your just created.

Follow the prompts and accept the defaults for file names until you get to Emulators Setup.

When you get to the Emulators Setup stage make sure to choose:

Authentication Emulator
Firestore Emulator
Again, use the default values for the ports. When prompted, say yes to enabling the Emulator UI — this provides us with an interface similar to the Firebase console that we can access when running the emulators locally. Make sure you say yes to downloading the emulators.

At this point, you should have several new files added to your application:

.firebaserc
firebase.json
firestore.indexes.json
firestore.rules 8. firebase deploy

9. firebase deploy --only firestore:rules -- for rules modification

environment.development.ts & environment.ts
app.config.ts
firebase emulators:start

Since we enabled the Emulator UI, after running the command to start the emulators you should be able to go to:

http://localhost:4000

Modify the start script in package.json (under "scripts") to reflect the following:

"start": "firebase emulators:exec --project=demo-project --ui 'ng serve'",
NOTE: If you are using Windows you may need to set the command up like this instead:

"start": "firebase emulators:exec --project=demo-project --ui \"ng serve\""

npm start

Adding Data to the Emulator
First, make sure you run the start script:

npm start
This will ensure that the emulators are started before serving your application. Next, you can head over to:

http://localhost:4000
From there, click on the Firestore tab. You should see an option that says + Start collection. Click that, and create a collection with a Collection ID of messsages.

This will automatically create our first document for us, but we need to make sure to fill out its fields. Make sure to add the following fields and values when creating a document:

author | string | Josh
content | string | hello
created | string |

If get any error, please read the firebase log file. For emulators and firestore, use the default suggestions
