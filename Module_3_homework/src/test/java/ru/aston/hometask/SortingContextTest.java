package ru.aston.hometask;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import ru.aston.hometask.strategy.BubbleSortingStrategy;
import ru.aston.hometask.strategy.MergeSortingStrategy;
import ru.aston.hometask.strategy.SortingContext;

class SortingContextTest {

    static int[] unsorted = {1, 34, 87, 42, 3, 5, 99, 43};
    static int[] sorted  = {1, 3, 5, 34, 42, 43, 87, 99};

    @Test
    void when_BubbleSortingStrategiInitialized_then_BubbleSortingStrategyUsed() {
        SortingContext context = new SortingContext();
        context.setSortingStrategy(new BubbleSortingStrategy());

        int[] result = context.sortArray(unsorted);

        assertArrayEquals(sorted, result);
    }

    @Test
    void when_MergeSortingStrategiInitialized_then_MergeSortingStrategyUsed() {
        SortingContext context = new SortingContext();
        context.setSortingStrategy(new MergeSortingStrategy());

        int[] result = context.sortArray(unsorted);

        assertArrayEquals(sorted, result);
    }
}
