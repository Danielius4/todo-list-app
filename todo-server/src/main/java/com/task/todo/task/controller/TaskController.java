package com.task.todo.task.controller;

import com.task.todo.task.model.Task;
import com.task.todo.task.repository.TaskRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = {"http://localhost:3000"}
)
@RequestMapping({"/api"})
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository repository) {
        this.taskRepository = repository;
    }

    @GetMapping({"/tasks"})
    public ResponseEntity<Iterable<Task>> findAll() {
        try {
            new ArrayList();
            List<Task> task = this.taskRepository.findAll();
            if (task.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                Collections.reverse(task);
                return new ResponseEntity(task, HttpStatus.OK);
            }
        } catch (Exception var2) {
            return new ResponseEntity((MultiValueMap)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping({"/tasks"})
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task _task = (Task)this.taskRepository.save(new Task(task.getContent(), false));
            return new ResponseEntity(_task, HttpStatus.CREATED);
        } catch (Exception var3) {
            return new ResponseEntity((MultiValueMap)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping({"/tasks/{id}"})
    public ResponseEntity<Task> updateTask(@PathVariable("id") int id, @RequestBody Task task) {
        Optional<Task> taskData = this.taskRepository.findById(id);
        if (taskData.isPresent()) {
            Task _task = (Task)taskData.get();
            _task.setContent(task.getContent());
            _task.setCompleted(task.isCompleted());
            return new ResponseEntity((Task)this.taskRepository.save(_task), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/tasks/{id}"})
    public ResponseEntity<HttpStatus> deleteAllTasks(@PathVariable("id") int id) {
        try {
            this.taskRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var3) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}