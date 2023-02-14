package br.com.cesarschool.poo.utils;

public class Ordenador {

    private static final Comparador COMPARADOR_DE_COMPARAVEIS = new ComparadorDeComparavel();

//	public static void ordenar(Comparavel[] comparaveis) {
//		Comparavel ax = null;
//		for (int i = 0; i < comparaveis.length; i++) {
//			for (int k = i; k < comparaveis.length; k++) {
//				if (comparaveis[i].comparar(comparaveis[k]) > 0) {
//					ax = comparaveis[i];
//					comparaveis[i] = comparaveis[k];
//					comparaveis[k] = ax;
//				}
//			}
//		}
//	}
    /**
     * Este m�todo substitui totalmente a replica��o de c�digo do m�todo acima!!!!
     *
     */
    public static void ordenar(Comparavel[] comparaveis) {
        ordenar(comparaveis, COMPARADOR_DE_COMPARAVEIS);
    }
    public static void ordenar(Object[] comparaveis, Comparador comparador) {
        Object ax = null;
        for (int i = 0; i < comparaveis.length; i++) {
            for (int k = i; k < comparaveis.length; k++) {
                if (comparador.comparar(comparaveis[i], comparaveis[k]) > 0) {
                    ax = comparaveis[i];
                    comparaveis[i] = comparaveis[k];
                    comparaveis[k] = ax;
                }
            }
        }
    }
}
