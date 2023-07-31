package org.koalaProject;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Basket {
    // Offers have an expiry date
    //
    private final Map<String, Integer> contents;

    public Basket(GroceryStore groceryStore, List<String> contents) throws InvalidItemException {
        List<String> invalidItems = groceryStore.getInvalidItems(contents);
        if (!invalidItems.isEmpty())
            throw new InvalidItemException("Basket initialised with invalid item(s): " + invalidItems);
        this.contents = this.createContentsFrequencyMap(contents);
    }

    public Map<String, Integer> getContents() {
        return contents;
    }

    private Map<String, Integer> createContentsFrequencyMap(List<String> contents) {
        return new HashSet<>(contents).stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        item -> Collections.frequency(contents, item)
                ));
    }
}
