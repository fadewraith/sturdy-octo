import { Component, Input, ChangeDetectionStrategy } from '@angular/core';
import { TodoInterface } from '../../types/todo.interface';
import { TodosService } from '../../services/todos.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-todos-todo',
  templateUrl: './todo.component.html',
  /**
   * onPush components are not checked.
   * angular takes this component outside of the simple change detection cycle
   */
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TodoComponent {
  @Input('todo') todoProps: TodoInterface;

  filter$: Observable<string>;

  constructor(private todosService: TodosService) {
    this.filter$ = todosService.filter$;
  }

  checkRender(): boolean {
    console.warn('checkRender');
    return true;
  }

  changeText(): void {
    this.todoProps.text = 'Changed from inside';
  }

  changeFilter(): void {
    this.todosService.filter$.next('active');
  }
}
