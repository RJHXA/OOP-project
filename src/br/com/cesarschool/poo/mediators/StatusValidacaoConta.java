package br.com.cesarschool.poo.mediators;

/**
 * @author Anônimo 
 */
public class StatusValidacaoConta {
	
	public static final int CONTA_NAO_INFORMADA = 1; 
	public static final int NUMERO_MENOR_IGUAL_A_ZERO = 2; 
	public static final int STATUS_NAO_INFORMADO = 3; 
	public static final int DATA_ABERTURA_NAO_INFORMADA = 4;
	public static final int DATA_ABERTURA_MAIOR_DATA_ATUAL = 5;
	public static final int DATA_ABERTURA_MENOR_UM_MES = 6;
	public static final int CREDITO_NP_CONTA_ENCERRADA = 7;
	public static final int VALOR_CREDITO_INVALIDO = 8;
	public static final int DEBITO_NP_CONTA_BLOQUEADA = 9;
	public static final int VALOR_DEBITO_INVALIDO = 10;
	public static final int CONTA_NAO_INCLUIDA = 11;
	public static final int CONTA_NAO_ENCONTRADA = 12;
	public static final int CORRENTISTA_NAO_ENCONTRADO = 13;
	public static final int TAXA_JUROS_INVALIDA = 14;
	public static final int QTD_SITUACOES_EXCECAO = 14;
	
	private int[] codigosErros;
	private String[] mensagens;
	private boolean valido;
	
	public StatusValidacaoConta(int[] codigosErros, String[] mensagens, boolean valido) {
		super();
		this.codigosErros = codigosErros;
		this.mensagens = mensagens;
		this.valido = valido;
	}
	
	public int[] getCodigosErros() {
		return codigosErros;
	}
	public String[] getMensagens() {
		return mensagens;
	}
	public boolean isValido() {
		return valido;
	}
	void setValido(boolean valido) {
		this.valido = valido;
	}	
}
