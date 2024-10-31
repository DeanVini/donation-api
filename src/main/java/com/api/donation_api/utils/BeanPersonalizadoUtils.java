package com.api.donation_api.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

public  class BeanPersonalizadoUtils {
    /**
     * Copia as propriedades não nulas de um objeto de origem para um objeto de destino.
     * @param origem Objeto de onde as propriedades serão copiadas.
     * @param destino Objeto para onde as propriedades serão copiadas.
     */
    public static  void  copiarPropriedadesNaoNulas(Object origem, Object destino){
        BeanUtils.copyProperties(origem, destino, obterNomesPropriedadesNulas(origem));
    }

    /**
     * Obtém os nomes das propriedades que estão nulas no objeto de origem.
     * @param origem Objeto do qual as propriedades nulas serão identificadas.
     * @return Um array de nomes de propriedades que possuem valor nulo.
     */
    private  static String[] obterNomesPropriedadesNulas(Object origem) {
        BeanWrapper origemWrapper = new BeanWrapperImpl(origem);

        return Arrays.stream(origemWrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(nome -> origemWrapper.getPropertyValue(nome) == null)
                .toArray(String[]::new);
    }
}
