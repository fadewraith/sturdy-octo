import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  constructor() {}

  @Input() isLeftSidebarCollapsed: boolean = false; // Initialize as needed
  @Input() screenWidth: number = window.innerWidth;

  sizeClass: string = '';

  ngOnInit(): void {
    this.updateSizeClass();
  }

  updateSizeClass(): any {
    if (this.isLeftSidebarCollapsed) {
      this.sizeClass = '';
      return this.sizeClass;
    }
    this.sizeClass = this.screenWidth > 768 ? 'body-trimmed' : 'body-md-screen';
    return this.sizeClass;
  }

  // Call this method when isLeftSidebarCollapsed changes
  changeIsLeftSidebarCollapsed(isCollapsed: boolean): void {
    this.isLeftSidebarCollapsed = isCollapsed;
    this.updateSizeClass();
  }

  // Call this method when screenWidth changes
  onResize(newWidth: number): void {
    this.screenWidth = newWidth;
    this.updateSizeClass();
  }
}
