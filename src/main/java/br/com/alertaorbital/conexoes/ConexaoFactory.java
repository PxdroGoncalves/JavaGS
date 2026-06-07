package br.com.alertaorbital.conexoes;

import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    private static final String URL    = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    private static final String USUARIO = "rm567265";
    private static final String SENHA   = "290406";

    // Retorna uma conexão nova a cada chamada.
    // USE sempre dentro de try-with-resources nos DAOs:
    //   try (Connection con = ConexaoFactory.getConnection()) { ... }
    public static Connection getConnection() throws ExcecoesConexao {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
