package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.entidades.Conta;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoJaExistente;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoNaoExistente;

public class RepositorioConta extends RepositorioGenerico{

	private static final int TAMANHO_MAX_CONTAS = 1000;
	private static RepositorioConta instancia;
	private Conta[] cadastroConta = new Conta[TAMANHO_MAX_CONTAS];
	private int tamanhoAtual = 0;
	
	public static RepositorioConta getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioConta();
		}
		return instancia;
	}
	private RepositorioConta() {
		
	}
	
	public boolean incluir(Conta conta) throws ExcecaoObjetoJaExistente {
		if (buscarIndice(conta.getNumero()) != -1) {
			throw new ExcecaoObjetoJaExistente();
		} else if (tamanhoAtual == TAMANHO_MAX_CONTAS - 1) {
			return false;
		} else {
			for (int i = 0; i < cadastroConta.length; i++) {
				if (cadastroConta[i] == null) {
					cadastroConta[i] = conta;
					break;
				}
			}
			tamanhoAtual++; 
			return true; 
		}
	}
	public boolean alterar(Conta conta) throws ExcecaoObjetoNaoExistente {
		int indice = buscarIndice(conta.getNumero()); 
		if (indice == -1) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			cadastroConta[indice] = conta;
			return true; 
		}
	}
	
	public Conta buscar(long numero) {
		int indice = buscarIndice(numero);
		if (indice == -1) {
			return null;
		} else {
			return cadastroConta[indice];
		}
	}
	
	public boolean excluir(long numero) throws ExcecaoObjetoNaoExistente {
		int indice = buscarIndice(numero);
		if (indice == -1) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			cadastroConta[indice] = null;
			tamanhoAtual--;
			return true;
		}		
	}
	
	private int buscarIndice(long numero) {		
		for (int i = 0; i < cadastroConta.length; i++) {
			Conta conta = cadastroConta[i];
			if (conta != null && conta.getNumero() == numero) {
				return i; 				
			}
		}
		return -1;
	}
	@Override
	public int getTamanhoMaximoRepositorio() {
		return TAMANHO_MAX_CONTAS;
	}
}
