import React, { useState } from 'react'
import { Todo } from '../types'
import axios from 'axios';

interface TodoFormProps {
  todos: Todo[];
  setTodos: (data: Todo[]) => void;
}

export const TodoForm: React.FC<TodoFormProps> = ({ todos, setTodos}) => {

  const [todo, setTodo] = useState<Todo>({id: 1, content: '', completed: false})

  const handleSubmit = async (e: React.FormEvent) => {
    try{
      e.preventDefault();
      if(!todo.content || /^\s*$/.test(todo.content)){
        return;
      }
      const response = await axios.post(
        `http://localhost:8080/api/tasks`, todo)
    
      setTodos([response.data, ...todos]);
      setTodo({id: response.data.id + 1, content: '', completed: false})
    } catch(error){
      
    }
  }

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input type="text" value={todo.content} onChange={(e) => setTodo({...todo, content: e.target.value})} />
        <button type="submit">Add Todo</button>
      </form>
    </div>
  )
}

export default TodoForm

