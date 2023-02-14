package br.com.cesarschool.poo.telas;

import br.com.cesarschool.poo.entidades.Correntista;
import br.com.cesarschool.poo.mediators.CorrentistaMediator;

public class ProgramaCadastroConta {

	public static void main(String[] args) {

		carregarCorrentistas();
		TelaConta tela = new TelaConta();
		tela.executaTela();
	}
	 
	// m�todo para incluir alguns correntistas!!
	private static void carregarCorrentistas() {
		CorrentistaMediator correntistaMediator = new CorrentistaMediator();
		correntistaMediator.incluir(new Correntista("12345678901", "Claudio"));
		correntistaMediator.incluir(new Correntista("10987654321", "Maria"));
		correntistaMediator.incluir(new Correntista("00000000000", "Sergio"));
		correntistaMediator.incluir(new Correntista("99999999999", "Kamilla"));
		correntistaMediator.incluir(new Correntista("11111111111", "Josa"));
		correntistaMediator.incluir(new Correntista("66666666666", "Marcia"));
	}
}
