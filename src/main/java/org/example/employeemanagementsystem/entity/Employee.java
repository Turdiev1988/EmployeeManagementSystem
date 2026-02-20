package org.example.employeemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="ism")
    private String firstName;

    @Column(name = "familiya")
    private String lastName;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

}
