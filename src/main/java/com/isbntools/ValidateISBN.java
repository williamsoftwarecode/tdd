package com.isbntools;

public class ValidateISBN {

    private static final int SHORT_ISBN_LENGTH = 10;
    private static final int LONG_ISBN_LENGTH = 13;
    private static final int SHORT_ISBN_MULTIPLIER = 11;
    private static final int LONG_ISBN_MULTIPLIER = 10;

    public boolean checkISBN(String isbn) {
        int length = isbn.length();
        if (length != SHORT_ISBN_LENGTH && length != LONG_ISBN_LENGTH) {
            throw new NumberFormatException("ISBN numbers must be 10 or 13 digits long");
        }

        if (length == 10) {
            return isValidISBN10Number(isbn);
        } else {
            return isValidISBN13Number(isbn);
        }
    }

    private static boolean isValidISBN10Number(String isbn) {
        int total = 0;
        for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {
            char currentChar = isbn.charAt(i);
            if (!Character.isDigit(currentChar)) {
                if (!(i == 9 && currentChar == 'X')) {
                    throw new NumberFormatException("ISBN numbers can only contain numeric digits");
                }
                currentChar = 10;
            }
            total += Character.getNumericValue(currentChar) * (SHORT_ISBN_LENGTH - i);
        }

        return total % SHORT_ISBN_MULTIPLIER == 0;
    }

    private static boolean isValidISBN13Number(String isbn) {
        int total = 0;
        for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
            char currentChar = isbn.charAt(i);
            if (!Character.isDigit(currentChar)) {
                if (!(i == 12 && currentChar == 'X')) {
                    throw new NumberFormatException("ISBN numbers can only contain numeric digits");
                }
                currentChar = 10;
            }

            int multiple = isEvenNumber(i) ? 1 : 3;
            total += Character.getNumericValue(currentChar) * multiple;
        }

        return total % LONG_ISBN_MULTIPLIER == 0;
    }

    private static boolean isEvenNumber(int num) {
        return num % 2 == 0;
    }
}
