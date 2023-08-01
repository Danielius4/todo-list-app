import React, { useEffect, useState } from 'react';
import './App.css';
import TodoForm from './components/TodoForm';
import { Todo } from './types';
import { TodoList } from './components/TodoList';
import { getTodos } from './Api';

function App() {
  
  const [todos, setTodos] = useState<Todo[]>([]);
  
  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try{
      const response = await getTodos();
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
