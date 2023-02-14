package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.entidades.Correntista;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoJaExistente;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoNaoExistente;

public class RepositorioCorrentista extends RepositorioGenerico {

	private static final int TAMANHO_MAX_CONTAS = 1000;
	private static RepositorioCorrentista instancia;
	
	private Correntista[] cadastroCorrentista = new Correntista[TAMANHO_MAX_CONTAS];
	private int tamanhoAtual = 0;
	
	public static RepositorioCorrentista getInstancia() {
		if (instancia == null) {
			instancia = new RepositorioCorrentista();
		}
		return instancia;
	}
	private RepositorioCorrentista() {
		
	}
	public boolean incluir(Correntista correntista) throws ExcecaoObjetoJaExistente {
		if (buscarIndice(correntista.getCpf()) != -1) {
			throw new ExcecaoObjetoJaExistente();
		} else if (tamanhoAtual == TAMANHO_MAX_CONTAS - 1) {
			throw new ExcecaoObjetoJaExistente();
		} else {
			for (int i = 0; i < cadastroCorrentista.length; i++) {
				if (cadastroCorrentista[i] == null) {
					cadastroCorrentista[i] = correntista;
					break;
				}
			}
			tamanhoAtual++; 
			return true; 
		}
	}
	public boolean alterar(Correntista correntista) throws ExcecaoObjetoNaoExistente {
		int indice = buscarIndice(correntista.getCpf()); 
		if (indice == -1) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			cadastroCorrentista[indice] = correntista;
			return true; 
		}
	}
	
	public Correntista buscar(String cpf) {
		int indice = buscarIndice(cpf);
		if (indice == -1) {
			return null;
		} else {
			return cadastroCorrentista[indice];
		}
	}
	
	public boolean excluir(String cpf) throws ExcecaoObjetoNaoExistente {
		int indice = buscarIndice(cpf);
		if (indice == -1) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			cadastroCorrentista[indice] = null;
			tamanhoAtual--;
			return true;
		}		
	}
	
	private int buscarIndice(String cpf) {		
		for (int i = 0; i < cadastroCorrentista.length; i++) {
			Correntista correntista = cadastroCorrentista[i];
			if (correntista != null && correntista.getCpf().equals(cpf)) {
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
