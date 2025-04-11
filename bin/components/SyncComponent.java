package me.meerkap.rpgmarketplace.bin.components;


import me.meerkap.rpgmarketplace.bin.controller.ViewRegistry;
import me.meerkap.rpgmarketplace.bin.dispatcher.EventDispatcher;
import me.meerkap.rpgmarketplace.bin.dispatcher.events.InventoryUpdateEvent;
import me.meerkap.rpgmarketplace.bin.module.PaginatedModel;

/**
 * Componente para registrar la sincronización global de un modelo.
 */
public class SyncComponent {
    public static void registerGlobalSync(PaginatedModel model) {
        System.out.println("Sincronización global registrada para el modelo " + model.getUniqueId());
        EventDispatcher.subscribe(InventoryUpdateEvent.class, (event, view) -> {
            if (event.getSourceModel().equals(model)) {
                ViewRegistry.refreshAllViews(model.getUniqueId());
            }
        });
    }
}