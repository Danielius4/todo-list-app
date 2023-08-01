export type AddTodo = (newTodo: string) => void;

export type Todo = {
  id: number
  content: string;
  completed: boolean;
}

export type ToggleComplete = (selectedTodo: Todo) => void;

export const ApiUrl: string = 'http://localhost:8080/api/tasks'

