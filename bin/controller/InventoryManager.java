package me.meerkap.rpgmarketplace.bin.controller;

import me.meerkap.rpgmarketplace.bin.components.SyncComponent;
import me.meerkap.rpgmarketplace.bin.module.InventoryModel;
import me.meerkap.rpgmarketplace.bin.module.PaginatedModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Administrador global de inventarios.
 */
public class InventoryManager {
    private static InventoryManager instance;
    private final Map<String, InventoryModel> models = new ConcurrentHashMap<>();

    private InventoryManager() {
    }

    public static synchronized InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void registerModel(InventoryModel model) {
        models.put(model.getUniqueId(), model);
        SyncComponent.registerGlobalSync((PaginatedModel) model);
    }

    public InventoryModel getModel(String uniqueId) {
        return models.get(uniqueId);
    }

    // Para facilitar el testeo, permite inyectar un mock.
    public static void setInstance(InventoryManager mock) {
        instance = mock;
    }
}