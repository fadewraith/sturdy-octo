import { Component, ElementRef, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-single-select',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="select-container" #selectContainer>
      <button class="select-toggle" (click)="toggleDropdown()">
        {{ selectedOption || placeholder }}
      </button>
      
      <div class="select-menu" *ngIf="isOpen">
        <div class="search-container">
          <input 
            type="text" 
            [(ngModel)]="searchText" 
            (input)="filterOptions()"
            placeholder="Search..."
            class="search-input"
          >
        </div>

        <div class="select-all" *ngIf="showSelectAll">
          <div class="select-item" (click)="clearSelection()">
            Clear selection
          </div>
        </div>

        <div class="options-container">
          <div 
            class="select-item" 
            *ngFor="let option of filteredOptions"
            [class.selected]="option === selectedOption"
            (click)="selectOption(option)"
          >
            {{ option }}
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .select-container {
      position: relative;
      width: 100%;
      max-width: 300px;
    }

    .select-toggle {
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

    .select-toggle:hover {
      border-color: #cbd5e0;
    }

    .select-menu {
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
      border-bottom: 1px solid #e2e8f0;
    }

    .options-container {
      max-height: 200px;
      overflow-y: auto;
    }

    .select-item {
      padding: 0.75rem 1rem;
      cursor: pointer;
    }

    .select-item:hover {
      background: #f7fafc;
    }

    .select-item.selected {
      background: #ebf5ff;
      color: #2563eb;
    }

    @media (max-width: 640px) {
      .select-toggle {
        padding: 0.5rem 0.75rem;
        font-size: 0.875rem;
      }

      .select-item {
        padding: 0.5rem 0.75rem;
      }
    }
  `]
})
export class SingleSelectComponent {
  @Input() options: string[] = [];
  @Input() placeholder: string = 'Select an option';
  @Input() showSelectAll: boolean = true;
  @Output() selectionChange = new EventEmitter<string | null>();

  isOpen = false;
  selectedOption: string | null = null;
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

  selectOption(option: string) {
    this.selectedOption = option;
    this.selectionChange.emit(option);
    this.isOpen = false;
  }

  clearSelection() {
    this.selectedOption = null;
    this.selectionChange.emit(null);
    this.isOpen = false;
  }
}