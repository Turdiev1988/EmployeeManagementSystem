package org.example.employeemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.nio.file.FileStore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


}
