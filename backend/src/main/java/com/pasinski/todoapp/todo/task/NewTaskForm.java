package com.pasinski.todoapp.todo.task;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@ApiModel
public record NewTaskForm(
        String name,
        String description,
        Date dueDate,
        Long categoryId) {

    public NewTaskForm(String title, Long categoryId) {
        this(title, null, null, categoryId);
    }
}
