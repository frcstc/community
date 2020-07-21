package com.hzgood.community.provider;

import com.hzgood.community.model.User;

public class UserContext {
    private UserContext() {

    }

    private static final ThreadLocal<User> context = new ThreadLocal<>();

    public static void set(User user) {
        context.set(user);
    }

    public static User get() {
        return  context.get();
    }

    public static void remove() {
        context.remove();
    }
}
