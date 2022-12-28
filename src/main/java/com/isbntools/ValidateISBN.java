package com.isbntools;

public class ValidateISBN {

    public boolean checkISBN(String isbn) {
        int length = isbn.length();
        if (length != 10 && length != 13) {
            throw new NumberFormatException("ISBN numbers must be 10 or 13 digits long");
        }

        if (length == 10) {
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

        int total = 0;
        for (int i = 0; i < 13; i++) {
            char currentChar = isbn.charAt(i);
            if (!Character.isDigit(currentChar)) {
                if (!(i == 12 && currentChar == 'X')) {
                    throw new NumberFormatException("ISBN numbers can only contain numeric digits");
                }
                currentChar = 10;
            }

            int multiple = (i % 2 == 0) ? 1 : 3;
            total += Character.getNumericValue(currentChar) * multiple;
        }

        return total % 10 == 0;
    }
}
