package me.meerkap.rpgmarketplace.bin.module;


import me.meerkap.rpgmarketplace.bin.controller.ViewRegistry;
import me.meerkap.rpgmarketplace.bin.view.InventoryView;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Modelo base para inventarios.
 */
public abstract class InventoryModel implements Model {
    private final Set<InventoryView<?>> linkedViews = new CopyOnWriteArraySet<>();
    protected final String uniqueId;

    public InventoryModel(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public abstract void updateItem(int globalIndex, Object item);

    protected void notifyViews() {
        // Utiliza el ViewRegistry para actualizar todas las vistas asociadas
        ViewRegistry.refreshAllViews(uniqueId);
    }

    public void registerView(InventoryView<?> view) {
        linkedViews.add(view);
    }

    public void unregisterView(InventoryView<?> view) {
        linkedViews.remove(view);
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }
}