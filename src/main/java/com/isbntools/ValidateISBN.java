package com.isbntools;

public class ValidateISBN {

    public boolean checkISBN(String isbn) {
        if (isbn.length() != 10) {
            throw new NumberFormatException("ISBN numbers must be 10 digits long");
        }

        int total = 0;
        for (int i = 0; i < 10; i++) {
            char currentChar = isbn.charAt(i);
            if (!Character.isDigit(currentChar)) {
                if (!(i == 9 && currentChar == 'X')) {
                    throw new NumberFormatException("ISBN numbers can only contain numeric digits");
                }
                currentChar = 10;
            }
            total += Character.getNumericValue(currentChar) * (10 - i);
        }

        return total % 11 == 0;
    }
}
