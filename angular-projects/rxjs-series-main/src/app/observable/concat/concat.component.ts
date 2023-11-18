import { Component, OnInit } from '@angular/core';
import { concat, interval, map, take } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-concat',
  templateUrl: './concat.component.html',
  styleUrls: ['./concat.component.scss']
})
export class ConcatComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  ngOnInit(): void {

    // creating observable
    const sourceList = interval(1000).pipe(
      map(v => 'Tech List #' + (v+1)),
      take(5)
    );

    const sourceNews = interval(1000).pipe(
      map(v => 'Tech News #' + (v+1)),
      take(3)
    );

    const sourceGadgets = interval(1000).pipe(
      map(v => 'Tech Gadgets #' + (v+1)),
      take(4)
    );

    const FinalObs = concat(sourceList, sourceNews, sourceGadgets);

    FinalObs.subscribe(res => {
      console.warn(res);
      this._du.print(res, 'elContainer');
    })
  }

}
