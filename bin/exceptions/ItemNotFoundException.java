package me.meerkap.rpgmarketplace.bin.exceptions;

/**
 * Excepción lanzada cuando se intenta acceder a un ítem que no existe.
 */
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(int globalIndex) {
        super("Ítem no encontrado en índice: " + globalIndex);
    }
}
