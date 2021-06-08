package de.thbingenproj.backend.controller.costs.models;

import lombok.Data;

/**
 * @param <T> Generic Pair Type1
 * @param <U> Generic Pair Type2
 */
@Data
public class Pair<T, U> {
    private T elm1;
    private U elm2;

    public Pair(T elm1, U elm2) {
        this.elm1 = elm1;
        this.elm2 = elm2;
    }
}
