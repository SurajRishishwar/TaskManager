package com.suraj.TaskManager.repository;

import com.suraj.TaskManager.entity.Task;
import com.suraj.TaskManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findByGoalContainingIgnoreCase(String keyword);
    List<Task> findAllByUser(User user);
}
