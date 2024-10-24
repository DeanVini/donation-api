package com.api.donation_api.validations;

import org.jetbrains.annotations.NotNull;

public class CpfValidator {
    public static boolean isCpfValido(@NotNull String cpf){
        String cpfSemFormatacao = cpf.replaceAll("[^0-9]", "");
        if(cpfSemFormatacao.length() != 11){
            return false;
        }
        if (cpfSemFormatacao.chars().distinct().count() ==1){
            return false;
        }
        return validarDigitosCpf(cpfSemFormatacao);
    }

    private static boolean validarDigitosCpf(@NotNull String cpf) {
        int[] pesosPrimeiroDigito = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesosSegundoDigito = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int somaPrimeiroDigito = 0;
        for (int i = 0; i < 9; i++) {
            somaPrimeiroDigito += (cpf.charAt(i) - '0') * pesosPrimeiroDigito[i];
        }
        int primeiroDigito = (somaPrimeiroDigito * 10) % 11;
        if (primeiroDigito == 10) primeiroDigito = 0;

        int somaSegundoDigito = 0;
        for (int i = 0; i < 9; i++) {
            somaSegundoDigito += (cpf.charAt(i) - '0') * pesosSegundoDigito[i];
        }
        somaSegundoDigito += primeiroDigito * 2;
        int segundoDigito = (somaSegundoDigito * 10) % 11;
        if (segundoDigito == 10) segundoDigito = 0;

        return primeiroDigito == (cpf.charAt(9) - '0') && segundoDigito == (cpf.charAt(10) - '0');
    }
}
