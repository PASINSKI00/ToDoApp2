package com.pasinski.todoapp.todo.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pasinski.todoapp.user.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;

    @Column(nullable = false)
    @JsonProperty("name")
    private String name;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_user"
    )
    private AppUser user;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
