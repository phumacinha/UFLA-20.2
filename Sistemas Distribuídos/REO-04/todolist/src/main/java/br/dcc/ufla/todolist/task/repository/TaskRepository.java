package br.dcc.ufla.todolist.task.repository;

import br.dcc.ufla.todolist.task.model.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pedro Antônio de Souza
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByOrderByDueDateAsc();
}
