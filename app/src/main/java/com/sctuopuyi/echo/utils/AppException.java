package com.sctuopuyi.echo.utils;

import org.jetbrains.annotations.NotNull;

public class AppException extends Exception {

    public AppException(@NotNull String message) {
        super(message);
    }
}