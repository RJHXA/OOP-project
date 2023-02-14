package br.com.cesarschool.poo.utils;

public class ComparadorDeComparavel implements Comparador {

    @Override
    public int comparar(Object o1, Object o2) {
        Comparavel c1 = (Comparavel)o1;
        Comparavel c2 = (Comparavel)o2;
        return c1.comparar(c2);
    }

}
