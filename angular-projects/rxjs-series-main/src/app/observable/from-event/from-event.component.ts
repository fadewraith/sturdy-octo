import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { fromEvent } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-from-event',
  templateUrl: './from-event.component.html',
  styleUrls: ['./from-event.component.scss']
})
export class FromEventComponent implements OnInit, AfterViewInit {

  constructor(private _designUtility: DesignUtilityService) { }

  // accessing template reference variable by template reference variable

  @ViewChild('addBtn') addBtn!: ElementRef;

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    let count = 1;
    // takes 2 arguments, target name, event name
    fromEvent(this.addBtn.nativeElement, 'click').subscribe(res => {
      // it will produce error and it is happening because we are using it in ngoninit, so whenever we use viewchild for this type of work, where we have to use DOM elements and to work with them, we do it in  ngafterviewinit, so that whenever our full page is rendered
      // console.warn('video '+ count++);
      let countValue = 'Video '+  count++;
      this._designUtility.print(countValue, 'elContainer');
      this._designUtility.print(countValue, 'elContainer2');

    });
    // console.warn(this.addBtn);

  }

 

}
