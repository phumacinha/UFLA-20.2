package br.dcc.ufla.todolist.task.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author Pedro Antônio de Souza
 */

@Entity
@Table(name = "tasks")
@Data
@JsonPropertyOrder({ "id", "description", "due_date", "complete" })
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Descrição da tarefa é obrigatória.")
    @Size(min = 1, max = 512)
    @Lob
    private String description;
    
    @Column(name = "due_date")
    @JsonProperty("due_date")
    @NotNull(message = "Prazo é obrigatório.")
    @Future
    private Date dueDate;
    
    @NotNull
    private Boolean complete = false;    
}
