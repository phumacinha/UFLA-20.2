package agenda.jdbc.modelo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de entidade Contato.
 * @author Pedro Antônio de Souza
 */

@Data
@NoArgsConstructor
public class Contato {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    
}
