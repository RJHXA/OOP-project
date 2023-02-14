package br.com.cesarschool.poo.utils.conta;

import br.com.cesarschool.poo.entidades.Conta;
import br.com.cesarschool.poo.utils.Comparador;

public class ComparadorContaDataDeAbertura implements Comparador {

    @Override
    public int comparar(Object o1, Object o2) {
        Conta p1 = (Conta)o1;
        Conta p2 = (Conta)o2;
        if (p1.getDataAbertura().equals(p2.getDataAbertura())) {
            return 0;
        } else if (p1.getDataAbertura().isAfter(p2.getDataAbertura())) {
            return 1;
        } else {
            return -1;
        }
    }
}
