package com.booklnad.bookland.dto.responses;

import lombok.Data;

@Data
public class MinMaxPrises {
    private int minPrise;
    private int maxPrise;

    public MinMaxPrises(int minPrise, int maxPrise) {
        this.minPrise = minPrise;
        this.maxPrise = maxPrise;
    }
}
