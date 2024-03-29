package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.Identifiable;

public record CarBodyType(Long id, String type) implements Identifiable {
    
    @Override
    public Long getId() {
        return id;
    }
}