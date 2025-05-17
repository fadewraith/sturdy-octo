import { Component } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { PaginationComponent } from './app/pagination/pagination.component';
import { ModalComponent } from './app/modal/modal.component';
import { DropdownComponent } from './app/dropdown/dropdown.component';
import { MultiselectComponent } from './app/multiselect/multiselect.component';
import { SingleSelectComponent } from './app/single-select/single-select.component';
import { DynamicComponent } from './app/dynamic/dynamic.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    PaginationComponent,
    ModalComponent,
    DropdownComponent,
    MultiselectComponent,
    SingleSelectComponent,
    DynamicComponent
  ],
  template: `
    <div class="container">
      <h1>Components Demo</h1>
      
      <section class="section">
        <h2>Simple Dropdown</h2>
        <app-dropdown
          [options]="dropdownOptions"
          placeholder="Select a fruit"
          (selectionChange)="onDropdownChange($event)"
        ></app-dropdown>
      </section>

      <section class="section">
        <h2>Multi-select Dropdown</h2>
        <app-multiselect
          [options]="dropdownOptions"
          placeholder="Select fruits"
          (selectionChange)="onMultiSelectChange($event)"
        ></app-multiselect>
      </section>

      <section class="section">
        <h2>Single Select Dropdown</h2>
        <app-single-select
          [options]="dropdownOptions"
          placeholder="Select a fruit"
          (selectionChange)="onSingleSelectChange($event)"
        ></app-single-select>
      </section>

      <section class="section">
        <h2>Dynamic Component Demo</h2>
        <button (click)="toggleDynamicComponent()">
          Toggle Dynamic Component
        </button>
        <app-dynamic
          [component]="currentDynamicComponent"
          [componentData]="dynamicComponentData"
        ></app-dynamic>
      </section>

      <section class="section">
        <h2>Modal Demo</h2>
        <div class="button-container">
          <button (click)="openModal1()">Open Modal 1</button>
          <button (click)="openModal2()">Open Modal 2</button>
        </div>
      </section>

      <section class="section">
        <h2>Pagination Demo</h2>
        <app-pagination
          [currentPage]="currentPage"
          [totalPages]="totalPages"
          [visiblePagesCount]="5"
          (pageChange)="onPageChange($event)"
        ></app-pagination>
      </section>

      <app-modal
        [isOpen]="isModal1Open"
        [closeOnOutsideClick]="false"
        title="Modal 1"
        (closeModal)="closeModal1()"
        (submitModal)="submitModal1()"
        (cancelModal)="cancelModal1()"
      >
        <p>This modal can only be closed using the buttons.</p>
      </app-modal>

      <app-modal
        [isOpen]="isModal2Open"
        [closeOnOutsideClick]="true"
        title="Modal 2"
        (closeModal)="closeModal2()"
        (submitModal)="submitModal2()"
        (cancelModal)="cancelModal2()"
      >
        <p>This modal can be closed by clicking outside.</p>
      </app-modal>
    </div>
  `,
  styles: [`
    .container {
      max-width: 800px;
      margin: 2rem auto;
      padding: 0 1rem;
    }

    h1 {
      margin-bottom: 2rem;
      color: #1a202c;
      text-align: center;
    }

    .section {
      margin-bottom: 2rem;
      padding: 1.5rem;
      background: white;
      border-radius: 8px;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }

    h2 {
      margin-bottom: 1rem;
      color: #2d3748;
      font-size: 1.25rem;
    }

    .button-container {
      display: flex;
      gap: 1rem;
      margin-top: 1rem;
    }

    button {
      padding: 0.5rem 1rem;
      background-color: #3b82f6;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      transition: background-color 0.2s;
    }

    button:hover {
      background-color: #2563eb;
    }

    @media (max-width: 640px) {
      .container {
        margin: 1rem auto;
      }

      .section {
        padding: 1rem;
        margin-bottom: 1rem;
      }

      .button-container {
        flex-direction: column;
        gap: 0.5rem;
      }

      h1 {
        font-size: 1.5rem;
      }

      h2 {
        font-size: 1.125rem;
      }
    }
  `]
})
export class App {
  currentPage = 1;
  totalPages = 10;
  isModal1Open = false;
  isModal2Open = false;

  dropdownOptions = [
    'Apple', 'Banana', 'Orange', 'Mango', 'Pineapple',
    'Grape', 'Strawberry', 'Blueberry', 'Peach', 'Plum'
  ];

  currentDynamicComponent: any = null;
  dynamicComponentData: any = null;

  onPageChange(page: number) {
    this.currentPage = page;
  }

  onDropdownChange(value: string) {
    console.log('Dropdown selected:', value);
  }

  onMultiSelectChange(values: string[]) {
    console.log('Multiselect selected:', values);
  }

  onSingleSelectChange(value: string | null) {
    console.log('Single select selected:', value);
  }

  toggleDynamicComponent() {
    if (this.currentDynamicComponent === null) {
      this.currentDynamicComponent = DropdownComponent;
      this.dynamicComponentData = {
        options: this.dropdownOptions,
        placeholder: 'Dynamic Dropdown',
        selectionChange: (value: string) => console.log('Dynamic component selected:', value)
      };
    } else {
      this.currentDynamicComponent = null;
      this.dynamicComponentData = null;
    }
  }

  openModal1() {
    this.isModal1Open = true;
  }

  closeModal1() {
    this.isModal1Open = false;
  }

  submitModal1() {
    console.log('Modal 1 submitted');
    this.isModal1Open = false;
  }

  cancelModal1() {
    console.log('Modal 1 cancelled');
    this.isModal1Open = false;
  }

  openModal2() {
    this.isModal2Open = true;
  }

  closeModal2() {
    this.isModal2Open = false;
  }

  submitModal2() {
    console.log('Modal 2 submitted');
    this.isModal2Open = false;
  }

  cancelModal2() {
    console.log('Modal 2 cancelled');
    this.isModal2Open = false;
  }
}

bootstrapApplication(App);