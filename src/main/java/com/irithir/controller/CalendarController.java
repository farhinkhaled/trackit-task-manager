package com.irithir.controller;

import com.irithir.model.UserEntity;
import com.irithir.repository.TaskRepository;
import com.irithir.repository.UserRepository;
import com.irithir.repository.projection.TaskDeadlineCount;
import com.irithir.security.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CalendarController {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public CalendarController(TaskRepository taskRepository,
                              UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/calendar")
    public ModelAndView calendarView(@RequestParam(required = false) Integer month,
                                     @RequestParam(required = false) Integer year,
                                     ModelAndView modelAndView) {
        LocalDate now = LocalDate.now();

        int selectedMonth = (month != null) ? month : now.getMonthValue();
        int selectedYear = (year != null) ? year : now.getYear();

        LocalDate startDate = LocalDate.of(selectedYear, selectedMonth, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);

        List<TaskDeadlineCount> counts = taskRepository.countTasksByDeadlineBetween(startDate, endDate, user);

        Map<String, Long> deadlineCountMap = new HashMap<>();

        for (TaskDeadlineCount c : counts) {
            deadlineCountMap.put(c.getDeadline().toString(), c.getCount());
        }

        modelAndView.addObject("deadlineCounts", deadlineCountMap);
        modelAndView.addObject("month", selectedMonth);
        modelAndView.addObject("year", selectedYear);

        modelAndView.setViewName("calendar.html");

        return modelAndView;
    }
}

