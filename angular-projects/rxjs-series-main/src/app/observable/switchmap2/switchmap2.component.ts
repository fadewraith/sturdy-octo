import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { debounceTime, distinctUntilChanged, filter, map, pluck, switchMap } from 'rxjs';
import { Search } from 'src/app/appInterface/search.interface';
import { SearchService } from 'src/app/appServices/search.service';

@Component({
  selector: 'app-switchmap2',
  templateUrl: './switchmap2.component.html',
  styleUrls: ['./switchmap2.component.scss']
})
export class Switchmap2Component implements AfterViewInit {

  // for accessing DOM element via viewchild, we should use ngafterviewinit
  @ViewChild('searchForm') searchForm!: NgForm;
  searchResults!: any;
  searchResultCount!: any;

  constructor(private _searchService: SearchService) { }

  ngAfterViewInit(): void {

    // this._searchService.getSearches('angular').subscribe(res => {
    //   console.warn(res);
      
    // })

      const formValue = this.searchForm.valueChanges;

      formValue?.pipe(
        // map(data => data.searchTerm)
        // map(data => data['searchTerm'])
        filter(() => this.searchForm.valid!),
        pluck('searchTerm'),
        debounceTime(1000),
        distinctUntilChanged(),
        switchMap(data => this._searchService.getSearches(data))
      )
      .subscribe(res => {
        console.warn(res);
        this.searchResults = res;
        // console.log('count =>', Object.keys(res).length)
        this.searchResultCount = Object.keys(res).length
      })
  }

}
