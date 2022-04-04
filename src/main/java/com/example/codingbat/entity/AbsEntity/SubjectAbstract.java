package com.example.codingbat.entity.AbsEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class SubjectAbstract {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name is required")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "description is required")
    @Column(nullable = false)
    private String description;

    public SubjectAbstract(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
