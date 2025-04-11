package me.meerkap.rpgmarketplace.bin.components;


import me.meerkap.rpgmarketplace.bin.controller.ViewRegistry;
import me.meerkap.rpgmarketplace.bin.dispatcher.EventDispatcher;
import me.meerkap.rpgmarketplace.bin.dispatcher.events.InventoryUpdateEvent;
import me.meerkap.rpgmarketplace.bin.dispatcher.events.ItemRemovedEvent;
import me.meerkap.rpgmarketplace.bin.module.PaginatedModel;

/**
 * Componente para registrar la sincronización global de un modelo.
 */
public class SyncComponent {
    public static void registerGlobalSync(PaginatedModel model) {
        // Suscripción para actualizaciones generales del inventario.
        EventDispatcher.subscribe(InventoryUpdateEvent.class, (event, view) -> {
            ViewRegistry.refreshAllViews(model.getUniqueId());
        });

        // Suscripción para la eliminación de ítems en el inventario.
        EventDispatcher.subscribe(ItemRemovedEvent.class, (event, view) -> {
            ViewRegistry.refreshAllViews(model.getUniqueId());
        });

        System.out.println("Sincronización global registrada para el modelo " + model.getUniqueId());
    }
}