package me.meerkap.rpgmarketplace.bin.components;


import me.meerkap.rpgmarketplace.bin.module.InventoryModel;

/**
 * Componente para guardar el historial de vistas.
 */
public class HistoryState {
    private final InventoryModel model;
    private final int page;

    public HistoryState(InventoryModel model, int page) {
        this.model = model;
        this.page = page;
    }

    public InventoryModel getModel() {
        return model;
    }

    public int getPage() {
        return page;
    }
}
