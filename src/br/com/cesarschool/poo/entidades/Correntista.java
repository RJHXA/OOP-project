package br.com.cesarschool.poo.entidades;

import br.com.cesarschool.poo.utils.Comparavel;
import br.com.cesarschool.poo.utils.Identificavel;

public class Correntista extends Identificavel implements Comparavel {
	private String cpf;
	private String nome;
	public Correntista(String cpf, String nome) {
		super();
		this.cpf = cpf;
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String getIdentificadorUnico() {
		return cpf;
	}
	
	@Override
	public int comparar(Object objeto) {
		Correntista outro = (Correntista)objeto;
		return this.getCpf().compareTo(outro.getCpf());
	} 
}
