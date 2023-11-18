import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { map, Observable, shareReplay } from 'rxjs';

@Component({
  selector: 'app-share-replay',
  templateUrl: './share-replay.component.html',
  styleUrls: ['./share-replay.component.scss']
})
export class ShareReplayComponent implements OnInit {

  constructor(private _http: HttpClient) { }

  url = 'https://test-products-b05fe.firebaseio.com/products.json';

  allProducts: any;
  mobiles!: Observable<any>;
  laptops!: Observable<any>;

  ngOnInit(): void {
    // if the data is ame, then our request should be only one not multiple times
    // with share replay we can avoid multiple http requests
    // here we are getting 2 data mobile and desktops with one http request
    this.allProducts = this._http.get(this.url).pipe(
      shareReplay()
    );

    this.mobiles = this.allProducts.pipe(
      map((res: any) => res.filter((mobileData: any) => {
        return mobileData['type'] == 'mobile'
      }))
    )

    this.laptops = this.allProducts.pipe(
      map((res: any) => res.filter((pcData: any) => {
        return pcData['type'] == 'laptop'
      }))
    )

    // this._http.get(this.url).subscribe(res => {
    //   console.warn(res);
    //   this.allProducts = res;
    // })
  }

}
