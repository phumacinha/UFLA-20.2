package agenda.jdbc.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe DAOException
 * @author Pedro Antônio de Souza
 */
@Getter
@Setter
public class DAOException extends RuntimeException {
    public enum DAOExceptionType {
        ERRO_INTERNO,
        NENHUMA_LINHA_AFETADA,
        NENHUM_ID_OBTIDO
    }
    
    private DAOExceptionType type = null;
    
    /**
     * Exceção para ser lançada pelas DAOs.
     * @param mensagem Mensagem da exceção.
     */
    public DAOException (String mensagem) {
        super(mensagem);
    }
    
    /**
     * Exceção para ser lançada pelas DAOs com type.
     * @param tipo Tipo de exceção.
     * @param mensagem Mensagem da exceção.
     */
    public DAOException (DAOExceptionType tipo, String mensagem) {
        super(mensagem);
        this.type = tipo;
    }
    
}
