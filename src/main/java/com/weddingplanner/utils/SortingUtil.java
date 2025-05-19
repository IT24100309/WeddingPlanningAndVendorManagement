package com.weddingplanner.utils;

import java.util.Comparator;

/**
 * Utility class for sorting collections
 */
public class SortingUtil {
    
    /**
     * Sorts the given list using bubble sort algorithm
     * @param <T> The type of elements in the list
     * @param list The list to be sorted
     * @param comparator The comparator to determine the order of the list
     */
    public static <T> void bubbleSort(CustomLinkedList<T> list, Comparator<T> comparator) {
        if (list == null || list.isEmpty() || list.size() == 1) {
            return; // Nothing to sort
        }
        
        int n = list.size();
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                T current = list.get(j);
                T next = list.get(j + 1);
                
                if (comparator.compare(current, next) > 0) {
                    // Need to swap elements
                    // Remove and reinsert elements to swap them
                    list.remove(j);
                    list.remove(j); // After removing j, j+1 becomes j
                    
                    // Add them back in swapped order
                    list.add(next);
                    list.add(current);
                    
                    swapped = true;
                }
            }
            
            // If no swapping occurred in this pass, the list is sorted
            if (!swapped) {
                break;
            }
        }
    }
    
    /**
     * Implementation of bubble sort for vendor price range sorting
     * This method extracts price ranges from string format like "1000-2000"
     * and sorts vendors by the lower bound of their price range
     * 
     * @param list The list of vendors to sort
     * @param <T> Type of elements that have a getPriceRange method
     * @param priceRangeExtractor Function to extract price range as string from T
     */
    public static <T> void bubbleSortByPriceRange(CustomLinkedList<T> list, 
                                              PriceRangeExtractor<T> priceRangeExtractor) {
        bubbleSort(list, (v1, v2) -> {
            String priceRange1 = priceRangeExtractor.getPriceRange(v1);
            String priceRange2 = priceRangeExtractor.getPriceRange(v2);
            
            int lowerBound1 = extractLowerBound(priceRange1);
            int lowerBound2 = extractLowerBound(priceRange2);
            
            return Integer.compare(lowerBound1, lowerBound2);
        });
    }
    
    /**
     * Functional interface for extracting price range from an object
     * @param <T> The type of object to extract price range from
     */
    @FunctionalInterface
    public interface PriceRangeExtractor<T> {
        String getPriceRange(T item);
    }
    
    /**
     * Extracts the lower bound from a price range string like "1000-2000"
     * @param priceRange The price range string
     * @return The lower bound as an integer
     */
    private static int extractLowerBound(String priceRange) {
        if (priceRange == null || priceRange.isEmpty()) {
            return 0;
        }
        
        try {
            // Handle various price range formats
            if (priceRange.contains("-")) {
                // Format: "1000-2000"
                return Integer.parseInt(priceRange.split("-")[0].trim());
            } else if (priceRange.matches("\\d+")) {
                // Format: "1000"
                return Integer.parseInt(priceRange.trim());
            } else {
                // Try to extract any numeric value at the beginning
                String numericPart = priceRange.replaceAll("[^0-9]", "");
                return numericPart.isEmpty() ? 0 : Integer.parseInt(numericPart);
            }
        } catch (NumberFormatException e) {
            return 0; // Default value if parsing fails
        }
    }
} 