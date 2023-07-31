export type AddTodo = (newTodo: string) => void;

export type Todo = {
  id: number
  content: string;
  completed: boolean;
}

export type ToggleComplete = (selectedTodo: Todo) => void;

