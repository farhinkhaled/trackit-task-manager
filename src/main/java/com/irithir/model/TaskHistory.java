package com.irithir.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_history")
public class TaskHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String eventType;
    @CreationTimestamp
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "sub_task_id", nullable = true)
    private SubTask subTask;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
