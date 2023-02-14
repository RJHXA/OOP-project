package br.com.cesarschool.poo.utils.conta;

import br.com.cesarschool.poo.entidades.Conta;
import br.com.cesarschool.poo.utils.Comparador;

public class ComparadorContaSaldo implements Comparador {

    @Override
    public int comparar(Object o1, Object o2) {
        Conta p1 = (Conta)o1;
        Conta p2 = (Conta)o2;
        if (p1.getSaldo() == p2.getSaldo()) {
            return 0;
        }
        else if (p1.getSaldo() > p2.getSaldo()){
            return 1;
        }
        else {
        	return -1;
        }
    }
}
