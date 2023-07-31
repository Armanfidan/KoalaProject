package org.koalaProject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record GroceryStore(Map<String, Integer> prices, Map<String, Offer> offers) {

    public List<String> getInvalidItems(List<String> items) {
        return items.stream()
                .filter(item -> !prices.containsKey(item))
                .collect(Collectors.toList());
    }

    private float calculateItemPrice(String item, int count) {
        float totalPrice = this.prices.get(item) * count;
        return shouldOfferBeAppliedOnItem(item, count) ?
                totalPrice * this.offers.get(item).priceMultiplier() :
                totalPrice;
    }

    private boolean shouldOfferBeAppliedOnItem(String item, int count) {
        return this.offers.containsKey(item)
                && count >= this.offers.get(item).minimumProducts();
    }

    public float calculateBasketPrice(Basket basket) {
        return basket.getContents().entrySet().stream()
                .map(itemAndCount -> this.calculateItemPrice(itemAndCount.getKey(), itemAndCount.getValue()))
                .reduce(0f, Float::sum);
    }
}
