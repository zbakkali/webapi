package com.example.webapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"NAME", "LEVEL"})})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String level;
}
