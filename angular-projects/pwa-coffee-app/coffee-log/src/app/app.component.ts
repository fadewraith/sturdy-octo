import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SwPush, SwUpdate } from '@angular/service-worker';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'coffee-log';

  constructor(private _snackBar: MatSnackBar, private _swUpdate: SwUpdate, private _swPush: SwPush) {}

  registerForPush() {
    console.warn('hello');
    if(this._swPush.isEnabled) {
      Notification.requestPermission(permission => {
        if(permission == 'granted') {
          this._swPush.requestSubscription({
            // generated by npx web-push generate-vapid-keys
            serverPublicKey: "BOjkorKkwBA3PE5PO2BiFWaAnKAz8gMtJTkukuk0317gx-FzhniBstPoHgNkEDvSg8rvgBmgQ6a-2JGime4jGwI"
          }).then(registration => {
            console.warn(registration);
          })
        }
      })
      
    }

  }

  updateNetworkStatusUI() {
    if(navigator.onLine) {
      // false positives, careful
      (document.querySelector("body") as any).style = "";
    } else {
      // we are offline
      (document.querySelector("body") as any).style = "filter: grayscale(1)";
    }
  }

  ngOnInit() {
    // checking sw-based updates
    if(this._swUpdate.isEnabled) {
      this._swUpdate.checkForUpdate();
      this._swUpdate.versionUpdates.subscribe(update => {
        if(update.type == 'VERSION_READY') {
          // location.reload();
          const sb = this._snackBar.open("There is an update version available", "Install now", { duration: 4000 });
          sb.onAction().subscribe( () => {
            // TODO: ux check before  reloading
            location.reload();
          });
        }
      });
    }

    // updating the ui on network changes
    this.updateNetworkStatusUI();
    window.addEventListener("online", this.updateNetworkStatusUI);
    window.addEventListener("offline", this.updateNetworkStatusUI);
    if (window.matchMedia('(display-mode: browser').matches) {
      // we're in the browser
      if('standalone' in navigator) {
        // only available in safari
        this._snackBar.open("You can install this app, use Share -> Add to Home Screen", "", { duration: 3000 });
      } else {
        window.addEventListener("beforeinstallprompt", event => {
          event.preventDefault();
          const sb = this._snackBar.open("You  can install this app.", "Install", { duration: 5000 });
          sb.onAction().subscribe(() => {
            (event as any).prompt();
            (event as any).userChoice.then((result: any) => {
              if(result.outcome == "dismissed") {
                // TODO:
              } else {
                // TODO:
              }
            })
          });
        });
      }
    }
  }
}
