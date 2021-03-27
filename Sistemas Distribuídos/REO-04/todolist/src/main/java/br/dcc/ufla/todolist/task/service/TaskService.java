package br.dcc.ufla.todolist.task.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import br.dcc.ufla.todolist.task.model.Task;
import br.dcc.ufla.todolist.task.repository.TaskRepository;

/**
 * @author Pedro Antônio de Souza
 */
@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    
    public List<Task> findAll () {
        return taskRepository.findAllByOrderByDueDateAsc();
    }
    
    public Task findById (Long id) {
        Optional<Task> item = taskRepository.findById(id);
        
        if (item.isPresent()) {
            return item.get();
        }
        
        return null;
    }
    
    public Task save (Task item) {
        return taskRepository.save(item);
    }
    
    public Boolean delete (Long id) {
        if (this.findById(id) == null) {
            return false;
        }
        
       taskRepository.deleteById(id);
       return true;
    }
}
