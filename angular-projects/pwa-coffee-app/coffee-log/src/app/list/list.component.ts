import { Component } from '@angular/core';
import { DataService } from '../data.service';
import { Coffee } from '../logic/Coffee';
import { Router } from '@angular/router';
import { GeolocationService } from '../geolocation.service';
import { UiService } from '../ui.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent {

  list: Coffee[] = [];

  constructor(private _data: DataService, private _router: Router, private _geoLocation: GeolocationService, private _ui: UiService) {}

  ngOnInit() {
    this._data.getList((list: Coffee[]) => {
      this.list = list;
    });
    this._ui.setTitle("Coffees");
    this._ui.setThemeColor("orange");
  }

  goDetails(coffee: Coffee) {
    this._router.navigate(["/coffee", coffee._id]);
  }

  goMap(coffee: Coffee) {
    const mapURL = this._geoLocation.getMapLink(coffee.location!);
    // location.href = mapURL;
    window.open(mapURL, "_blank");

  }

  shareCoffee(coffee: Coffee) {
    const text = `I had this coffee at ${coffee.place} and for me its a ${coffee.rating} stars.`;
    const info = {
      title: coffee.name,
      text: text,
      url: window.location.href
    }
    if('share' in navigator && navigator.canShare(info)) {
      // may not be available on all os or browser
      navigator.share(info);
    } else {
      // TODO : show a msg
      console.warn("share is not available");
    }

  }




}
