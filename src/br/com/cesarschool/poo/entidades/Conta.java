package br.com.cesarschool.poo.entidades;

import java.time.LocalDate;
import br.com.cesarschool.poo.utils.Identificavel;

public class Conta extends Identificavel{
	private long numero;
	private LocalDate dataAbertura;
	private double saldo;
	private StatusConta status;
	private Correntista correntista;
	public Conta(long numero, LocalDate dataAbertura, double saldo, StatusConta status,
			Correntista correntista) {
		this.numero = numero;
		this.dataAbertura = dataAbertura;
		this.saldo = saldo;
		this.status = status;
		this.correntista = correntista;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public LocalDate getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public StatusConta getStatus() {
		return status;
	}
	public void setStatus(StatusConta status) {
		this.status = status;
	}
	public void debitar(double valor) {
		saldo = saldo - valor;
	}
	public void creditar(double valor) {
		saldo = saldo + valor;
	}
	@Override
	public String getIdentificadorUnico() {
		return "" + numero;
	}
	public Correntista getCorrentista() {
		return this.correntista;
	}
}
