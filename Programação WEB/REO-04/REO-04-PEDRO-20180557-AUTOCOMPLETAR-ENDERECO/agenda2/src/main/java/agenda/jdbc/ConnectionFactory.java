package agenda.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Pedro Antônio de Souza
 */
public class ConnectionFactory {
    
    /**
     * Cria conexão com banco de dados.
     * @return Conexão com o banco de dados.
     */
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/programacaoweb",
                    "root",
                    "pedro1995");
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
