package io.subs.data.helper;

/**
 * @author Ritesh Shakya
 */

public class RxDto<T> {
    private T data;

    public RxDto(T data) {
        super();
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
