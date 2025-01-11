export interface Todo {
  id: string;
  title: string;
  description: string;
}

// We are using the Omit utility type from TypeScript which allows us to use an existing type (in this case Todo) and then remove specific properties from it (in this case id) in order to create a new type.
export type CreateTodo = Omit<Todo, 'id'>;
