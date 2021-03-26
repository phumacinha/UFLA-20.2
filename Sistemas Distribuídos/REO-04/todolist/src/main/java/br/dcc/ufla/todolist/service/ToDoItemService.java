package br.dcc.ufla.todolist.service;

import br.dcc.ufla.todolist.model.ToDoItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import br.dcc.ufla.todolist.repository.ToDoItemRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * @author Pedro Antônio de Souza
 */
@Service
public class ToDoItemService {

    @Autowired
    ToDoItemRepository toDoItemRepository;
    
    public List<ToDoItem> findAll () {
        return toDoItemRepository.findAllByOrderByDueDateAsc();
    }
    
    public ToDoItem findById (Long id) {
        Optional<ToDoItem> item = toDoItemRepository.findById(id);
        
        if (item.isPresent()) {
            return item.get();
        }
        
        return null;
    }
    
    public ToDoItem save (ToDoItem item) {
        return toDoItemRepository.save(item);
    }
    
    public Boolean delete (Long id) {
        if (this.findById(id) == null) {
            return false;
        }
        
       toDoItemRepository.deleteById(id);
       return true;
    }
}
