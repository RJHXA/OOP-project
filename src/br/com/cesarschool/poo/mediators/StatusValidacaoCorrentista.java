package br.com.cesarschool.poo.mediators;

/**
 * @author Anônimo 
 */
public class StatusValidacaoCorrentista {
	
	public static final int CORRENTISTA_NAO_INFORMADO = 1; 
	public static final int CPF_NAO_INFORMADO = 2; 
	public static final int NOME_NAO_INFORMADO = 3; 
	public static final int CPF_INVALIDO = 4;
	public static final int CORRENTISTA_NAO_INCLUIDO = 5;
	public static final int CORRENTISTA_NAO_ENCONTRADO = 6;
	public static final int QTD_SITUACOES_EXCECAO = 6;
	
	private int[] codigosErros;
	private String[] mensagens;
	private boolean valido;
	
	public StatusValidacaoCorrentista(int[] codigosErros, String[] mensagens, boolean valido) {
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
