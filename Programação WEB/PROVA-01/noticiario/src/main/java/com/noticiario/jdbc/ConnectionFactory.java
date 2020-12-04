package com.noticiario.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe ConnectionFactory
 * @author Pedro Antônio de Souza
 */
public class ConnectionFactory {
    // Retorna uma conexão com o banco de dados.
    public Connection getConnection () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/noticiario",
                    "root",
                    "pedro1995");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver de banco de dados não encontrado.");
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados.");
        }
    } 
}
