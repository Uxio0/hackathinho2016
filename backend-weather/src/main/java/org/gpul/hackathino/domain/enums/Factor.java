package org.gpul.hackathino.domain.enums;

/**
 * Created by david on 22/10/16.
 */
public enum Factor {
    HIDRAULIC(0.1), EOLIC(0.2), SOLAR(0.3);

    private Double value;

    Factor(Double value) {
        this.value = value;
    }

    public Double getValue(){
        return this.value;
    }

}
