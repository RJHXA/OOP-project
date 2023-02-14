package br.com.cesarschool.poo.telas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import br.com.cesarschool.poo.entidades.Conta;
import br.com.cesarschool.poo.entidades.ContaPoupanca;
import br.com.cesarschool.poo.entidades.StatusConta;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoJaExistente;
import br.com.cesarschool.poo.excecoes.ExcecaoObjetoNaoExistente;
import br.com.cesarschool.poo.mediators.ContaMediator;
import br.com.cesarschool.poo.mediators.StatusValidacaoConta;

/**
 * @author An�nimo
 */
public class TelaConta {
	
	private static final int OPC_BLOQUEIO = 7;
	private static final int OPC_ENCERRAMENTO = 8;
	private static final int OPC_DESBLOQUEIO = 9; 
	private static final String DIGITE_O_NUMERO = "Digite n�mero: ";
	private static final String CONTA_NAO_ENCONTRADA = "Conta n�o encontrada!";
	private static final int NUMERO_DESCONHECIDO = -1;
	private static final Scanner ENTRADA = new Scanner(System.in);
	private ContaMediator contaMediator = new ContaMediator(); 
	
	public void executaTela() throws ExcecaoObjetoNaoExistente, ExcecaoObjetoJaExistente {
		while(true) {
			long numero = NUMERO_DESCONHECIDO;
			imprimeMenuPrincipal();
			int opcao = ENTRADA.nextInt();
			if (opcao == 1) {
				processaInclusao();
			} else if (opcao == 2) {
				numero = processaBusca();
				if (numero != NUMERO_DESCONHECIDO) {
					processaAlteracao(numero);
				} 
			} else if (opcao == 3) {
				numero = processaBusca();
				if (numero != NUMERO_DESCONHECIDO) {
					processaExclusao(numero);
				}
			} else if (opcao == 4) {
				processaBusca();
			} else if (opcao == 5) {
				numero = processaBusca();
				if (numero != NUMERO_DESCONHECIDO) {
					processaCreditar(numero);
				}
			} else if (opcao == 6) {
				numero = processaBusca();
				if (numero != NUMERO_DESCONHECIDO) {
					processaDebitar(numero);
				}			
			} else if (opcao == OPC_BLOQUEIO) {
				numero = processaBusca();
				if (numero != NUMERO_DESCONHECIDO) {
					processaBloquear(numero);
				}
			} else if (opcao == OPC_ENCERRAMENTO) {
				numero = processaBusca();
				if (numero != NUMERO_DESCONHECIDO) {
					processaEncerrar(numero);
				}									
			} else if (opcao == OPC_DESBLOQUEIO) {
				numero = processaBusca();
				if (numero != NUMERO_DESCONHECIDO) {
					processaDesbloquear(numero);
				}											
			} 
			else if (opcao == 10) {
				processaRelatorioPorSaldo();
			}
			else if (opcao == 11) {
				processaRelatorioPorDataDeAbertura();
			}
			else if (opcao == 0) {
				System.out.println("Saindo do cadastro de contas");
				System.exit(0);
			} else {
				System.out.println("Op��o inv�lida!!");
			}
		} 
	}
	
	private void imprimeMenuPrincipal() {		
		System.out.println("1- Incluir");
		System.out.println("2- Alterar");
		System.out.println("3- Excluir");
		System.out.println("4- Buscar");
		System.out.println("5- Creditar");
		System.out.println("6- Debitar");
		System.out.println("7- Bloquear");
		System.out.println("8- Encerrar");
		System.out.println("9- Desbloquear");
		System.out.println("10- Relatorio de contas por saldo");
		System.out.println("11- Relatorio de contas por data de abertura");
		System.out.println("0- Sair");
		System.out.print("Digite a op��o: ");
	}
	
	private void processaRelatorioPorSaldo() {
		Conta[] contas = contaMediator.consultarTodosPorSaldoCrescente();
		for (Conta conta : contas) {
            System.out.print("Número da conta: ");
            System.out.println(conta.getNumero());

            System.out.print("Saldo da conta: ");
            System.out.println(conta.getSaldo());

            System.out.print("Data de abertura da conta: ");
            System.out.println(conta.getDataAbertura());

            System.out.print("Correntista da conta: ");
            System.out.println(conta.getCorrentista().getNome());
		}
	}
	
	private void processaRelatorioPorDataDeAbertura() {
		Conta[] contas = contaMediator.consultarTodosPorDataDeAbertura();
		for (Conta conta : contas) {
            System.out.print("Número da conta: ");
            System.out.println(conta.getNumero());

            System.out.print("Saldo da conta: ");
            System.out.println(conta.getSaldo());

            System.out.print("Data de abertura da conta: ");
            System.out.println(conta.getDataAbertura());

            System.out.print("Correntista da conta: ");
            System.out.println(conta.getCorrentista().getNome());
		}
	}
	
	private void processaMensagensErroValidacao(StatusValidacaoConta status) {
		String[] mensagensErro = status.getMensagens();
		System.out.println("Problemas ao incuir/alterar conta:");
		for (String mensagemErro : mensagensErro) {
			if (mensagemErro != null) {
				System.out.println(mensagemErro);
			} 
		}
	}
	
	private String lerCpfCorrentista() {
		System.out.print("Digite o CPF do correntista: ");
		String cpf = ENTRADA.next();		
		return cpf;
	}
	
