package com.isbntools;

public class StockManager {

    private final ExternalISBNDataService service;

    public StockManager(ExternalISBNDataService service) {
        this.service = service;
    }

    public String getLocatorCode(String isbn) {
        Book book = service.lookup(isbn);

        StringBuilder locatorCode = new StringBuilder();
        locatorCode.append(isbn.substring(isbn.length() - 4));
        locatorCode.append(book.getAuthor().charAt(0));
        locatorCode.append(book.getTitle().split(" ").length);

        return locatorCode.toString();
    }
}
