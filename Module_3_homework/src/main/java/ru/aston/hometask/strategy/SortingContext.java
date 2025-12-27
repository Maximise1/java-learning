package ru.aston.hometask.strategy;

public class SortingContext {

    private SortingStrategy sortingStrategy;

    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    public int[] sortArray(int[] array) {
        return this.sortingStrategy.sort(array);
    }
}