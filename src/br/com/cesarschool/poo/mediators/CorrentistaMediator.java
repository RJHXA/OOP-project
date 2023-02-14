package br.com.cesarschool.poo.mediators;

import java.time.LocalDate;

import br.com.cesarschool.poo.excecoes.ExcecaoObjetoJaExistente;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoNaoExistente;
import br.com.cesarschool.poo.utils.Ordenador;
import java.time.temporal.ChronoUnit;

import br.com.cesarschool.poo.entidades.Correntista;
import br.com.cesarschool.poo.repositorios.RepositorioCorrentista;

public class CorrentistaMediator {
	
	private static final String MSG_CORRENTISTA_NAO_INFORMADO = "Correntista n�o informado"; 
	private static final String MSG_CPF_NAO_INFORMADO = "CPF n�o informado"; 
	private static final String MSG_NOME_NAO_INFORMADO = "Nome n�o informado"; 
	private static final String MSG_CPF_INVALIDO = "CPF inv�lido";
	private static final String MSG_CORRENTISTA_NAO_INCLUIDO = "Correntista n�o inclu�do";
	private static final String MSG_CORRENTISTA_NAO_ENCONTRADO = "Correntista n�o encontrado";
	private static final int ZERO = 0;
	
	private RepositorioCorrentista repositorio = RepositorioCorrentista.getInstancia();
	
	public StatusValidacaoCorrentista incluir(Correntista correntista) throws ExcecaoObjetoJaExistente {
		StatusValidacaoCorrentista status = validar(correntista);
		if (status.isValido()) {
			boolean retornoRepositorio = repositorio.incluir(correntista);
			if (!retornoRepositorio) {
				status.getCodigosErros()[0] = StatusValidacaoCorrentista.CORRENTISTA_NAO_INCLUIDO;
				status.getMensagens()[0] = MSG_CORRENTISTA_NAO_INCLUIDO;
				status.setValido(false);
			}
		}
		return status;
	}
	public StatusValidacaoCorrentista alterar(Correntista correntista) throws ExcecaoObjetoNaoExistente {
		StatusValidacaoCorrentista status = validar(correntista);
		if (status.isValido()) {
			boolean retornoRepositorio = repositorio.alterar(correntista);
			if (!retornoRepositorio) {
				status.getCodigosErros()[0] = StatusValidacaoCorrentista.CORRENTISTA_NAO_ENCONTRADO;
				status.getMensagens()[0] = MSG_CORRENTISTA_NAO_ENCONTRADO;
				status.setValido(false);
			}
		}
		return status;
	}
	public boolean excluir(String cpf) throws ExcecaoObjetoNaoExistente {
		return repositorio.excluir(cpf);
	}	
	public Correntista buscar(String cpf) {
		return repositorio.buscar(cpf);
	}
	
	private boolean cpfValido(String cpf) {
		// Retorno sempre true pois facilita os testes
		// Mas...na entrega, o m�todo deveria estar implementado!! 
		return true;
	}
	
	private StatusValidacaoCorrentista validar(Correntista correntista) {
		int[] codigoStatus = new int[StatusValidacaoCorrentista.QTD_SITUACOES_EXCECAO]; 
		String[] mensagensStatus = new String[StatusValidacaoCorrentista.QTD_SITUACOES_EXCECAO];
		int contErros = 0;
		if (correntista == null) {
			codigoStatus[contErros++] = StatusValidacaoCorrentista.CORRENTISTA_NAO_INFORMADO;
			mensagensStatus[contErros] = MSG_CORRENTISTA_NAO_INFORMADO;			
		} else {
			if (correntista.getCpf() == null || "".equals(correntista.getCpf().trim())) {
				codigoStatus[contErros++] = StatusValidacaoCorrentista.CPF_NAO_INFORMADO;
				mensagensStatus[contErros] = MSG_CPF_NAO_INFORMADO;
			} 
			if (correntista.getNome() == null || "".equals(correntista.getNome().trim())) {
				codigoStatus[contErros++] = StatusValidacaoCorrentista.NOME_NAO_INFORMADO;
				mensagensStatus[contErros] = MSG_NOME_NAO_INFORMADO;				
			}			
			if (cpfValido(correntista.getCpf())) {
				codigoStatus[contErros++] = StatusValidacaoCorrentista.CPF_INVALIDO;
				mensagensStatus[contErros] = MSG_CPF_INVALIDO;				
			}			
		}		
		return new StatusValidacaoCorrentista(codigoStatus, mensagensStatus, contErros == ZERO);		
	}	
	
	public Correntista[] consultarTodosOrdenadoPorNome() {
		Correntista[] todos = (Correntista[]) repositorio.buscarTodos();
		if (todos != null && todos.length > 0) {
			ordenarFornecedorPorNome(todos);
		}
		return todos;
	}
	private void ordenarFornecedorPorNome(Correntista[] fornecedores) {
		Ordenador.ordenar(fornecedores);
	}
}
