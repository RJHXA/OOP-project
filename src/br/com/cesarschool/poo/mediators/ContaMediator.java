package br.com.cesarschool.poo.mediators;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import br.com.cesarschool.poo.entidades.Conta;
import br.com.cesarschool.poo.entidades.ContaPoupanca;
import br.com.cesarschool.poo.entidades.Correntista;
import br.com.cesarschool.poo.entidades.StatusConta;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoJaExistente;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoNaoExistente;
import br.com.cesarschool.poo.repositorios.RepositorioConta;
import br.com.cesarschool.poo.utils.Comparavel;
import br.com.cesarschool.poo.utils.Ordenador;
import br.com.cesarschool.poo.utils.conta.*;

public class ContaMediator {
	
	public static final int ESCORE_NAO_CALCULADO = -1;
	public static final int ESCORE_ZERO = 0;
	public static final int BRONZE = 1;
	public static final int PRATA = 2;
	public static final int OURO = 3;
	public static final int DIAMANTE = 4;
	
	private static final String MSG_CONTA_NAO_INFORMADA = "Conta n�o informada"; 
	private static final String MSG_NUMERO_MENOR_IGUAL_A_ZERO = "N�mero da conta negativo ou zero"; 
	private static final String MSG_STATUS_NAO_INFORMADO = "Status n�o informado"; 
	private static final String MSG_DATA_ABERTURA_NAO_INFORMADA = "Data de abertura n�o informada";
	private static final String MSG_DATA_ABERTURA_MAIOR_DATA_ATUAL = "Data de abertura menor que a data atual";
	private static final String MSG_DATA_ABERTURA_MENOR_UM_MES = "Data de abertura muito antiga";
	private static final String MSG_CREDITO_NP_CONTA_ENCERRADA = "Cr�dito n�o permitido: conta encerrada";
	private static final String MSG_VALOR_CREDITO_INVALIDO = "Valor de cr�dito inv�lido";
	private static final String MSG_DEBITO_NP_CONTA_BLOQUEADA = "D�bito n�o permitido: conta bloqueada";
	private static final String MSG_VALOR_DEBITO_INVALIDO = "Valor de d�dito inv�lido";
	private static final String MSG_CONTA_NAO_INCLUIDA = "Conta j� cadastrada ou reposit�rio cheio";
	private static final String MSG_CONTA_NAO_ENCONTRADA = "Conta n�o cadastrada";
	private static final String MSG_CORRENTISTA_NAO_ENCONTRADO = "Correntista n�o encontrado";
	private static final String MSG_TAXA_JUROS_INVALIDA = "Taxa de juros inv�lida";
	private static final int TRINTA_DIAS = 30;
	private static final int LIMITE_BRONZE = 5800;
	private static final int LIMITE_PRATA = 13000;
	private static final int LIMITE_OURO = 39000;
	private static final int ZERO = 0;
	
	private RepositorioConta repositorio = RepositorioConta.getInstancia();
	private CorrentistaMediator correntistaMediator = new CorrentistaMediator();
	
	public StatusValidacaoConta incluir(Conta conta, String cpf) throws ExcecaoObjetoJaExistente {
		StatusValidacaoConta status = validar(conta, cpf);
		if (status.isValido()) {
			boolean retornoRepositorio = repositorio.incluir(conta);
			if (!retornoRepositorio) {
				status.getCodigosErros()[0] = StatusValidacaoConta.CONTA_NAO_INCLUIDA;
				status.getMensagens()[0] = MSG_CONTA_NAO_INCLUIDA;
				status.setValido(false);
			}
		}
		return status;
	}
	public StatusValidacaoConta alterar(Conta conta, String cpf) throws ExcecaoObjetoNaoExistente {
		StatusValidacaoConta status = validar(conta, cpf);
		if (status.isValido()) {
			boolean retornoRepositorio = repositorio.alterar(conta);
			if (!retornoRepositorio) {
				status.getCodigosErros()[0] = StatusValidacaoConta.CONTA_NAO_ENCONTRADA;
				status.getMensagens()[0] = MSG_CONTA_NAO_ENCONTRADA;
				status.setValido(false);
			}
		}
		return status;
	}
	public boolean excluir(long numero) throws ExcecaoObjetoNaoExistente {
		return repositorio.excluir(numero);
	}	
	public Conta buscar(long numero) {
		return repositorio.buscar(numero);
	}
	
