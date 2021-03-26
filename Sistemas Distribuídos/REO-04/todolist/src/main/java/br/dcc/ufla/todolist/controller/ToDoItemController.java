package br.dcc.ufla.todolist.controller;

import br.dcc.ufla.todolist.model.ToDoItem;
import br.dcc.ufla.todolist.service.ToDoItemService;
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

/**
 * @author Pedro Antônio de Souza
 */

@RestController
@RequestMapping("/todolist")
public class ToDoItemController {

    @Autowired
    ToDoItemService toDoItemService;
    
    @GetMapping
    public ResponseEntity<List<ToDoItem>> getToDoItems() {
        return new ResponseEntity<>(toDoItemService.findAll(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Object> createToDoItem(@RequestBody @Valid ToDoItem toDoItem, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        
        toDoItemService.save(toDoItem);
        return new ResponseEntity<>(toDoItem, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ToDoItem> getNoticiaById(@PathVariable("id") long id) {
        ToDoItem toDoItem = toDoItemService.findById(id);
        
        if (toDoItem != null) {
            return new ResponseEntity<>(toDoItem, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNoticia(@PathVariable("id") long id) {
        ToDoItem toDoItem = toDoItemService.findById(id);
        
        if (toDoItem != null) {
            toDoItemService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNoticia(@PathVariable("id") long id, @RequestBody @Valid ToDoItem toDoItem, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        
        ToDoItem _toDoItem = toDoItemService.findById(id);
        
        if (_toDoItem != null) {
            _toDoItem.setDescription(toDoItem.getDescription());
            _toDoItem.setDueDate(toDoItem.getDueDate());
            _toDoItem.setComplete(toDoItem.getComplete());
            
            toDoItemService.save(_toDoItem);
            return new ResponseEntity<>(_toDoItem, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
