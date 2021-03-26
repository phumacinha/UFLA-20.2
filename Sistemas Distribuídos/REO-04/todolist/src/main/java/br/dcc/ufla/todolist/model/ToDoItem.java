package br.dcc.ufla.todolist.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Pedro Antônio de Souza
 */

@Entity
@Table(name = "todolist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoItem {
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
    
    @Column(name = "creation_date")
    @JsonProperty("creation_date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Column(name = "last_update_date")
    @JsonProperty("last_update_date")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataModificacao;
    
}
