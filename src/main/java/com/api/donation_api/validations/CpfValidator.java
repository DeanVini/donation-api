package com.api.donation_api.validations;

import org.jetbrains.annotations.NotNull;

public class CpfValidator {
    public static boolean isValidCpf(@NotNull String cpf){
        String unformattedCpf = cpf.replaceAll("[^0-9]", "");
        if(unformattedCpf.length() != 11){
            return true;
        }
        if (unformattedCpf.chars().distinct().count() ==1){
            return true;
        }
        return !validateCpfDigits(unformattedCpf);
    }

    private static boolean validateCpfDigits(@NotNull String cpf) {
        int[] weightsFirstDigit = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weightsSecondDigit = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int firstDigitSum = 0;
        for (int i = 0; i < 9; i++) {
            firstDigitSum += (cpf.charAt(i) - '0') * weightsFirstDigit[i];
        }
        int firstDigit = (firstDigitSum * 10) % 11;
        if (firstDigit == 10) firstDigit = 0;

        int secondDigitSum = 0;
        for (int i = 0; i < 9; i++) {
            secondDigitSum += (cpf.charAt(i) - '0') * weightsSecondDigit[i];
        }
        secondDigitSum += firstDigit * 2;
        int secondDigit = (secondDigitSum * 10) % 11;
        if (secondDigit == 10) secondDigit = 0;

        return firstDigit == (cpf.charAt(9) - '0') && secondDigit == (cpf.charAt(10) - '0');
    }
}
