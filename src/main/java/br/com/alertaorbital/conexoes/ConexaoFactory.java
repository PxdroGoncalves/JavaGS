package br.com.alertaorbital.conexoes;

import br.com.alertaorbital.excecoes.ExcecoesConexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    public static Connection getConnection() throws ExcecoesConexao {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String url      = System.getenv("DB_URL");
            String usuario  = System.getenv("DB_USER");
            String senha    = System.getenv("DB_PASSWORD");

            // Fallback local (sem variável de ambiente configurada)
            if (url == null || usuario == null || senha == null) {
                url      = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
                usuario  = "rm567265";
                senha    = "290406";
            }

            return DriverManager.getConnection(url, usuario, senha);

        } catch (ClassNotFoundException | SQLException e) {
            throw new ExcecoesConexao(e);
        }
    }
}
