package br.dcc.ufla.todolist.repository;

import br.dcc.ufla.todolist.model.ToDoItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pedro Antônio de Souza
 */
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {
    public List<ToDoItem> findAllByOrderByDueDateAsc();
}
