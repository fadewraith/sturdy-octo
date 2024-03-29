import { Component } from "@angular/core";

@Component({
  selector: "app-root",
  template: `
    <header>
      <mat-toolbar color="primary">
        <a
          mat-stroked-button
          routerLinkActive="active"
          routerLink="users"
          class="nav-link"
          >Users</a
        >
        <a mat-stroked-button routerLinkActive="active" routerLink="photos"
          >Photos</a
        >
      </mat-toolbar>
    </header>
    <main class="content">
      <aside class="sidebar">
        <router-outlet></router-outlet>
      </aside>
      <section class="details">
        <router-outlet
          (activate)="isButtonVisible = true"
          (deactivate)="isButtonVisible = false"
          name="details"
        ></router-outlet>
        <a
          mat-button
          *ngIf="isButtonVisible"
          [routerLink]="['', { outlets: { details: null } }]"
          >close</a
        >
      </section>
    </main>
  `,
  styles: [
    `
      .content {
        min-height: calc(100vh - 64px);
        display: flex;
      }
      .active {
        border-color: #fff;
      }
      a {
        margin-left: 15px;
      }
    `,
  ],
})
export class AppComponent {
  // source - https://youtu.be/WKCweQOI9-U?list=PLX7eV3JL9sflxg0fWgaWoz43Rx99bivmv
  isButtonVisible = false;
}
