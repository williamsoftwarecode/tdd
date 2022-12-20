package com.isbntools;

public class ValidateISBN {

    public boolean checkISBN(String isbn) {
        if (isbn.length() != 10) {
            throw new NumberFormatException("ISBN numbers must be 10 digits long");
        }

        if (!isbn.matches("[0-9]+")) {
            throw new NumberFormatException("ISBN numbers must be numeric");
        }

        int total = 0;
        for (int i = 0; i < 10; i++) {
            char currentChar = isbn.charAt(i);
            if (!Character.isDigit(currentChar)) {
                throw new NumberFormatException("ISBN numbers can only contain numeric digits");
            }
            total += currentChar * (10 - i);
        }

        return total % 11 == 0;
    }
}
