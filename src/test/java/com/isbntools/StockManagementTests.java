package com.isbntools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTests {

    private static final String isbn = "0140177396";

    ExternalISBNDataService mockDatabaseService;
    ExternalISBNDataService mockWebService;
    StockManager stockManager;

    @BeforeEach
    void setup() {
        mockDatabaseService = mock(ExternalISBNDataService.class);
        mockWebService = mock(ExternalISBNDataService.class);

        stockManager = new StockManager();
        stockManager.setWebService(mockWebService);
        stockManager.setDatabaseService(mockDatabaseService);
    }

    @Test
    public void testCanGetACorrectLocatorCode() {
        when(mockDatabaseService.lookup(anyString())).thenReturn(null);
        when(mockWebService.lookup(anyString())).thenAnswer(isbn -> new Book(isbn.toString(), "Of Mice and Men", "J. Steinbeck"));

        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    void databaseIsUsedIfDataIsPresent() {
        when(mockDatabaseService.lookup(isbn)).thenReturn(new Book(isbn, "a", "a"));

        stockManager.getLocatorCode(isbn);
        verify(mockDatabaseService).lookup(isbn);
        verify(mockWebService, never()).lookup(anyString());
    }

    @Test
    void webServiceIsUsedIfDataIsNotPresentInDatabase() {
        when(mockDatabaseService.lookup(isbn)).thenReturn(null);
        when(mockWebService.lookup(isbn)).thenReturn(new Book(isbn, "a", "a"));

        stockManager.getLocatorCode(isbn);
        verify(mockDatabaseService).lookup(isbn);
        verify(mockWebService).lookup(isbn);
    }
}
