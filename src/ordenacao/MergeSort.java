package ordenacao;

import java.util.Arrays;

public class MergeSort {
    public static int[] mergeSort(int[] lista){
        if(lista.length <= 1){
            return lista;
        }

        int meio = lista.length/2;

        int[] esquerda = mergeSort(Arrays.copyOfRange(lista, 0, meio));

        int[] direita = mergeSort(Arrays.copyOfRange(lista, meio, lista.length));

        return merge(esquerda, direita);
    }

    public static int[] merge(int[] esquerda, int[] direita){
        int[] resultado = new int[esquerda.length + direita.length];
        int i = 0, j = 0, k = 0;

        while (i < esquerda.length && j < direita.length) {
            if(esquerda[i] <= direita[j]) {
                resultado[k] = esquerda[i];
                i++;
            } else{
                resultado[k] = direita[j];
                j++;
            }
            k++;
        }

        while (i < esquerda.length){
            resultado[k] = esquerda[i];
            i++;
            k++;
        }

        while (j < direita.length) {
            resultado[k] = direita[j];
            j++;
            k++;
        }

        return resultado;
    }
}
