import { Component, OnInit } from '@angular/core';
import { GeneralService } from '../../services/general.service';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss']
})
export class DialogComponent implements OnInit {

  popUp!: boolean;
  constructor(public _generalService: GeneralService) { }

  ngOnInit(): void {
    this.popUp = this._generalService.showDialog;
  }

  



}
