import { Component, ElementRef, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dropdown',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dropdown-container" #dropdownContainer>
      <button class="dropdown-toggle" (click)="toggleDropdown()">
        {{ selectedOption || placeholder }}
      </button>
      
      <div class="dropdown-menu" *ngIf="isOpen">
        <div class="dropdown-item" 
             *ngFor="let option of options" 
             (click)="selectOption(option)">
          {{ option }}
        </div>
      </div>
    </div>
  `,
  styles: [`
    .dropdown-container {
      position: relative;
      width: 100%;
      max-width: 300px;
    }

    .dropdown-toggle {
      width: 100%;
      padding: 0.75rem 1rem;
      background: white;
      border: 1px solid #e2e8f0;
      border-radius: 0.375rem;
      cursor: pointer;
      text-align: left;
      font-size: 1rem;
    }

    .dropdown-toggle:hover {
      border-color: #cbd5e0;
    }

    .dropdown-menu {
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
      max-height: 200px;
      overflow-y: auto;
    }

    .dropdown-item {
      padding: 0.75rem 1rem;
      cursor: pointer;
    }

    .dropdown-item:hover {
      background: #f7fafc;
    }

    @media (max-width: 640px) {
      .dropdown-toggle {
        padding: 0.5rem 0.75rem;
        font-size: 0.875rem;
      }

      .dropdown-item {
        padding: 0.5rem 0.75rem;
      }
    }
  `]
})
export class DropdownComponent {
  @Input() options: string[] = [];
  @Input() placeholder: string = 'Select an option';
  @Output() selectionChange = new EventEmitter<string>();

  isOpen = false;
  selectedOption: string | null = null;

  constructor(private elementRef: ElementRef) {
    this.selectionChange = new EventEmitter<string>();
  }

  @HostListener('document:click', ['$event'])
  onClickOutside(event: Event) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.isOpen = false;
    }
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectOption(option: string) {
    this.selectedOption = option;
    this.selectionChange.emit(option);
    this.isOpen = false;
  }
}