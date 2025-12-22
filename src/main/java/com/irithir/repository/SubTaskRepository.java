package com.irithir.repository;

import com.irithir.model.SubTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    Page<SubTask> findByTask_Id(Long tasId, Pageable pageable);
}
