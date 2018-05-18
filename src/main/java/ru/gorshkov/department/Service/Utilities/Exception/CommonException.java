package ru.gorshkov.department.Service.Utilities.Exception;

public class CommonException extends Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public CommonException(String message) {
        this.message = message;
    }
}
