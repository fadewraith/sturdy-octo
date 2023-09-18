import { ErrorHandler, enableProdMode, importProvidersFrom } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';

import { environment } from './environments/environment';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
// import { CustomErrorHandlerService } from './app/custom-error-handler.service';
import { CustomErrorHandler } from './app/custom-error-handler.service';
import { GlobalHttpErrorHandlerInterceptor } from './app/global-http-error-handler.interceptor';

if (environment.production) {
  enableProdMode();
}

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(
      BrowserAnimationsModule,
      HttpClientModule,
      MatSnackBarModule
    ),
    {
      provide: ErrorHandler,
      // useClass: CustomErrorHandlerService
      useClass: CustomErrorHandler
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: GlobalHttpErrorHandlerInterceptor,
      multi: true
    }
  ],
}).catch((err) => console.error(err));
