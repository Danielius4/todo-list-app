package com.task.todo.task.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private int id;
    private String content;
    private boolean completed;

    public Task(String content, boolean completed) {
        this.content = content;
        this.completed = completed;
    }
}
