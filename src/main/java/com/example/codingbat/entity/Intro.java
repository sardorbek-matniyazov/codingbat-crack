package com.example.codingbat.entity;

import com.example.codingbat.entity.AbsEntity.SubjectAbstract;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Intro extends SubjectAbstract {

    public Intro(Long id, @NotNull(message = "name is required") String name,
                 @NotNull(message = "description is required") String description,
                 String key, MainSection mainSection) {
        super(id, name, description);
        this.key = key;
        this.mainSection = mainSection;
    }

    public Intro(String name, String description, String key, MainSection mainSection) {
        super(name, description);
        this.key = key;
        this.mainSection = mainSection;
    }

    private String videoUrl;

    @Column(nullable = false)
    private String key;

    @ManyToOne()
    private Topic topic;

    @ManyToOne()
    private Problem problem;

    @ManyToOne(optional = false)
    private MainSection mainSection;
}
