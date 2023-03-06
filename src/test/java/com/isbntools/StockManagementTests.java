package com.isbntools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @Test
    void databaseIsUsedIfDataIsPresent() {
        ExternalISBNDataService mockWebService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService mockDatabaseService = mock(ExternalISBNDataService.class);

        String isbn = "0140177396";
        when(mockDatabaseService.lookup(isbn)).thenReturn(new Book(isbn, "a", "a"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(mockWebService);
        stockManager.setDatabaseService(mockDatabaseService);

        stockManager.getLocatorCode(isbn);
        verify(mockDatabaseService, times(1)).lookup(isbn);
        verify(mockWebService, times(0)).lookup(anyString());
    }

    @Test
    void webServiceIsUsedIfDataIsNotPresentInDatabase() {
        ExternalISBNDataService mockWebService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService mockDatabaseService = mock(ExternalISBNDataService.class);

        String isbn = "0140177396";
        when(mockDatabaseService.lookup(isbn)).thenReturn(null);
        when(mockWebService.lookup(isbn)).thenReturn(new Book(isbn, "a", "a"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(mockWebService);
        stockManager.setDatabaseService(mockDatabaseService);

        stockManager.getLocatorCode(isbn);
        verify(mockDatabaseService, times(1)).lookup(isbn);
        verify(mockWebService, times(1)).lookup(isbn);
    }
}
