import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BookResponse } from 'src/app/services/models';

@Component({
  selector: 'app-book-card',
  templateUrl: './book-card.component.html',
  styleUrls: ['./book-card.component.scss'],
})
export class BookCardComponent {
  private _book: BookResponse = {};
  private _bookCover: string | undefined = 'https://picsum.photos/1900/800';
  private _manage: boolean = false;

  @Output() private share: EventEmitter<BookResponse> =
    new EventEmitter<BookResponse>();
  @Output() private archive: EventEmitter<BookResponse> =
    new EventEmitter<BookResponse>();
  @Output() private addToWaitingList: EventEmitter<BookResponse> =
    new EventEmitter<BookResponse>();
  @Output() private borrow: EventEmitter<BookResponse> =
    new EventEmitter<BookResponse>();
  @Output() private edit: EventEmitter<BookResponse> =
    new EventEmitter<BookResponse>();
  @Output() private details: EventEmitter<BookResponse> =
    new EventEmitter<BookResponse>();

  get book(): BookResponse {
    return this._book;
  }

  @Input()
  set book(value: BookResponse) {
    this._book = value;
  }

  get bookCover(): string | undefined {
    if (this._book.cover) {
      return 'data:image/jpg;base64, ' + this._book.cover;
    }
    return this._bookCover;
  }

  get manage(): boolean {
    return this._manage;
  }

  @Input()
  set manage(value: boolean) {
    this._manage = value;
  }

  onShare() {
    this.share.emit(this._book);
  }

  onArchive() {
    this.archive.emit(this._book);
  }

  onAddToWaitingList() {
    this.addToWaitingList.emit(this._book);
  }

  onBorrow() {
    this.borrow.emit(this._book);
  }

  onEdit() {
    this.edit.emit(this._book);
  }

  onShowDetails() {
    this.details.emit(this._book);
  }
}
