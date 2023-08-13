import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GeneralService {

  // creating property, will use this  value to show and hide the dialog
  showDialog: boolean = false;

  constructor() { }
}


// step 1 - create directive, service, component, design the component for popup