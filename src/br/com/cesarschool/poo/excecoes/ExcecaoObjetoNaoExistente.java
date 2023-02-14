package br.com.cesarschool.poo.excecoes;

public class ExcecaoObjetoNaoExistente extends Exception {
    public ExcecaoObjetoNaoExistente() {}

    public ExcecaoObjetoNaoExistente(String mensagem) {
        super(mensagem);
    }
}
