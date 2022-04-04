package com.example.codingbat.entity;

import com.example.codingbat.entity.AbsEntity.SubjectAbstract;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Problem extends SubjectAbstract {
    @Builder
    public Problem(String name, String description) {
        super(name, description);
    }

    public Problem(String name, String description, String key, ProblemGroup problemGroup, double difficulty) {
        super(name, description);
        this.key = key;
        this.problemGroup = problemGroup;
        this.difficulty = difficulty;
    }
    public Problem(Long id,String name, String description, String key, ProblemGroup problemGroup, double difficulty) {
        super(id, name, description);
        this.key = key;
        this.problemGroup = problemGroup;
        this.difficulty = difficulty;
    }

    private String key;

    @ManyToOne(optional = false)
    private ProblemGroup problemGroup;

    @ManyToMany
    private Set<Member> members;

    private double difficulty;
}
