package com.irithir.controller;

import com.irithir.dto.SubTaskDto;
import com.irithir.dto.TaskDto;
import com.irithir.model.SubTask;
import com.irithir.model.Task;
import com.irithir.model.TaskHistory;
import com.irithir.service.SubTaskService;
import com.irithir.service.TaskHistoryService;
import com.irithir.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {
    private TaskService taskService;
    private SubTaskService subTaskService;
    private TaskHistoryService taskHistoryService;

    public TaskController(TaskService taskService,
                          SubTaskService subTaskService,
                          TaskHistoryService taskHistoryService){
        this.taskService = taskService;
        this.subTaskService = subTaskService;
        this.taskHistoryService = taskHistoryService;
    }

    @GetMapping("/task-page")
    public ModelAndView taskPage(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 ModelAndView modelAndView){
        List<TaskDto> tasks = taskService.findAllTasks();
        Page<Task> taskPage = taskService.getTasks(page, size);
        Task task = new Task();

        long totalTasks = tasks.size();
        long inProgressTasks = taskService.countTasksByStatus("IN_PROGRESS");
        long overdueTasks = taskService.countTasksByStatus("OVERDUE");
        long completedTasks = taskService.countTasksByStatus("COMPLETED");

        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("task", task);
        modelAndView.addObject("taskPage", taskPage);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalTasks", totalTasks);
        modelAndView.addObject("inProgressTasks", inProgressTasks);
        modelAndView.addObject("overdueTasks", overdueTasks);
        modelAndView.addObject("completedTasks", completedTasks);

        modelAndView.setViewName("tasks.html");
        return modelAndView;
    }

//    @GetMapping("/tasks/search")
//    public ModelAndView searchTasks(@RequestParam(value = "query")String query,
//                                    ModelAndView modelAndView){
//        List<TaskDto> tasks = taskService.searchTasks(query);
//        Page<TaskDto> taskPage = new PageImpl<>(tasks);
//
//        modelAndView.addObject("tasks", tasks);
//        modelAndView.addObject("task", new Task());
//        modelAndView.addObject("taskPage", taskPage);
//        modelAndView.setViewName("tasks.html");
//        return modelAndView;
//    }

    @PostMapping("/create-task")
    public ModelAndView createTask(@ModelAttribute("task") TaskDto task,
                                   ModelAndView modelAndView){

        if(task.getId() == null){
            taskService.saveTask(task);
        }
        else{
            taskService.updateTask(task);
        }

        modelAndView.setViewName("redirect:/task-page");
        return modelAndView;
    }

    @GetMapping("/task/delete/{taskId}")
    public ModelAndView deleteTask(@PathVariable("taskId") Long taskId,
                                   ModelAndView modelAndView){
        taskService.deleteTask(taskId);
        modelAndView.setViewName("redirect:/task-page");
        return modelAndView;
    }

    @GetMapping("/task-detail/{taskId}")
    public ModelAndView taskDetailsPage(@PathVariable("taskId") Long taskId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size,
                                        ModelAndView modelAndView){
        TaskDto taskDto = taskService.findTaskById(taskId);
        SubTask subTask = new SubTask();
        List<SubTaskDto> subTasks = subTaskService.findAllSubTasks();
        Page<SubTask> subTaskPage = subTaskService.getSubTasks(taskId, page, size);
        List<TaskHistory> latestHistories = taskHistoryService.findLatestHistories(taskId);

        modelAndView.addObject("task", taskDto);
        modelAndView.addObject("taskId", taskId);
        modelAndView.addObject("subTask", subTask);
        modelAndView.addObject("subTasks", subTasks);
        modelAndView.addObject("subTaskPage", subTaskPage);
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("latestHistories", latestHistories);

        modelAndView.setViewName("task-detail.html");

        return modelAndView;
    }

    @PostMapping("/sub-task/{taskId}")
    public ModelAndView createSubTask(@PathVariable("taskId") Long taskId,
                                      @ModelAttribute("subTask") SubTaskDto subTask,
                                      ModelAndView modelAndView){

        subTaskService.saveSubTask(taskId, subTask);

        modelAndView.setViewName("redirect:/task-detail/" + taskId);
        return modelAndView;
    }

    @GetMapping("/sub-task/delete/{subTaskId}")
    public ModelAndView deleteSubTask(@PathVariable("subTaskId") Long subTaskId,
                                   ModelAndView modelAndView){
        SubTask subTask = subTaskService.findSubTaskById(subTaskId);
        Long taskId = subTask.getTask().getId();

        subTaskService.deleteSubTask(subTaskId);
        modelAndView.setViewName("redirect:/task-detail/" +taskId);
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView homePage(ModelAndView modelAndView){
        List<TaskDto> upcomingTasks = taskService.upcomingTasks();

        modelAndView.addObject("upcomingTasks", upcomingTasks);
        modelAndView.setViewName("home.html");

        return modelAndView;
    }

    @PostMapping("/task/complete")
    public ModelAndView completeTask(@RequestParam Long taskId,
                                     ModelAndView modelAndView) {
        taskService.markTaskAsComplete(taskId);
        modelAndView.setViewName("redirect:/task-page");
        return modelAndView; // back to your task page
    }
}
