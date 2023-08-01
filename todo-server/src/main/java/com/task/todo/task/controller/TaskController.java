package com.task.todo.task.controller;

import com.task.todo.task.model.Task;

import com.task.todo.task.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@AllArgsConstructor
@CrossOrigin(
        origins = {"http://localhost:3000"}
)
@RequestMapping({"/api"})
public class TaskController {

    private final TaskService taskService;

    @GetMapping({"/tasks"})
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping({"/tasks"})
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PutMapping({"/tasks/{id}"})
    public ResponseEntity<Task> updateTask(@PathVariable("id") int id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping({"/tasks/{id}"})
    public ResponseEntity<HttpStatus> deleteAllTasks(@PathVariable("id") int id) {
        return taskService.deleteTask(id);
    }
}