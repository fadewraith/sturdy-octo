import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div 
      *ngIf="isOpen" 
      class="modal-overlay"
      (click)="closeOnOutsideClick && onOverlayClick($event)"
    >
      <div class="modal-container" (click)="$event.stopPropagation()">
        <div class="modal-header">
          <h2>{{ title }}</h2>
          <button class="close-button" (click)="close()">×</button>
        </div>
        <div class="modal-content">
          <ng-content></ng-content>
        </div>
        <div class="modal-footer">
          <button class="button cancel" (click)="cancel()">Cancel</button>
          <button class="button submit" (click)="submit()">Submit</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .modal-overlay {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-color: rgba(0, 0, 0, 0.5);
      display: flex;
      align-items: center;
      justify-content: center;
      z-index: 1000;
    }

    .modal-container {
      background-color: white;
      border-radius: 8px;
      padding: 1.5rem;
      width: 90%;
      max-width: 500px;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .modal-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1rem;
    }

    .modal-header h2 {
      margin: 0;
      font-size: 1.5rem;
      color: #1a202c;
    }

    .close-button {
      background: none;
      border: none;
      font-size: 1.5rem;
      cursor: pointer;
      color: #4a5568;
      padding: 0.25rem 0.5rem;
      border-radius: 4px;
    }

    .close-button:hover {
      background-color: #f7fafc;
    }

    .modal-content {
      margin-bottom: 1.5rem;
    }

    .modal-footer {
      display: flex;
      justify-content: flex-end;
      gap: 0.75rem;
    }

    .button {
      padding: 0.5rem 1rem;
      border-radius: 4px;
      border: none;
      cursor: pointer;
      font-weight: 500;
      transition: background-color 0.2s;
    }

    .button.cancel {
      background-color: #e2e8f0;
      color: #4a5568;
    }

    .button.cancel:hover {
      background-color: #cbd5e0;
    }

    .button.submit {
      background-color: #3b82f6;
      color: white;
    }

    .button.submit:hover {
      background-color: #2563eb;
    }

    @media (max-width: 640px) {
      .modal-container {
        width: 95%;
        padding: 1rem;
      }

      .modal-header h2 {
        font-size: 1.25rem;
      }

      .button {
        padding: 0.375rem 0.75rem;
        font-size: 0.875rem;
      }
    }
  `]
})
export class ModalComponent {
  @Input() isOpen = false;
  @Input() title = '';
  @Input() closeOnOutsideClick = false;
  @Output() closeModal = new EventEmitter<void>();
  @Output() submitModal = new EventEmitter<void>();
  @Output() cancelModal = new EventEmitter<void>();

  onOverlayClick(event: MouseEvent): void {
    if (event.target === event.currentTarget) {
      this.close();
    }
  }

  close(): void {
    this.closeModal.emit();
  }

  submit(): void {
    this.submitModal.emit();
  }

  cancel(): void {
    this.cancelModal.emit();
  }
}