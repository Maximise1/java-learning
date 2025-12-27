package ru.aston.hometask.builder;

public class User {

    private final String name;

    private final int age;

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public static class Builder {

        private final String name;

        private int age = 0;

        public Builder(String name) {
            this.name = name;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
