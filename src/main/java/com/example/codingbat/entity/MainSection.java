package com.example.codingbat.entity;

import com.example.codingbat.entity.AbsEntity.SubjectAbstract;
import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity
public class MainSection extends SubjectAbstract {
    @Builder
    public MainSection(Long id, String name, String description) {
        super(id, name, description);
    }

}
