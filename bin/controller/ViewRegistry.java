package me.meerkap.rpgmarketplace.bin.controller;

import me.meerkap.rpgmarketplace.bin.view.AbstractInventoryView;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registro centralizado de vistas, mapeando un modelo (por su ID) a un conjunto de vistas.
 */
public class ViewRegistry {
    private static final Map<String, Set<AbstractInventoryView<?>>> modelToViews = new ConcurrentHashMap<>();

    public static void registerView(String modelId, AbstractInventoryView<?> view) {
        modelToViews.computeIfAbsent(modelId, k -> ConcurrentHashMap.newKeySet()).add(view);
    }

    public static void unregisterView(String modelId, AbstractInventoryView<?> view) {
        Set<AbstractInventoryView<?>> views = modelToViews.get(modelId);
        if (views != null) {
            views.remove(view);
        }
    }

    public static void refreshAllViews(String modelId) {
        Set<AbstractInventoryView<?>> views = modelToViews.get(modelId);
        if (views != null) {
            views.forEach(AbstractInventoryView::refresh);
        }
    }
}
