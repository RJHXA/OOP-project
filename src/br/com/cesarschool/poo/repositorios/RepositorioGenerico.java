package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.excecoes.ExcecaoObjetoJaExistente;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoNaoExistente;
import br.com.cesarschool.poo.utils.Identificavel;

public abstract class RepositorioGenerico {
	
	private Identificavel[] cadastro;
	private int tamanhoAtual = 0;
	
	public RepositorioGenerico() {
		cadastro = new Identificavel[getTamanhoMaximoRepositorio()];
	}
	
	public abstract int getTamanhoMaximoRepositorio(); 
	
	
	public boolean incluir(Identificavel identificavel) throws ExcecaoObjetoJaExistente, ExcecaoObjetoNaoExistente {
		if (buscarIndice(identificavel.getIdentificadorUnico()) != -1) {
			throw new ExcecaoObjetoJaExistente();
		} else if (tamanhoAtual == getTamanhoMaximoRepositorio() - 1) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			for (int i = 0; i < cadastro.length; i++) {
				if (cadastro[i] == null) {
					cadastro[i] = identificavel; 
					break;
				}
			}
			tamanhoAtual++; 
			return true; 
		}
	}
	public boolean alterar(Identificavel identificavel) throws ExcecaoObjetoNaoExistente {
		int indice = buscarIndice(identificavel.getIdentificadorUnico()); 
		if (indice == -1) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			cadastro[indice] = identificavel;
			return true; 
		}
	}
	
	public Identificavel buscar(String identificadorUnico) {
		int indice = buscarIndice(identificadorUnico);
		if (indice == -1) {
			return null;
		} else {
			return cadastro[indice];
		}
	}
	
	public boolean excluir(String identificadorUnico) throws ExcecaoObjetoNaoExistente {
		int indice = buscarIndice(identificadorUnico);
		if (indice == -1) {
			throw new ExcecaoObjetoNaoExistente();
		} else {
			cadastro[indice] = null;
			tamanhoAtual--;
			return true;
		}		
	}
	
	public Identificavel[] buscarTodos() {
		Identificavel[] resultado = new Identificavel[tamanhoAtual];
		int indice = 0;
		for (Identificavel identificavel : cadastro) {
			if (identificavel != null) {
				resultado[indice++] = identificavel; 
			}
		}
		return resultado;
	}
	
	private int buscarIndice(String identificadorUnico) {		
		for (int i = 0; i < cadastro.length; i++) {
			Identificavel identificavel = cadastro[i];
			if (identificavel != null && identificavel.getIdentificadorUnico().equals(identificadorUnico)) {
				return i; 				
			}
		}
		return -1;
	}		
}
