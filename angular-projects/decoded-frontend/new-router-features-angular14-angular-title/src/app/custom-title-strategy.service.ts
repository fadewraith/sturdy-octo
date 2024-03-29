import { Injectable } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterStateSnapshot, TitleStrategy } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CustomTitleStrategyService extends TitleStrategy {
  override updateTitle(snapshot: RouterStateSnapshot): void {
    // buildTitle fetches the 'title' for the urrently active route
    const title = this.buildTitle(snapshot);
    if (title) {
      this.title.setTitle(`App - ${title}`);
    }
   }

  constructor(private title: Title) { 
    super();
  }
}
