package br.dcc.ufla.todolist.task.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import br.dcc.ufla.todolist.task.model.Task;
import br.dcc.ufla.todolist.task.service.TaskService;

/**
 * @author Pedro Antônio de Souza
 */

@RestController
@RequestMapping("/todolist/1.0.0")
public class TaskController {

    @Autowired
    TaskService taskService;
    
    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        return new ResponseEntity<>(taskService.findAll(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Object> createToDoItem(@RequestBody @Valid Task toDoItem, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        
        taskService.save(toDoItem);
        return new ResponseEntity<>(toDoItem, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Task> getToDoItemById(@PathVariable("id") Long id) {
        Task toDoItem = taskService.findById(id);
        
        if (toDoItem != null) {
            return new ResponseEntity<>(toDoItem, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteToDoItem(@PathVariable("id") Long id) {
        Task toDoItem = taskService.findById(id);
        
        if (toDoItem != null) {
            taskService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateToDoItem(@PathVariable("id") Long id, @RequestBody @Valid Task toDoItem, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        
        Task _toDoItem = taskService.findById(id);
        
        if (_toDoItem != null) {
            _toDoItem.setDescription(toDoItem.getDescription());
            _toDoItem.setDueDate(toDoItem.getDueDate());
            _toDoItem.setComplete(toDoItem.getComplete());
            
            taskService.save(_toDoItem);
            return new ResponseEntity<>(_toDoItem, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
