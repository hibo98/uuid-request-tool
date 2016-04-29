package me.hibo98.uuid_request_tool;

public interface Callback<T> {
    public abstract void run(T t);
}
