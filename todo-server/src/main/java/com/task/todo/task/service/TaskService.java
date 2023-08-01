package com.task.todo.task.service;

import com.task.todo.task.model.Task;
import com.task.todo.task.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public ResponseEntity<Iterable<Task>> getAllTasks() {
        try {
            List<Task> tasks = this.taskRepository.findAll();
            if (tasks.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                Collections.reverse(tasks);
                return new ResponseEntity(tasks, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        try {
            if(task.getContent() == "")
                throw new RuntimeException("Content of the task is empty");

            Task _task = this.taskRepository.save(new Task(task.getContent(), false));
            return new ResponseEntity(_task, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Task> updateTask(@PathVariable("id") int id, @RequestBody Task task) {
        Optional<Task> taskData = this.taskRepository.findById(id);
        if (taskData.isPresent()) {
            Task _task = taskData.get();
            _task.setContent(task.getContent());
            _task.setCompleted(task.isCompleted());
            return new ResponseEntity(this.taskRepository.save(_task), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable("id") int id) {
        try {
            this.taskRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
