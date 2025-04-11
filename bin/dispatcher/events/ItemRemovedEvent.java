package me.meerkap.rpgmarketplace.bin.dispatcher.events;


import me.meerkap.rpgmarketplace.bin.module.Model;

/**
 * Evento que indica que un ítem ha sido eliminado de un modelo.
 */
public class ItemRemovedEvent {
    private final Model sourceModel;
    private final int removedIndex;

    public ItemRemovedEvent(Model sourceModel, int removedIndex) {
        this.sourceModel = sourceModel;
        this.removedIndex = removedIndex;
    }

    public Model getSourceModel() {
        return sourceModel;
    }

    public int getRemovedIndex() {
        return removedIndex;
    }
}