import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pagination',
  standalone: true,
  imports: [CommonModule],
  template: `
    <nav class="pagination-container" [class.mobile]="isMobile" aria-label="Pagination">
      <button 
        class="pagination-button" 
        [disabled]="currentPage === 1"
        (click)="onPageChange(1)"
        aria-label="Go to first page"
      >
        ««
      </button>
      
      <button 
        class="pagination-button"
        [disabled]="currentPage === 1"
        (click)="onPageChange(currentPage - 1)"
        aria-label="Go to previous page"
      >
        «
      </button>

      <div class="pagination-numbers">
        <button 
          *ngFor="let page of visiblePages"
          class="pagination-button"
          [class.active]="page === currentPage"
          (click)="onPageChange(page)"
          [attr.aria-current]="page === currentPage ? 'page' : null"
          [attr.aria-label]="'Go to page ' + page"
        >
          {{ page }}
        </button>
      </div>

      <button 
        class="pagination-button"
        [disabled]="currentPage === totalPages"
        (click)="onPageChange(currentPage + 1)"
        aria-label="Go to next page"
      >
        »
      </button>

      <button 
        class="pagination-button"
        [disabled]="currentPage === totalPages"
        (click)="onPageChange(totalPages)"
        aria-label="Go to last page"
      >
        »»
      </button>
    </nav>
  `,
  styles: [`
    .pagination-container {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 0.5rem;
      margin: 1rem 0;
      flex-wrap: wrap;
    }

    .pagination-numbers {
      display: flex;
      gap: 0.5rem;
    }

    .pagination-button {
      padding: 0.5rem 1rem;
      border: 1px solid #e2e8f0;
      background-color: white;
      color: #1a202c;
      border-radius: 0.375rem;
      cursor: pointer;
      transition: all 0.2s;
      min-width: 2.5rem;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .pagination-button:hover:not(:disabled) {
      background-color: #e2e8f0;
    }

    .pagination-button:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }

    .pagination-button.active {
      background-color: #3b82f6;
      color: white;
      border-color: #3b82f6;
    }

    @media (max-width: 640px) {
      .pagination-container.mobile {
        gap: 0.25rem;
      }

      .pagination-container.mobile .pagination-button {
        padding: 0.375rem 0.75rem;
        min-width: 2rem;
        font-size: 0.875rem;
      }
    }
  `]
})
export class PaginationComponent {
  @Input() currentPage: number = 1;
  @Input() totalPages: number = 1;
  @Input() visiblePagesCount: number = 5;
  @Output() pageChange = new EventEmitter<number>();

  isMobile = window.innerWidth < 640;

  get visiblePages(): number[] {
    const pages: number[] = [];
    let start = Math.max(1, this.currentPage - Math.floor(this.visiblePagesCount / 2));
    let end = Math.min(this.totalPages, start + this.visiblePagesCount - 1);

    if (end - start + 1 < this.visiblePagesCount) {
      start = Math.max(1, end - this.visiblePagesCount + 1);
    }

    for (let i = start; i <= end; i++) {
      pages.push(i);
    }

    return pages;
  }

  onPageChange(page: number): void {
    if (page !== this.currentPage && page >= 1 && page <= this.totalPages) {
      this.pageChange.emit(page);
    }
  }
}