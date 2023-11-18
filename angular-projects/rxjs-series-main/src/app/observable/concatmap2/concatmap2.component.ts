import { Component, OnInit } from '@angular/core';
import { concatMap, delay, from, mergeMap, of } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-concatmap2',
  templateUrl: './concatmap2.component.html',
  styleUrls: ['./concatmap2.component.scss']
})
export class Concatmap2Component implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  notifyData = [
    {
      name: 'Facebook',
      icon: '',
      time: '4 seconds ago.',
      img: '',
      strong: 'hello',
      p: 'commented on your post'
    },
    {
      name: 'Twitter',
      icon: '',
      time: '3 seconds ago.',
      img: '',
      strong: 'world',
      p: 'commented on your post'
    },
    {
      name: 'Instagram',
      icon: '',
      time: '2 seconds ago.',
      img: '',
      strong: 'john',
      p: 'commented on your post'
    },
    {
      name: 'Youtube',
      icon: '',
      time: '1 seconds ago.',
      img: '',
      strong: 'doe',
      p: 'commented on your post'
    }
  ];

  ngOnInit(): void {

    // by using form we are making this an observable, hence we are going to subscribe it
    from(this.notifyData).pipe(
      // mergeMap(res => this.getHtml(res))
      concatMap(res => this.getHtml(res))
    )
      .subscribe(res => {
        console.warn(res);
        // concat map appends, here we will prepend, newest top and so on
        this._du.print2(res, 'elContainer')
      })


  }

  // creating method for data, otherwise it will produce an error
  getHtml(data: any) {
    // if we dont return, it will give undefined error, here from above  we are getting stream data, so here we will also return an observable
    return of(`<div class="header">
    <div class="app">
    <img src = "${data.icon}" width="20">
        ${data.name}
    </div>
    <div class="time">${data.time}</div>
</div>
<div class="body">
<img src="${data.img}" class="item-img">
    <strong>${data.strong}</strong>
    <p>${data.p}</p>
</div>`).pipe(
      delay(2000)
    )
  }

}
