package com.isbntools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockManagementTests {

    @Test
    public void testCanGetACorrectLocatorCode() {
        ExternalISBNDataService testDatabaseService =
                isbn -> null;
        ExternalISBNDataService testWebService =
                isbn -> new Book(isbn, "Of Mice and Men", "J. Steinbeck");
        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }
}
