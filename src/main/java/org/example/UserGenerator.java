package org.example;

import java.util.Random;

abstract public class UserGenerator {
    private static final Random rnd = new Random();
    private static int getRnd() {
        return rnd.nextInt(1000000);
    }

    public static User createDefaultUser() {
        return new User("User" + getRnd() + "@yandex.ru", "code" + getRnd(), "Name" + getRnd());
    }

    public static User loginWithNoEmail() {
        return new User(null, "code" + getRnd(), "Name" + getRnd());
    }
    public static User loginWithNoPassword() {
        return new User("email" + getRnd() + "@yandex.ru", null, "Name" + getRnd());
    }
}