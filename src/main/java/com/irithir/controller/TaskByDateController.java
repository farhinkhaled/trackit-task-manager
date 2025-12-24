package com.irithir.controller;

import com.irithir.dto.TaskDto;
import com.irithir.model.Task;
import com.irithir.repository.TaskRepository;
import com.irithir.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TaskByDateController {
    private TaskService taskService;

    public TaskByDateController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks-by-date")
    public ModelAndView tasksByDate(@RequestParam("deadline") LocalDate deadline,
                                    ModelAndView modelAndView) {

        List<TaskDto> tasks = taskService.findTasksByDeadline(deadline);

//        List<Task> tasks = taskRepository.findByDeadline(deadline);

        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("deadline", deadline);

        modelAndView.setViewName("task-by-date.html");

        return modelAndView;
    }
}

