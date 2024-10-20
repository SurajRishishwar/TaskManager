package com.suraj.TaskManager.services;

import com.suraj.TaskManager.entity.Task;
import com.suraj.TaskManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    public List<Task>getTask(){
        return repository.findAll();
    }
}
