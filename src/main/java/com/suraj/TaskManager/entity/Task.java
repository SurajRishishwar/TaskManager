package com.suraj.TaskManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String goal;
    private LocalDate createdDate;
    private LocalDate deadlineDate;
    private List<String> replies;
    private List<String> instructions;
    @ManyToOne
    @JsonIgnore
    private User user;
}
