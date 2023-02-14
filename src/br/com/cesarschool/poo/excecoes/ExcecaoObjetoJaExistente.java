package br.com.cesarschool.poo.excecoes;

public class ExcecaoObjetoJaExistente extends Exception {
    public ExcecaoObjetoJaExistente() {}

    public ExcecaoObjetoJaExistente(String mensagem) {
        super(mensagem);
    }
}
