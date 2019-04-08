package com.codingexercise.flickr.model;

public class ResponseDataWrapper<T> {
    private Throwable throwable;
    private T response;

    public ResponseDataWrapper(Throwable throwable) {
        this.throwable = throwable;
    }

    public ResponseDataWrapper(T response) {
        this.response = response;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
