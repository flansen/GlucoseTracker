package de.flansen.glucosetracker.common.model;

/**
 * Created by Florian on 17.10.2016.
 */

public class NoSuchDiabetesDataException extends RuntimeException {
    public NoSuchDiabetesDataException(DiabetesDataType type) {
        super("The entry does not have DiabetesData of the type " + type);
    }
}
