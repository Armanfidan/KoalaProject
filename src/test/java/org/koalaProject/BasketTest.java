package org.koalaProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {
    private GroceryStore groceryStore;

    @BeforeEach
    void setUp() {
        Map<String, Integer> groceryStoreItems = new HashMap<>();
        groceryStoreItems.put("Apple", 35);
        groceryStoreItems.put("Banana", 20);
        groceryStoreItems.put("Melon", 50);
        groceryStoreItems.put("Lime", 15);

        Map<String, Offer> groceryStoreOffers = new HashMap<>();
        groceryStoreOffers.put("Melon", new Offer(2, (float) 1 / 2));
        groceryStoreOffers.put("Lime", new Offer(3, (float) 2 / 3));

        this.groceryStore = new GroceryStore(groceryStoreItems, groceryStoreOffers);
    }

    @Test
    void testExceptionIsThrownIfBasketIsInitialisedWithInvalidItem() {
        Exception exception = assertThrows(InvalidItemException.class, () -> {
            List<String> items = Arrays.asList("Apple", "Apple", "Banana", "ime", "Lime");
            new Basket(this.groceryStore, items);
        });

        String expectedMessage = "Basket initialised with invalid item(s): ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage), "Wrong exception thrown.");
    }

    @Test
    void testBasketPriceIsZeroIfNoItemsArePresent() throws InvalidItemException {
        Basket basket = new Basket(this.groceryStore, new ArrayList<>());
        assertEquals(this.groceryStore.calculateBasketPrice(basket), 0f, "Wrong price!");
    }

    @Test
    void testPriceCalculatedCorrectlyWithThreeLimesOffer() throws InvalidItemException {
        List<String> items = Arrays.asList("Apple", "Apple", "Banana", "Lime", "Lime", "Lime");
        Basket basket = new Basket(this.groceryStore, items);
        assertEquals(this.groceryStore.calculateBasketPrice(basket), 120f, "Wrong price!");
    }

    @Test
    void testPriceCalculatedCorrectlyWithTwoLimesNoOffer() throws InvalidItemException {
        List<String> items = Arrays.asList("Apple", "Apple", "Banana", "Lime", "Lime");
        Basket basket = new Basket(this.groceryStore, items);
        assertEquals(this.groceryStore.calculateBasketPrice(basket), 120f, "Wrong price!");
    }

    @Test
    void testPriceCalculatedCorrectlyWithTwoMelonsOffer() throws InvalidItemException {
        List<String> items = Arrays.asList("Apple", "Apple", "Banana", "Melon", "Melon");
        Basket basket = new Basket(this.groceryStore, items);
        assertEquals(this.groceryStore.calculateBasketPrice(basket), 140f, "Wrong price!");
    }

    @Test
    void testPriceCalculatedCorrectlyWithOneMelonNoOffer() throws InvalidItemException {
        List<String> items = Arrays.asList("Apple", "Apple", "Banana", "Melon");
        Basket basket = new Basket(this.groceryStore, items);
        assertEquals(this.groceryStore.calculateBasketPrice(basket), 140f, "Wrong price!");
    }

    @Test
    void testPriceCalculatedCorrectlyWithNoOffers() throws InvalidItemException {
        List<String> items = Arrays.asList("Apple", "Apple", "Banana");
        Basket basket = new Basket(this.groceryStore, items);
        assertEquals(this.groceryStore.calculateBasketPrice(basket), 90f, "Wrong price!");
    }
}