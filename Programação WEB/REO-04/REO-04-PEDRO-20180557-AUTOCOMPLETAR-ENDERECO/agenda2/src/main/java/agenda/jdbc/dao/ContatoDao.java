package agenda.jdbc.dao;

import agenda.jdbc.ConnectionFactory;
import agenda.jdbc.dao.DAOException.DAOExceptionType;
import agenda.jdbc.modelo.Contato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO da entidade Contato.
 * @author Pedro Antônio de Souza
 */
public class ContatoDao {
    // Conexão com o vanco de dados
    private final Connection connection;
    
    public ContatoDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    /**
     * Adiciona novo contato ao banco de dados.
     * @param contato Contato a ser adicionado.
     */
    public void adiciona (Contato contato) {
        String sql = "INSERT INTO Contato"
                + " (nome, telefone, email, cep, endereco, numero, bairro,"
                + " estado, cidade) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Prepared statament para insersão
        try (PreparedStatement stmt = connection.prepareStatement(sql,
                                     Statement.RETURN_GENERATED_KEYS);){
            
            // Define valores
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getCep());
            stmt.setString(5, contato.getEndereco());
            stmt.setString(6, contato.getNumero());
            stmt.setString(7, contato.getBairro());
            stmt.setString(8, contato.getEstado());
            stmt.setString(9, contato.getCidade());
            
            // Executa
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DAOException(DAOExceptionType.NENHUMA_LINHA_AFETADA,
                        "Erro ao adicionar contato, nenhuma dado registrado.");
            }
            
            try  (ResultSet idGerada = stmt.getGeneratedKeys()) {
                if (idGerada.next()) {
                    contato.setId(idGerada.getLong(1));
                }
                else {
                    throw new DAOException(DAOExceptionType.NENHUM_ID_OBTIDO, 
                            "Erro ao adicionar contato, nenhum ID obtido.");
                }
            }
            
            stmt.close();
        }
        catch (SQLException e) {
            throw new DAOException("Erro ao adicionar novo contato: " + e);
        }
    }
    
    /**
     * Altera dados de um contato no banco de dados.
     * @param contato Contato alterado.
     */
    public void altera (Contato contato) {
        // SQL para alteração.
        String sql = "UPDATE Contato SET nome=?, telefone=?, email=?,"
                + " cep=?, endereco=?, numero=?, bairro=?, estado=?, cidade=?"
                + " WHERE id=?";
        
        // Prepare Statement para alteração
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define dados
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getCep());
            stmt.setString(5, contato.getEndereco());
            stmt.setString(6, contato.getNumero());
            stmt.setString(7, contato.getBairro());
            stmt.setString(8, contato.getEstado());
            stmt.setString(9, contato.getCidade());
            stmt.setLong(10, contato.getId());
            
            // Executa e fecha
            stmt.execute();
            stmt.close();
        }
        catch (SQLException e) {
            throw new DAOException("Erro ao alterar contato: " + e);
        }
    }
    
    /**
     * Remove um contato do banco de dados.
     * @param contato Contato a ser removido.
     */
    public void remove (Contato contato) {
        String sql = "DELETE FROM Contato WHERE id=?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Define id
            stmt.setLong(1, contato.getId());
            
            // Executa           
            if (stmt.executeUpdate() == 0) {
                throw new DAOException(DAOExceptionType.NENHUMA_LINHA_AFETADA,
                        "Nenhuma linha afetada.");
            }
            
            // Fecha
            stmt.close();
        }
        catch (SQLException e) {
            throw new DAOException("Erro ao remover o contato: " + e);
        }
    }
    
    /**
     * Retorna lista de contatos registrados.
     * @return Lista de contatos registrados.
     */
    public ArrayList<Contato> lista () {
        try {
            // Prepared statement com query para listagem
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM"
                    + " Contato ORDER BY id ASC");
            
            // Executa a query e armazena seu resultado
            ResultSet rs = stmt.executeQuery();
            
            // Array com resultado
            ArrayList<Contato> lista = new ArrayList<>();
            
            // Percorre os resultados
            while (rs.next()) {
                // Cria Contato
                Contato contato = new Contato();
                contato.setId(rs.getLong("id"));
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));
                contato.setCep(rs.getString("cep"));
                contato.setEndereco(rs.getString("endereco"));
                contato.setNumero(rs.getString("numero"));
                contato.setBairro(rs.getString("bairro"));
                contato.setCidade(rs.getString("cidade"));
                contato.setEstado(rs.getString("estado"));
                
                // Adiciona à lista
                lista.add(contato);
            }
            
            // Fecha result set e prepared statement
            rs.close();
            stmt.close();
            
            return lista;
        }
        catch (SQLException e) {
            throw new DAOException("Erro ao gerar lista: " + e);
        }
    }
}
