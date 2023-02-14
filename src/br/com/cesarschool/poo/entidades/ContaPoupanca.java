package br.com.cesarschool.poo.entidades;

import java.time.LocalDate;

public class ContaPoupanca extends Conta {
	private double taxaJuros;
	private int qtdDepositos;
	public ContaPoupanca(long numero, LocalDate dataAbertura, double saldo, StatusConta status, Correntista correntista,
			double taxaJuros) {
		super(numero, dataAbertura, saldo, status, correntista);
		this.taxaJuros = taxaJuros;
		this.qtdDepositos = 0;
	}
	public double getTaxaJuros() {
		return taxaJuros;
	}
	public void setTaxaJuros(double taxaJuros) {
		this.taxaJuros = taxaJuros;
	}
	public int getQtdDepositos() {
		return qtdDepositos;
	}
	public void setQtdDepositos(int qtdDepositos) {
		this.qtdDepositos = qtdDepositos;
	}
	@Override
	public void creditar(double valor) {
		double saldoAtu = getSaldo();
		saldoAtu = saldoAtu + (1 + taxaJuros/100.0)*valor;
		qtdDepositos++;
		setSaldo(saldoAtu);
	}
}
