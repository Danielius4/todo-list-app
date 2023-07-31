import React from 'react';
import { Todo } from '../types';

interface TodoItemProps {
  todo: Todo;
  handleToggle: (todo: Todo) => void;
  handleDelete: (id: number) => void;
}

export const TodoItem: React.FC<TodoItemProps> = ({ todo, handleToggle, handleDelete}) => {

  function changeCompletion() {
    todo.completed = !todo.completed;
    handleToggle(todo);
  }

  return (
    <div>
      <input
        type="checkbox"
        checked={todo.completed}
        onChange={changeCompletion}
      />
      {todo.content}
      <button type="button" onClick={() => handleDelete(todo.id)}>Delete</button>
    </div>
  )
}