	public StatusValidacaoConta creditar(long numero, double valor) throws ExcecaoObjetoNaoExistente {
		int[] codigoStatus = new int[StatusValidacaoConta.QTD_SITUACOES_EXCECAO]; 
		String[] mensagensStatus = new String[StatusValidacaoConta.QTD_SITUACOES_EXCECAO];
		int contErros = 0;
		Conta conta = buscar(numero);
		if (valor <= 0) {
			codigoStatus[contErros++] = StatusValidacaoConta.VALOR_CREDITO_INVALIDO;
			mensagensStatus[contErros] = MSG_VALOR_CREDITO_INVALIDO;						
		}		
		if (conta == null) {
			codigoStatus[contErros++] = StatusValidacaoConta.CONTA_NAO_ENCONTRADA;
			mensagensStatus[contErros] = MSG_CONTA_NAO_ENCONTRADA;			
		} else if (StatusConta.ENCERRADA.equals(conta.getStatus())) {
			codigoStatus[contErros++] = StatusValidacaoConta.CREDITO_NP_CONTA_ENCERRADA;
			mensagensStatus[contErros] = MSG_CREDITO_NP_CONTA_ENCERRADA;							
		}
		StatusValidacaoConta statusVal = new StatusValidacaoConta(codigoStatus, mensagensStatus, contErros == ZERO);	
		if (statusVal.isValido()) {
			conta.creditar(valor);
			repositorio.alterar(conta);
		}
		return statusVal;
	}
	public StatusValidacaoConta debitar(long numero, double valor) throws ExcecaoObjetoNaoExistente {
		int[] codigoStatus = new int[StatusValidacaoConta.QTD_SITUACOES_EXCECAO]; 
		String[] mensagensStatus = new String[StatusValidacaoConta.QTD_SITUACOES_EXCECAO];
		int contErros = 0;
		Conta conta = buscar(numero);
		if (valor <= 0) {
			codigoStatus[contErros++] = StatusValidacaoConta.VALOR_DEBITO_INVALIDO;
			mensagensStatus[contErros] = MSG_VALOR_DEBITO_INVALIDO;						
		}		
		if (conta == null) {
			codigoStatus[contErros++] = StatusValidacaoConta.CONTA_NAO_ENCONTRADA;
			mensagensStatus[contErros] = MSG_CONTA_NAO_ENCONTRADA;			
		} else if (StatusConta.BLOQUEADA.equals(conta.getStatus())) {
			codigoStatus[contErros++] = StatusValidacaoConta.DEBITO_NP_CONTA_BLOQUEADA;
			mensagensStatus[contErros] = MSG_DEBITO_NP_CONTA_BLOQUEADA;							
		}
		StatusValidacaoConta statusVal = new StatusValidacaoConta(codigoStatus, mensagensStatus, contErros == ZERO);	
		if (statusVal.isValido()) {
			conta.debitar(valor);
			repositorio.alterar(conta);
		}
		return statusVal;
	}
	public int calcularEscoreConta(long numero) {
		Conta conta = buscar(numero);
		if (conta == null || StatusConta.BLOQUEADA.equals(conta.getStatus())) {
			return ESCORE_NAO_CALCULADO;
		} else if (StatusConta.ENCERRADA.equals(conta.getStatus())) {
			return ESCORE_ZERO;
		} else {
			long tempoVidaConta = calcularTempoVidaConta(conta);
			double f = conta.getSaldo()*3 + tempoVidaConta*2;
			if (f < LIMITE_BRONZE) {
				return BRONZE;
			} else if (f >= LIMITE_BRONZE && f < LIMITE_PRATA) {
				return PRATA;
			} else if (f >= LIMITE_PRATA && f < LIMITE_OURO) {
				return OURO;
			} else {
				return DIAMANTE;
			}
		}
	}
	
	public boolean encerrarConta(long numero) throws ExcecaoObjetoNaoExistente {
		StatusConta[] statusProibidos = {StatusConta.ENCERRADA};
		return validarAlterarStatusConta(numero, statusProibidos, StatusConta.ENCERRADA);				
	}
	
	public boolean bloquearConta(long numero) throws ExcecaoObjetoNaoExistente {
		StatusConta[] statusProibidos = {StatusConta.ENCERRADA, StatusConta.BLOQUEADA};
		return validarAlterarStatusConta(numero, statusProibidos, StatusConta.BLOQUEADA);		
	}
	public boolean desbloquearConta(long numero) throws ExcecaoObjetoNaoExistente {
		StatusConta[] statusProibidos = {StatusConta.ENCERRADA, StatusConta.ATIVA};
		return validarAlterarStatusConta(numero, statusProibidos, StatusConta.ATIVA);
	}
	
