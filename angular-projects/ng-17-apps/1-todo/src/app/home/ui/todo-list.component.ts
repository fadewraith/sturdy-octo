import { Component, Input } from '@angular/core';
import { Todo } from '../../shared/interfaces/todo';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-todo-list',
  template: `
    <ul>
      @for (todo of todos; track todo.id){
      <li>
        <a routerLink="/detail/{{ todo.id }}">{{ todo.title }}</a>
      </li>
      } @empty {
      <li>Nothing to do!</li>
      }
    </ul>
  `,
  imports: [RouterLink],
  styles: [
    `
      ul {
        margin: 0;
        padding: 1rem;
      }
    `,
  ],
})
export class TodoListComponent {
  @Input({ required: true }) todos!: Todo[];
}
