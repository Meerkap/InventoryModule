package me.meerkap.rpgmarketplace.bin.dispatcher.events;


import me.meerkap.rpgmarketplace.bin.module.Model;

/**
 * Evento que se dispara cuando se actualiza un inventario.
 */
public class InventoryUpdateEvent {
    private final Model sourceModel;

    public InventoryUpdateEvent(Model sourceModel) {
        this.sourceModel = sourceModel;
    }

    public Model getSourceModel() {
        return sourceModel;
    }
}