	private boolean validarAlterarStatusConta(long numero, StatusConta[] statusProibidios, StatusConta statusFinal) throws ExcecaoObjetoNaoExistente {
		Conta conta = buscar(numero);
		if (conta == null) {
			return false;
		} else {
			for (StatusConta statusConta : statusProibidios) {
				if (statusConta.equals(conta.getStatus())) {
					return false;
				}
			}
			conta.setStatus(statusFinal);
			repositorio.alterar(conta);
			return true; 
		}
	}
	
	private long calcularTempoVidaConta(Conta conta) {
		LocalDate dataAbertura = conta.getDataAbertura();
		LocalDate dataAtual = LocalDate.now();
		long dias = dataAbertura.until(dataAtual, ChronoUnit.DAYS);
		return dias; 
	}
	
	private StatusValidacaoConta validar(Conta conta, String cpf) {
		int[] codigoStatus = new int[StatusValidacaoConta.QTD_SITUACOES_EXCECAO]; 
		String[] mensagensStatus = new String[StatusValidacaoConta.QTD_SITUACOES_EXCECAO];
		int contErros = 0;
		if (conta == null) {
			codigoStatus[contErros++] = StatusValidacaoConta.CONTA_NAO_INFORMADA;
			mensagensStatus[contErros] = MSG_CONTA_NAO_INFORMADA;			
		} else {
			if (conta.getNumero() <= 0) {
				codigoStatus[contErros++] = StatusValidacaoConta.NUMERO_MENOR_IGUAL_A_ZERO;
				mensagensStatus[contErros] = MSG_NUMERO_MENOR_IGUAL_A_ZERO;
			} 
			if (conta.getStatus() == null) {
				codigoStatus[contErros++] = StatusValidacaoConta.STATUS_NAO_INFORMADO;
				mensagensStatus[contErros] = MSG_STATUS_NAO_INFORMADO;				
			}			
			if (conta.getDataAbertura() == null) {
				codigoStatus[contErros++] = StatusValidacaoConta.DATA_ABERTURA_NAO_INFORMADA;
				mensagensStatus[contErros] = MSG_DATA_ABERTURA_NAO_INFORMADA;				
			}			
			if (dataAberturaMaiorDataAtual(conta.getDataAbertura())) {
				codigoStatus[contErros++] = StatusValidacaoConta.DATA_ABERTURA_MAIOR_DATA_ATUAL;
				mensagensStatus[contErros] = MSG_DATA_ABERTURA_MAIOR_DATA_ATUAL;								
			}
			if (dataAberturaMenorDataAtualUmMes(conta.getDataAbertura())) {
				codigoStatus[contErros++] = StatusValidacaoConta.DATA_ABERTURA_MENOR_UM_MES;
				mensagensStatus[contErros] = MSG_DATA_ABERTURA_MENOR_UM_MES;												
			}
			Correntista correntista = correntistaMediator.buscar(cpf);
			if (correntista == null) {
				codigoStatus[contErros++] = StatusValidacaoConta.CORRENTISTA_NAO_ENCONTRADO;
				mensagensStatus[contErros] = MSG_CORRENTISTA_NAO_ENCONTRADO;																
			}
			if (conta instanceof ContaPoupanca) {
				ContaPoupanca contaPoupanca = (ContaPoupanca)conta;
				if (contaPoupanca.getTaxaJuros() <= 0) {
					codigoStatus[contErros++] = StatusValidacaoConta.TAXA_JUROS_INVALIDA;
					mensagensStatus[contErros] = MSG_TAXA_JUROS_INVALIDA;					
				}
			}
		}		
		return new StatusValidacaoConta(codigoStatus, mensagensStatus, contErros == ZERO);		
	}	
	private boolean dataAberturaMaiorDataAtual(LocalDate dataAbertura) {
		if (dataAbertura != null) {
			LocalDate dataAtual = LocalDate.now();
			return dataAbertura.isAfter(dataAtual);
		}
		return false;
	}
	private boolean dataAberturaMenorDataAtualUmMes(LocalDate dataAbertura) {
		if (dataAbertura != null) {
			LocalDate dataAtual = LocalDate.now();
			return dataAbertura.isBefore(dataAtual.minusDays(TRINTA_DIAS));
		}
		return false;
	}
	public Conta[] consultarTodosPorSaldoCrescente() {
		Conta[] todos = (Conta[]) repositorio.buscarTodos();
		if (todos != null && todos.length > 0) {
			Ordenador.ordenar((Comparavel[]) todos, new ComparadorContaSaldo());
		}
		return todos;
	}
	public Conta[] consultarTodosPorDataDeAbertura() {
		Conta[] todos = (Conta[]) repositorio.buscarTodos();
		if (todos != null && todos.length > 0) {
			Ordenador.ordenar((Comparavel[]) todos, new ComparadorContaDataDeAbertura());
		}
		return todos;
	}
}
