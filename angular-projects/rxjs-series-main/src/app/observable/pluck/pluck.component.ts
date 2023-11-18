import { Component, OnInit } from '@angular/core';
import { from, map, pluck, toArray } from 'rxjs';

@Component({
  selector: 'app-pluck',
  templateUrl: './pluck.component.html',
  styleUrls: ['./pluck.component.scss']
})
export class PluckComponent implements OnInit {

  constructor() { }

  data: any;

  users = [
    {
      name: 'hello',
      skills: 'Angular',
      job: {
        title: 'Frontend Developer',
        experience: 10,
      }
    },
    {
      name: 'world',
      skills: 'Typescript',
      job: {
        title: 'Backend Developer',
        experience: 5,
      }
    },
    {
      name: 'John',
      skills: 'Tailwind CSS',
      job: {
        title: 'Data Engineer',
        experience: 8,
      }
    },
    {
      name: 'Doe',
      skills: 'Bootstrap',
      job: {
        title: 'Data Scientist',
        experience: 11,
      }
    },
  ]

  data2: any;

  ngOnInit(): void {


    //  ex - 01
    // here if we use ng for to demonstrate our data, we can't use ngfor to iterate because its in the form of stream not in the form of data
    from(this.users).pipe(
      // map(data => data.name),
      pluck('name'),
      toArray()
    )
      .subscribe(res => {
        console.warn(res);;
        this.data = res;
      })

    // ex - 02
    from(this.users).pipe(
      // map(data => data.name),
      // below pluck will print nested object details
      pluck('job', 'title'),
      toArray()
    )
      .subscribe(res => {
        console.warn(res);;
        this.data2 = res;
      })



  }



}
