import axios from 'axios';
import { Todo } from './types';

const ApiUrl = 'http://localhost:8080/api/tasks';

export const postTodo = async (todo: Todo) => {
  try {
    const response = await axios.post(ApiUrl, todo);
    return response;
  } catch (error) {
    throw new Error('Failed to create todo.');
  }
};

export const getTodos = async () => {
  try {
    const response = await axios.get(ApiUrl);
    return response;
  } catch (error) {
    throw new Error('Failed to get todo.');
  }
};

export const putTodo = async (todo: Todo) => {
  try{
    const response = await axios.put(`${ApiUrl}/${todo.id}`, todo);
    return response;
  } catch (error){
    throw new Error('Failed to put todo.');
  }
};

export const deleteTodo = async (id: number) => {
  try{
    const response = await axios.delete(`${ApiUrl}/${id}`);
    return response;
  } catch (error){
    throw new Error('Failed to delete todo.');
  }
  
};