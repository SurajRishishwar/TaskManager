package com.suraj.TaskManager.controller;

import com.suraj.TaskManager.entity.Task;
import com.suraj.TaskManager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskService service;
    @GetMapping("/getalltask")
    public List<Task> findalltask(){
        return service.getTask();
    }
}
