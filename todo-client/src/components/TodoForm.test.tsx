import {render, screen} from '@testing-library/react';
import {TodoForm} from './TodoForm';

test('The text field is empty', () => {
  render(<TodoForm todos={[]} setTodos={() => {}}/>);

  const textField = screen.getByLabelText('Todo');
  expect(textField).toHaveValue('');
})