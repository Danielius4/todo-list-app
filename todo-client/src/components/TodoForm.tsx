import React, { useState } from 'react'
import { ApiUrl, Todo } from '../types'
import axios from 'axios';
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

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
        ApiUrl, todo)
    
      setTodos([response.data, ...todos]);
      setTodo({id: response.data.id + 1, content: '', completed: false})
    } catch(error){
      
    }
  }

  return (
    <Container sx={{ width: '35%', maxWidth: 360, bgcolor: 'background.paper' }}>
      <form onSubmit={handleSubmit} style={{ display: 'flex', alignItems: 'center' }}>
        <TextField
          label="Todo"
          variant="outlined"
          value={todo.content}
          onChange={(e) => setTodo({ ...todo, content: e.target.value })}
          fullWidth
          margin="normal"
          size="medium"
        />
        <Button variant="outlined" type="submit" size="small" style={{marginTop: '5px', marginLeft: '10px'}}>
          Add Todo
        </Button>
      </form>
    </Container>
  )
}

export default TodoForm

