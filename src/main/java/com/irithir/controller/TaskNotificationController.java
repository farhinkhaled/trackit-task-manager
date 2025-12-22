package com.irithir.controller;

import com.irithir.model.TaskNotification;
import com.irithir.service.TaskNotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TaskNotificationController {
    private TaskNotificationService taskNotificationService;

    public TaskNotificationController(TaskNotificationService taskNotificationService) {
        this.taskNotificationService = taskNotificationService;
    }

    @GetMapping("/notifications")
    public ModelAndView getAllNotifications(ModelAndView modelAndView){
        List<TaskNotification> notifications = taskNotificationService.findAllNotifications();

        modelAndView.addObject("noNotification", notifications.isEmpty());
        modelAndView.addObject("notifications", notifications);
        modelAndView.setViewName("notifications.html");

        return modelAndView;
    }
}
