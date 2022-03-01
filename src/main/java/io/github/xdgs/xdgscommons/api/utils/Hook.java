package io.github.xdgs.xdgscommons.api.utils;

public interface Hook {
    void init(Object... args);
    void dispose();
}
