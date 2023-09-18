import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-select-menu-demo',
  templateUrl: './select-menu-demo.component.html',
  styleUrls: ['./select-menu-demo.component.scss']
})
export class SelectMenuDemoComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  // case 1
  // pre select value with value attrib
  // selectedItem: FormControl = new FormControl(2);

  // case 2
  // pre select value without value attrib
  // selectedItem: FormControl = new FormControl("Item 2");

  // case 3
  // selectedItem: FormControl = new FormControl('item2');
  // items: String[] = ['item1', 'item2', 'item3'];
  
  // case 4
  items: { key: string; value: string }[] = [
    {
      key: '1',
      value: 'item1',
    },
    {
      key: '2',
      value: 'item2',
    },
    {
      key: '3',
      value: 'item3',
    }
  ];

  selectedItem: FormControl = new FormControl(this.items[1]);

}
