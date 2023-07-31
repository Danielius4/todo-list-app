import React, { useEffect, useState } from 'react';
import './App.css';
import TodoForm from './components/TodoForm';
import { Todo } from './types';
import { TodoList } from './components/TodoList';
import axios from 'axios';

function App() {
  
  const [todos, setTodos] = useState<Todo[]>([]);
  
  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try{
      const response = await axios.get<Todo[]>('http://localhost:8080/api/tasks');
      setTodos(response.data);
    } catch (error){

    }
  }

  return (
    <div className="App">
      <h1> Todo app </h1>
      <TodoForm todos={todos} setTodos={setTodos}/>
      <TodoList todos={todos} setTodos={setTodos}/>
    </div>
  );
}

export default App;
