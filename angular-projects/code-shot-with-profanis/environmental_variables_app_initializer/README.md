<!-- source - https://www.youtube.com/playlist?list=PLhzRPVQgdM8XWl2S8Dbl8oB2YtrsY0i4O -->

===========================

1. this is the code wrriten in app module ts file - 

 provide: APP_INITIALIZER,
      useFactory: () => {
        const settingsService = inject(SettingsService);
        const http = inject(HttpClient);
        return () =>
          new Promise((resolve) => {
            // load settings for a deployed app
            if (environment.production) {
              http
                .get('./config.json')
                .pipe(
                  tap((data: any) => {
                    settingsService.baseUrl = data.baseUrl;
                    resolve(true);
                  }),
                  catchError((error) => {
                    settingsService.baseUrl = 'http://default.api';
                    resolve(true);
                    return of(null);
                  })
                )
                .subscribe();
            } else {
              // load settings for a local app
              const settings = require('../../config.json');
              settingsService.baseUrl = settings.baseUrl;
              resolve(true);
            }
          });
      },

2. it works, when we create a build and config.json file is not present, then in that case, according to the error handled - default.api will be loaded, else production api will be loaded
3. create a dist ng build --production configuration
4. run the dist - go inside the project in the dist dir - 
5. http-server -p 8080
6. open the console in the browser and see the difference when the project is running locally and on server
