import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { delay, retry, retryWhen, scan } from 'rxjs';

@Component({
  selector: 'app-retry',
  templateUrl: './retry.component.html',
  styleUrls: ['./retry.component.scss']
})
export class RetryComponent implements OnInit {

  constructor(private http: HttpClient) { }

  person: any;
  fetching: boolean = false;
  status: string = 'No Data';

  ngOnInit(): void {
    // this.fetchDetails();
  }

  fetchDetails() {
    this.fetching = true;
    this.status = 'Fetching Data...';
    this.http.get('https://reqres.in/api/users/2').pipe(
      // in rety give number how many times we want to retry
      // retry(4)
      // inside that we pass notifier and that notifier works as a function an we rety based on that error
      // we are going to use pipe coz we are going to use chaining
      retryWhen(error => error.pipe(
        // try after 3 sec
        delay(3000),
        // it takes 2 params accumulator (which itself a function) and seed and its optional
        scan((retryCount) => {
          if(retryCount >= 5) {
            throw error;
          } else {
            retryCount = retryCount+1;
            console.warn('retryCount => '+retryCount);
            this.status = 'Retrying Attempt # '+ retryCount;
            return retryCount;

          }
        }, 0)
      ))
    )
    .subscribe(response => {
      console.warn(response);
      this.person = response;
      this.status = 'Data Fetched';
      this.fetching = false;
    },
      (error) => {
        console.warn(error);
        this.status = 'Problem Fetching Data';
        this.fetching = false;
      })
  }

}
