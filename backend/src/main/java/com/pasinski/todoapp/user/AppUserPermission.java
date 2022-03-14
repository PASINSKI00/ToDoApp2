package com.pasinski.todoapp.user;

public enum AppUserPermission {
    TASK_READ("task:read"),
    TASK_WRITE("task:write"),
    CATEGORY_READ("category:read"),
    CATEGORY_WRITE("category:write");

    private final String permission;

    public String getPermission() {
        return permission;
    }

    AppUserPermission(String permission) {
        this.permission = permission;
    }
}
