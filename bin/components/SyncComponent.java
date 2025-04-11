package me.meerkap.rpgmarketplace.bin.components;


import me.meerkap.rpgmarketplace.bin.module.PaginatedModel;

/**
 * Componente para registrar la sincronización global de un modelo.
 */
public class SyncComponent {
    public static void registerGlobalSync(PaginatedModel model) {
        System.out.println("Sincronización global registrada para el modelo " + model.getUniqueId());
        // Se puede agregar lógica adicional para escuchar cambios en el modelo.
    }
}