package com.suraj.TaskManager.services;

import com.suraj.TaskManager.Exceptions.DataNotPresent;
import com.suraj.TaskManager.Exceptions.GoalCannotBeNull;
import com.suraj.TaskManager.entity.Task;
import com.suraj.TaskManager.entity.User;
import com.suraj.TaskManager.repository.TaskRepository;
import com.suraj.TaskManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<Task>getTask(){
        return repository.findAll();
    }

    public Task addtask(Task task,String username) {
        if(task.getGoal().isEmpty()){
            throw new GoalCannotBeNull("Goal Cannot Be Null");
        }
        User user=userRepository.findByUsername(username);
        task.setUser(user);
        task.setCreatedDate(LocalDate.now());
        if(task.getDeadlineDate()==null){
            task.setDeadlineDate(LocalDate.now().plusDays(7));
        }

        return repository.save(task);
    }

    public Task updatetask(Task task) {
       Optional<Task> oldTask=repository.findById(task.getId());
       if(oldTask.isEmpty()){
           throw new DataNotPresent("Data Not Found with Id : "+task.getId());
       }else{
           oldTask.get().setGoal(task.getGoal());

           if(task.getDeadlineDate()==null){
               oldTask.get().setDeadlineDate(oldTask.get().getDeadlineDate());
           }else{
               oldTask.get().setDeadlineDate(task.getDeadlineDate());
           }

//           oldTask.get().setCreatedDate(oldTask.get().getCreatedDate());

           return repository.save(oldTask.get());
       }

    }

    public Task deletetask(int id) {
        Optional<Task> task=repository.findById(id);
        if(task.isEmpty()){
            throw new DataNotPresent("Data Not Found with Id : "+id);
        }else{
            repository.deleteById(id);
            return task.get();
        }

    }

    public List<Task> gettaskbykey(String keyword) {

        List<Task> tasks=repository.findByGoalContainingIgnoreCase(keyword);
        if(tasks.isEmpty()){
            throw new DataNotPresent("Data Not Found with Keyword : "+keyword);
        }else {
            return tasks;
        }
    }
}
