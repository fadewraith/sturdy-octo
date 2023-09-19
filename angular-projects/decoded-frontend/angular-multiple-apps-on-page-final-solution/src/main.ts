import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { ChatAppModule } from './app/chat-app/chat-app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

const platform = platformBrowserDynamic(); // s1 - create 

platform.bootstrapModule(AppModule)
  .catch(err => console.error(err));

// this will create separate NgZone, separate root injector for this application and bootstrap the chat component as a root component
// platform.bootstrapModule(ChatAppModule)
//   .catch(err => console.error(err));
