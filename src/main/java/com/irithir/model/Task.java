package com.irithir.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskTitle;
    private String taskPriority;
    private String taskStatus;
    private LocalDate deadline;
    private String taskDescription;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<SubTask> subTasks = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private List<TaskHistory> histories = new ArrayList<>();
}
