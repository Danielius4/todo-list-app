import React, { useEffect, useReducer, useState } from 'react';
import { TodoItem } from './TodoItem'
import { Todo } from '../types';
import axios from 'axios';

interface TodoListProps {
  todos: Todo[];
  setTodos: (data: Todo[]) => void;
}

export const TodoList: React.FC<TodoListProps> = ({ todos, setTodos }) => {

  const [todoList, setTodoList] = useState<Todo[]>([]);
  const [reducerValue, forceUpdate] = useReducer(x => x + 1, 0)
  
  useEffect(() => {
    setTodoList(todos);
  }, [todos]);

  const handleDelete = async (id: number) => {
    try{
      await axios.delete(`http://localhost:8080/api/tasks/${id}`);
      setTodos(todos.filter((todo) => todo.id !== id));
    } catch (error){

    }
    
  };

  const handleCompletionToggle = async (todo: Todo) => {
    try{
      const response = await axios.put(`http://localhost:8080/api/tasks/${todo.id}`, todo);
      if(response.status === 200){
        forceUpdate();
      }
    } catch (error){

    }
  }

  return (
    <ul>
      {todoList.length ?
      todoList.map(todo => (
        <TodoItem
          key={todo.id}
          todo={todo}
          handleToggle={handleCompletionToggle}
          handleDelete={handleDelete}

        />
      )) : <></>}
    </ul>
  );
};