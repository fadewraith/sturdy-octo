import { Component, ElementRef, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-multiselect',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="multiselect-container" #multiselectContainer>
      <button class="multiselect-toggle" (click)="toggleDropdown()">
        {{ getSelectedText() }}
      </button>
      
      <div class="multiselect-menu" *ngIf="isOpen">
        <div class="search-container">
          <input 
            type="text" 
            [(ngModel)]="searchText" 
            (input)="filterOptions()"
            placeholder="Search..."
            class="search-input"
          >
        </div>

        <div class="select-all">
          <label>
            <input 
              type="checkbox" 
              [checked]="areAllSelected()" 
              (change)="toggleSelectAll()"
            >
            Select All
          </label>
        </div>

        <div class="options-container">
          <div class="multiselect-item" *ngFor="let option of filteredOptions">
            <label>
              <input 
                type="checkbox" 
                [checked]="isSelected(option)"
                (change)="toggleOption(option)"
              >
              {{ option }}
            </label>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .multiselect-container {
      position: relative;
      width: 100%;
      max-width: 300px;
    }

    .multiselect-toggle {
      width: 100%;
      padding: 0.75rem 1rem;
      background: white;
      border: 1px solid #e2e8f0;
      border-radius: 0.375rem;
      cursor: pointer;
      text-align: left;
      font-size: 1rem;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .multiselect-toggle:hover {
      border-color: #cbd5e0;
    }

    .multiselect-menu {
      position: absolute;
      top: 100%;
      left: 0;
      right: 0;
      margin-top: 0.25rem;
      background: white;
      border: 1px solid #e2e8f0;
      border-radius: 0.375rem;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      z-index: 1000;
    }

    .search-container {
      padding: 0.5rem;
      border-bottom: 1px solid #e2e8f0;
    }

    .search-input {
      width: 100%;
      padding: 0.5rem;
      border: 1px solid #e2e8f0;
      border-radius: 0.25rem;
      font-size: 0.875rem;
    }

    .select-all {
      padding: 0.5rem 1rem;
      border-bottom: 1px solid #e2e8f0;
    }

    .options-container {
      max-height: 200px;
      overflow-y: auto;
    }

    .multiselect-item {
      padding: 0.5rem 1rem;
    }

    .multiselect-item:hover {
      background: #f7fafc;
    }

    label {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      cursor: pointer;
    }

    @media (max-width: 640px) {
      .multiselect-toggle {
        padding: 0.5rem 0.75rem;
        font-size: 0.875rem;
      }

      .multiselect-item {
        padding: 0.5rem 0.75rem;
      }
    }
  `]
})
export class MultiselectComponent {
  @Input() options: string[] = [];
  @Input() placeholder: string = 'Select options';
  @Output() selectionChange = new EventEmitter<string[]>();

  isOpen = false;
  selectedOptions: string[] = [];
  searchText = '';
  filteredOptions: string[] = [];

  constructor(private elementRef: ElementRef) {}

  @HostListener('document:click', ['$event'])
  onClickOutside(event: Event) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.isOpen = false;
    }
  }

  ngOnInit() {
    this.filteredOptions = [...this.options];
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  filterOptions() {
    this.filteredOptions = this.options.filter(option =>
      option.toLowerCase().includes(this.searchText.toLowerCase())
    );
  }

  toggleOption(option: string) {
    const index = this.selectedOptions.indexOf(option);
    if (index === -1) {
      this.selectedOptions.push(option);
    } else {
      this.selectedOptions.splice(index, 1);
    }
    this.selectionChange.emit(this.selectedOptions);
  }

  isSelected(option: string): boolean {
    return this.selectedOptions.includes(option);
  }

  areAllSelected(): boolean {
    return this.filteredOptions.length > 0 && 
           this.filteredOptions.every(option => this.selectedOptions.includes(option));
  }

  toggleSelectAll() {
    if (this.areAllSelected()) {
      this.selectedOptions = this.selectedOptions.filter(
        option => !this.filteredOptions.includes(option)
      );
    } else {
      this.filteredOptions.forEach(option => {
        if (!this.selectedOptions.includes(option)) {
          this.selectedOptions.push(option);
        }
      });
    }
    this.selectionChange.emit(this.selectedOptions);
  }

  getSelectedText(): string {
    if (this.selectedOptions.length === 0) {
      return this.placeholder;
    }
    if (this.selectedOptions.length <= 2) {
      return this.selectedOptions.join(', ');
    }
    return `${this.selectedOptions.length} items selected`;
  }
}