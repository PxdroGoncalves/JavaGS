package br.com.alertaorbital.excecoes;

public class ExcecoesConexao extends Exception {

    public ExcecoesConexao() {
        super("Erro de conexao com o banco de dados");
    }

    public ExcecoesConexao(String mensagem) {
        super(mensagem);
    }

    public ExcecoesConexao(Exception e) {
        super(e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
        if (e.getClass().toString().equals("class java.lang.ClassNotFoundException")) {
            System.out.println("Erro no driver, sem comunicacao com o banco de dados");
        } else if (e.getClass().toString().equals("class java.sql.SQLException")) {
            System.out.println("Informacoes de acesso incorretas, acesso negado - " + e.getMessage());
        } else {
            e.printStackTrace();
        }
    }
}