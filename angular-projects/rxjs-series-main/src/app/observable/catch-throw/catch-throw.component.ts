import { Component, OnInit } from '@angular/core';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-catch-throw',
  templateUrl: './catch-throw.component.html',
  styleUrls: ['./catch-throw.component.scss']
})
export class CatchThrowComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  fetching: boolean = false;
  allProducts: any;

  error: any = 'error';

  ngOnInit(): void {
  }

  onGetProducts() {
    this.fetching = true;
    this._du.getProducts().subscribe(
      (res: any) => {
        this.allProducts = res;
        this.fetching = false;
      },
      (err: any) => {
        console.warn(err);
        // and we have to catch error in design utility service file
        this.error = err;
        this.fetching = false;

        
        this.fetching = false;
      }
    );
  }

}