	private void processaInclusao() throws ExcecaoObjetoJaExistente {
		Conta conta = capturaConta(NUMERO_DESCONHECIDO);
		String cpf = lerCpfCorrentista();		
		StatusValidacaoConta status = contaMediator.incluir(conta, cpf);
		if (status.isValido()) { 
			System.out.println("Conta inclu�da com sucesso!");
		} else {
			processaMensagensErroValidacao(status);			
		}
	}
	
	private void processaAlteracao(long numero) throws ExcecaoObjetoNaoExistente {
		Conta conta = capturaConta(numero);
		String cpf = lerCpfCorrentista();
		StatusValidacaoConta status = contaMediator.alterar(conta, cpf);
		if (status.isValido()) { 
			System.out.println("Conta alterada com sucesso!");
		} else {
			processaMensagensErroValidacao(status);		
		}
	}
	
	private long processaBusca() {
		System.out.print(DIGITE_O_NUMERO);
		long numero = ENTRADA.nextLong();
		Conta conta = contaMediator.buscar(numero);
		if (conta == null) {
			System.out.println(CONTA_NAO_ENCONTRADA);
			return NUMERO_DESCONHECIDO;
		} else {
			System.out.println("Numero: " + conta.getNumero());
			System.out.println("Saldo: " + conta.getSaldo());
			System.out.println("Data Abertura: " + conta.getDataAbertura());			
			System.out.println("Status: " + conta.getStatus().getDescricao());	
			if (conta instanceof ContaPoupanca) {
				ContaPoupanca contaPoupanca = (ContaPoupanca)conta;
				System.out.println("Taxa de juros: " + contaPoupanca.getTaxaJuros());
				System.out.println("Qtd dep�sitos: " + contaPoupanca.getQtdDepositos());
			}
			return numero;
		}
	}
	
	private void processaExclusao(long numero) throws ExcecaoObjetoNaoExistente {
		boolean retornoRepositorio = contaMediator.excluir(numero);
		if (retornoRepositorio) {
			System.out.println("Conta exclu�da com sucesso!");
		} else {
			System.out.println(CONTA_NAO_ENCONTRADA);
		}
	}
	private void processaCreditar(long numero) throws ExcecaoObjetoNaoExistente {
		System.out.print("Digite o valor a ser creditado: ");
		double valor = ENTRADA.nextDouble();
		StatusValidacaoConta statusRet = contaMediator.creditar(numero, valor);
		if (statusRet.isValido()) {
			System.out.println("Conta creditada com sucesso!");
		} else {
			processaMensagensErroValidacao(statusRet);
		}
	}
	private void processaDebitar(long numero) throws ExcecaoObjetoNaoExistente {
		System.out.print("Digite o valor a ser debitado: ");
		double valor = ENTRADA.nextDouble();
		StatusValidacaoConta statusRet = contaMediator.debitar(numero, valor);
		if (statusRet.isValido()) {
			System.out.println("Conta debitada com sucesso!");
		} else {
			processaMensagensErroValidacao(statusRet);
		}
	}
	private void processaMudancaStatus(long numero, int opcao, String msgSucesso, String msgErro) throws ExcecaoObjetoNaoExistente {
		boolean ret;                                                                                             
		if (opcao == OPC_BLOQUEIO) {
			ret = contaMediator.bloquearConta(numero);
		} else if (opcao == OPC_ENCERRAMENTO) {
			ret = contaMediator.encerrarConta(numero);
		} else {
			ret = contaMediator.desbloquearConta(numero);
		}
		if (ret) {
			System.out.println(msgSucesso);
		} else {
			System.out.println(msgErro);
		}
	}

	private void processaBloquear(long numero) throws ExcecaoObjetoNaoExistente {
		processaMudancaStatus(numero, OPC_BLOQUEIO, "Conta bloqueada com sucesso!", "Bloqueio n�o processado!"); 
	}
	private void processaEncerrar(long numero) throws ExcecaoObjetoNaoExistente {
		processaMudancaStatus(numero, OPC_ENCERRAMENTO, "Conta encerrada com sucesso!", "Encerramento n�o processado!"); 
	}
	private void processaDesbloquear(long numero) throws ExcecaoObjetoNaoExistente {
		processaMudancaStatus(numero, OPC_DESBLOQUEIO, "Conta desbloqueada com sucesso!", "Desbloqueio n�o processado!"); 
	}

	private Conta capturaConta(long numeroDaAlteracao) {
		long numero;		
		if (numeroDaAlteracao == NUMERO_DESCONHECIDO) {
			System.out.print(DIGITE_O_NUMERO);
			numero = ENTRADA.nextLong();			
		} else {
			numero = numeroDaAlteracao;
		}
		System.out.print("Digite a data de abertura (dd/mm/yyyy): ");		
		String dataAberturaStr = ENTRADA.next();
		LocalDate dataAbertura = LocalDate.parse(dataAberturaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.print("Digite o saldo: ");
		double saldo = ENTRADA.nextDouble();
		System.out.print("Digite o status: ");
		int statusInt = ENTRADA.nextInt();
		StatusConta status = StatusConta.obterPorCodigo(statusInt);
		System.out.print("Digite <1> para conta normal e <2> para conta poupan�a: ");
		int tipoConta = ENTRADA.nextInt();		
		if (tipoConta == 1) {
			return new Conta(numero, dataAbertura, saldo, status, null);	
		} else {
			System.out.print("Digite a taxa de juros: ");
			double taxaJuros = ENTRADA.nextDouble();
			return new ContaPoupanca(numero, dataAbertura, saldo, status, null, taxaJuros); 
		}
		
	}
}
