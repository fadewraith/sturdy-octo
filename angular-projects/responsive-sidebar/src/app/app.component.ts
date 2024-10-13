import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'responsive-sidebar';

  isLeftSidebarCollapsed: boolean = false;
  screenWidth: number = window.innerWidth;

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth < 768) {
      this.isLeftSidebarCollapsed = true;
    }
  }

  ngOnInit(): void {
    this.isLeftSidebarCollapsed = this.screenWidth < 768;
  }

  changeIsLeftSidebarCollapsed(isLeftSidebarCollapsed: boolean): void {
    this.isLeftSidebarCollapsed = isLeftSidebarCollapsed;
  }
}
