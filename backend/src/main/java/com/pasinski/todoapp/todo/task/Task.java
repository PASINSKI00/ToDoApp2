package com.pasinski.todoapp.todo.task;

import com.pasinski.todoapp.todo.category.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ApiModel
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
    @ApiModelProperty(notes = "Task ID", example = "1")
    private Long id;

    @Column(nullable = false)
    @JsonProperty("name")
    @NotNull
    @ApiModelProperty(notes = "Task name", example = "School")
    private String name;

    @JsonProperty("description")
    @ApiModelProperty(notes = "Task description", example = "Finish school project")
    private String description;

    @JsonProperty("dueDate")
    @ApiModelProperty(notes = "Task due date", example = "2020-01-01")
    private Date due_date;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @ApiModelProperty(notes = "Task status", example = "false")
    private boolean finished = false;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name ="id_category",
            referencedColumnName = "id"
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
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
