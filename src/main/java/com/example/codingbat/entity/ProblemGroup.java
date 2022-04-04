package com.example.codingbat.entity;

import com.example.codingbat.entity.AbsEntity.SubjectAbstract;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ProblemGroup extends SubjectAbstract {

    @Builder
    public ProblemGroup(String name, String description) {
        super(name, description);
    }

    public ProblemGroup(String name, String description, int reaction, MainSection mainSection) {
        super(name, description);
        this.reaction = reaction;
        this.mainSection = mainSection;
    }

    public ProblemGroup(Long id, String name, String description, int reaction, MainSection mainSection) {
        super(id, name, description);
        this.reaction = reaction;
        this.mainSection = mainSection;
    }

    private int reaction;

    @ManyToOne
    private MainSection mainSection;
}
