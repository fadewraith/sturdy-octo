import { Component, OnInit } from '@angular/core';
import { User } from '../model/td-form.model';
import * as CryptoJS from 'crypto-js';
import { LocalStorageService } from '../local-storage.service';
import { Router } from '@angular/router';



@Component({
  selector: 'app-get-user-data',
  templateUrl: './get-user-data.component.html',
  styleUrls: ['./get-user-data.component.css']
})
export class GetUserDataComponent implements OnInit {

  searchText: any;
  temporaryUser: any;
  decryptedData: any;
  constructor(private _localService: LocalStorageService, private _router: Router) { }

  userdata: User[] = [];
  ngOnInit(): void {
    console.warn(this._localService.getData('test'));
    const encryptedData = this._localService.getData('test');
    if(encryptedData) {
      this.userdata = encryptedData;
    } else {
      this._router.navigateByUrl('/');
    }
    this.temporaryUser = [...this.userdata];
  }


  deleteRow(index: number) {
    this.userdata.splice(index, 1);
    localStorage.setItem('test', JSON.stringify(this.userdata));
  }

  search(keyWord: string) {
    if (keyWord) {
      keyWord = keyWord.toLowerCase();
      const valuesOf = this.userdata.filter((arr: any) => {
        return arr.fName.toLowerCase().includes(keyWord) || arr.mobNum.toLowerCase().includes(keyWord) || arr.eMail.toLowerCase().includes(keyWord) || arr.lName.toLowerCase().includes(keyWord);
      })
      this.userdata = valuesOf;
    } else {
      this.userdata = this.temporaryUser;
    }

  }

}
