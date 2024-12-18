package com.suraj.TaskManager.controller;

import com.suraj.TaskManager.Exceptions.DataNotPresent;
import com.suraj.TaskManager.Exceptions.GoalCannotBeNull;
import com.suraj.TaskManager.entity.Task;
import com.suraj.TaskManager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping("/tasks")
    public ResponseEntity<?> findalltask(){
        List<Task> tasklist=service.getTask();
        return new ResponseEntity<>(tasklist,HttpStatus.OK);
    }

    @GetMapping("/tasksforuser")
    public ResponseEntity<?> findalltaskforuser(Authentication authentication){
        String username=authentication.getName();
        List<Task> tasklist=service.getTaskbyusername(username);
        return new ResponseEntity<>(tasklist,HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<?> addTask(@RequestBody Task task, Authentication authentication){
        try{
            String username=authentication.getName();
            Task newtask=service.addtask(task,username);
            return new ResponseEntity<>(newtask, HttpStatus.CREATED);
        }catch (GoalCannotBeNull e){
            Map<String,Object> errorCode =new HashMap<>();
            errorCode.put("Error",e.getMessage());
            return new ResponseEntity<>(errorCode,HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/task")
    public ResponseEntity<?> updatetask(@RequestBody Task task){
        try {
            Task tasktoupdate=service.updatetask(task);
            return new ResponseEntity<>(tasktoupdate,HttpStatus.OK);
        }catch (DataNotPresent e){
            Map<String,Object> errorCode =new HashMap<>();
            errorCode.put("Error",e.getMessage());
            return new ResponseEntity<>(errorCode,HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deletetask(@PathVariable int id){
        try {
            Task task=service.deletetask(id);
            return new ResponseEntity<>(task,HttpStatus.OK);
        }catch (DataNotPresent e){
            Map<String,Object> errorCode =new HashMap<>();
            errorCode.put("Error",e.getMessage());
            return new ResponseEntity<>(errorCode,HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/task/key/{keyword}")
    public ResponseEntity<?> gettask(@PathVariable String keyword){
        try{
            List<Task> tasks=service.gettaskbykey(keyword);
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }catch (DataNotPresent e){
            Map<String,Object> errorCode =new HashMap<>();
            errorCode.put("Error",e.getMessage());
            return new ResponseEntity<>(errorCode,HttpStatus.BAD_REQUEST);
        }

    }
}
