package br.com.cesarschool.poo.excecoes;

public class InformacaoValidacao {
    int codigo;
    String mensagem;

    public InformacaoValidacao(int codigo, String mensagem) {
        super();
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
