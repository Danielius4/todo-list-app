package com.task.todo.task.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.task.todo.task.model.Task;
import com.task.todo.task.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    private AutoCloseable autoCloseable;
    private TaskService underTest;
    private Task task1 = new Task("test 1", false);
    private Task task2 = new Task("", true);

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new TaskService(taskRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetEmptyTasks() {
        underTest.getAllTasks();
        verify(taskRepository).findAll();
    }

    @Test
    void canGetTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        Iterable<Task> tasks = underTest.getAllTasks().getBody();
        assertThat(tasks).asList().hasSize(2);
    }

    @Test
    void canCatchExceptionsWhileGetTasks() {
        when(taskRepository.findAll()).thenThrow(new RuntimeException());

        var tasks = underTest.getAllTasks();
        assertThat(tasks).isEqualTo(new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void canAddNewTask() {
        underTest.addTask(task1);

        ArgumentCaptor<Task> taskArgumentCaptor =
                ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskArgumentCaptor.capture());

        Task capturedTask = taskArgumentCaptor.getValue();
        assertThat(capturedTask).isEqualTo(task1);
    }

    @Test
    void willThrowExceptionWhenContentIsEmpty() {
        assertThat(underTest.addTask(task2)).isEqualTo(new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void canUpdateTask() {
        when(taskRepository.findById(task1.getId())).thenReturn(Optional.of(task1));
        when(taskRepository.save(task1)).thenReturn(task1);

        ResponseEntity<Task> responseEntity = underTest.updateTask(task1.getId(), task2);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(task2);
    }

    @Test
    void willThrowWhenUpdatedTaskIsNotFound() {
        assertThat(underTest.updateTask(task1.getId(), task1)).isEqualTo(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
    @Test
    void canDeleteTask() {
        assertThat(underTest.deleteTask(task1.getId())).isEqualTo(new ResponseEntity(HttpStatus.OK));
    }

    @Test
    void willThrowWhenDeletedTaskIsNotFound() {
        doThrow(new RuntimeException()).when(taskRepository).deleteById(task1.getId());

        assertThat(underTest.deleteTask(task1.getId())).isEqualTo(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}