package me.meerkap.rpgmarketplace.bin.exceptions;

/**
 * Excepción para indicar que el slot ingresado es inválido.
 */
public class InvalidSlotException extends RuntimeException {
    public InvalidSlotException(String message) {
        super(message);
    }
}