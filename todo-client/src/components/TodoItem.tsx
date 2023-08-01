import React from 'react';
import { Todo } from '../types';
import ListItem from '@mui/material/ListItem';
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import TextField from '@mui/material/TextField';

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

  const handleDeleteClick = (e: any) => {
    e.stopPropagation(); // Prevent the event from propagating to the parent ListItem
    handleDelete(todo.id);
  };

  return (
    <ListItem
      button
      style={{ textDecoration: todo.completed ? 'line-through' : 'none'}}
      secondaryAction={
        <IconButton aria-label="delete" onClick={handleDeleteClick}>
          <DeleteIcon />
        </IconButton>
      }
      onClick={() => changeCompletion()}
    >
      <TextField
          variant="standard"
          value={todo.content}
          fullWidth
          margin="normal"
          size="medium"
          InputProps={{
            readOnly: true,
          }}
        />
    </ListItem>
  )
}