package com.github.donkeyrit.javaapp.panels.filter.model;

import java.util.stream.Stream;

public interface ContentFilter<T> {
    Stream<T> filter(Stream<T> content);
}
