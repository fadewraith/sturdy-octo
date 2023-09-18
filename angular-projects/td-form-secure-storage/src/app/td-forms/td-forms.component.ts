import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User } from '../model/td-form.model';
import { ActivatedRoute, Router } from '@angular/router';
import * as CryptoJS from 'crypto-js';
import { LocalStorageService } from '../local-storage.service';

@Component({
  selector: 'app-td-forms',
  templateUrl: './td-forms.component.html',
  styleUrls: ['./td-forms.component.css']
})
export class TdFormsComponent implements OnInit {

  user: User = new User();
  samples= ['DWghS6maZgZPh4FICE2w/n/jkHtClx9J5azBRLfYtX8sErCqOelV8S8aGY57qNcf','AtIhj0SdDWrc1FZyEZVRAaSj/Gc0CXIcPRiXupgZKfxX/cGrSOyN2M0AIxpeLeSd' ,'R6jrnOYr0pboGD3xuoqiZ8PwF61k0Q3y1CxFHz+zANAe0h/PjkPaLepJJRwQBfcG','far7f6aJPzz+0oRRR2tHhSZqBVx352eOLy77cVPjGyh+ieZq2EEkJyGWLCI5sd7f'];
  mobNum: any;
  tdForm: User[] = [];
  decryptedData: any;

  constructor(private _router: Router, private router: ActivatedRoute, private _localService: LocalStorageService) { }


  ngOnInit(): void {

    this.mobNum = this.router.snapshot.params['mobNum'];

    // let storageValue = localStorage.getItem('test');
    let data = this._localService.getData('test');
    if(data) {
      this.tdForm = data;
    } else {
      this.tdForm = [];
      this._router.navigateByUrl('/');
    }
    // if (encryptedData != null) {
    //   this.tdForm = JSON.parse(encryptedData);
    // } else {
    //   this.tdForm = [];
    // }
    console.warn(this.tdForm);

    this.tdForm.forEach((e: any) => {
      if (e.mobNum === this.mobNum) {
        this.user = e;
      }
    });

  }


  getData(tdForm: NgForm) {
    console.warn(Array.isArray(this.tdForm));


    if (this.mobNum != null) { // edit operation

      this.tdForm.forEach((e: any) => {
        if (e.mobNum === this.mobNum) {
          e = this.user;
        }
      });

    } else { // save operation
      this.tdForm.push(this.user);
      console.warn(this.user);
    }

    // localStorage.setItem('test', JSON.stringify(this.tdForm));
    this._localService.setData('test', this.tdForm);

    this._router.navigateByUrl('/user');

  }

}
