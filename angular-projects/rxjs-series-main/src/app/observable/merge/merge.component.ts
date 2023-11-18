import { Component, OnInit } from '@angular/core';
import { concat, interval, map, merge, take } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-merge',
  templateUrl: './merge.component.html',
  styleUrls: ['./merge.component.scss']
})
export class MergeComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  ngOnInit(): void {

    // creating observable
    const sourceList = interval(3000).pipe(
      map(v => 'Tech List #' + (v+1)),
      take(5)
    );

    const sourceNews = interval(6000).pipe(
      map(v => 'Tech News #' + (v+1)),
      take(3)
    );

    const sourceGadgets = interval(3500).pipe(
      map(v => 'Tech Gadgets #' + (v+1)),
      take(4)
    );

    const FinalObs = merge(sourceList, sourceNews, sourceGadgets);

    FinalObs.subscribe(res => {
      console.warn(res);
      this._du.print(res, 'elContainer');
    })
  }

}
