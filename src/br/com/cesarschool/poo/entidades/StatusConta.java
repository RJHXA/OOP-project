package br.com.cesarschool.poo.entidades;

public enum StatusConta {
	ATIVA(1, "Ativa"), 
	ENCERRADA(2, "Encerrada"), 
	BLOQUEADA(3,"Bloqueada");  
	
	private int codigo;
	private String descricao;
	
	private StatusConta(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	public static StatusConta obterPorCodigo(int codigo) {		
		StatusConta[] listaStatus = StatusConta.values();
		for (StatusConta status : listaStatus) {
			if (status.getCodigo() == codigo) {
				return status;
			}
		}
		return null;
	}

}
