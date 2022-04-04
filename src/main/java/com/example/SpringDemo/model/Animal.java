package com.example.SpringDemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name="animals")
@Data
@NoArgsConstructor
@ToString
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Size(min = 2, max = 10)
    private String kind;

//    @Size(min = 2, max = 10)
    private String name;

//    @Size(min = 2, max = 10)
    private Long age;

    @Column(name = "owner_id")
//    @Size(min = 2, max = 10)
    private Long ownerId;
}
