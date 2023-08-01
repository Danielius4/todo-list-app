import React, { useEffect, useReducer, useState } from 'react';
import { TodoItem } from './TodoItem';
import { Todo } from '../types';
import Container from '@mui/material/Container';
import { deleteTodo, putTodo } from '../Api';

interface TodoListProps {
  todos: Todo[];
  setTodos: (data: Todo[]) => void;
}

export const TodoList: React.FC<TodoListProps> = ({ todos, setTodos }) => {

  const [todoList, setTodoList] = useState<Todo[]>([]);
  const [reducerValue, forceUpdate] = useReducer(x => x + 1, 0)
  
  useEffect(() => {
    setTodoList(todos);
  }, [todos, reducerValue]);

  const handleDelete = async (id: number) => {
    try{
      deleteTodo(id);
      setTodos(todos.filter((todo) => todo.id !== id));
    } catch (error){
    }
    
  };

  const handleCompletionToggle = async (todo: Todo) => {
    try{
      const response = await putTodo(todo);
      if(response.status === 200){
        forceUpdate();
      }
    } catch (error){

    }
  }

  return (
    <Container sx={{ width: '35%', maxWidth: 360, bgcolor: 'background.paper' }}>
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
    </Container>
  );
};