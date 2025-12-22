package com.irithir.controller;

import com.irithir.model.TaskHistory;
import com.irithir.service.TaskHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TaskHistoryController {
    private TaskHistoryService taskHistoryService;

    public TaskHistoryController(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
    }

    @GetMapping("/history/{taskId}")
    public ModelAndView showHistories(@PathVariable("taskId") Long taskId,
                                      ModelAndView modelAndView){
        List<TaskHistory> taskHistories = taskHistoryService.getAllTaskHistories(taskId);
        modelAndView.addObject("taskHistories", taskHistories);
        modelAndView.setViewName("history.html");
        return modelAndView;
    }

}
