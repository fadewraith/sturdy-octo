import { Component } from '@angular/core';
import { Coffee } from '../logic/Coffee';
import { GeolocationService } from '../geolocation.service';
import { TastingRating } from '../logic/TastingRating';
import { DataService } from '../data.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UiService } from '../ui.service';

@Component({
  selector: 'app-coffee',
  templateUrl: './coffee.component.html',
  styleUrls: ['./coffee.component.css']
})
export class CoffeeComponent {


  coffee = new Coffee();
  types = ["Espresso", "Ristretto", "Americano", "Cappuccino", "Frappe"];
  tastingEnabled: boolean = false;
  formType: "editing" | "inserting" = "inserting";

  constructor(private _geoLocation: GeolocationService, private _data: DataService, private _router: Router, private _route: ActivatedRoute, private _ui: UiService) {}

  ngOnInit() {
    this._ui.setThemeColor("brown");
    this._ui.setTitle("New");
    this._route.params.subscribe(params => {
      if(params["id"]) {
        this._data.get(params["id"], (res: any) => {
          this.coffee = res; // TODO: convert the obj to a coffee instance
          this.formType = "editing";
          this._ui.setTitle(this.coffee.name);
          if(this.coffee.tastingRating) {
            this.tastingEnabled = true;
          }
        })
      }
    })
  }


  cancel() {
    this._router.navigate(["/"]);

  }

  save() {

    let resultFunction = (result: boolean) => {
      if(result) {
        this._router.navigate(["/"]);
      } else {
        // TODO: error msg
      }
    }
    if(this.formType == 'inserting') {
      let { _id, ...insertedCoffee } = this.coffee;
      this._data.save(insertedCoffee, resultFunction);
    } else {
      this._data.save(this.coffee, resultFunction);
    }

  }

  acquireLocation() {
    this._geoLocation.requestLocation((location: GeolocationCoordinates | null) => {
      if(location) {
        this.coffee.location!.latitude = location.latitude;
        this.coffee.location!.longitude = location.longitude;
      }
    });

  }

  tastingRatingChanged(checked: boolean) {
    if(checked) {
      this.coffee.tastingRating = new TastingRating();
    } else {
      this.coffee.tastingRating = null;
    }

  }


}
