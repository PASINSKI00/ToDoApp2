package com.pasinski.todoapp.todo.task;

import com.pasinski.todoapp.todo.category.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;

    @Column(nullable = false)
    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dueDate")
    private Date due_date;

    @Column(nullable = false)
    private boolean finished = false;

    @ManyToOne()
    @JoinColumn(
            nullable = false,
            name ="id_category",
            referencedColumnName = "id"
    )
    private Category category;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", due_date=" + due_date +
                ", finished=" + finished +
                ", category=" + category +
                '}';
    }
}
