package com.example.webapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"NUMBER", "LABEL"})})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private PhoneLabelEnum label;

    @Column(length = 20)
    private String number;
}
