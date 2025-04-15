package me.meerkap.rpgmarketplace.bin.factory;

import me.meerkap.rpgmarketplace.bin.enums.CustomInventoryType;
import me.meerkap.rpgmarketplace.bin.utils.CustomInventoryHolder;

public class InventoryHolderFactory {

    /**
     * Crea un holder para un inventario paginado.
     *
     * @param inventoryId Identificador único del inventario.
     * @param currentPage Página actual.
     * @return Una instancia de CustomInventoryHolder configurada como inventario paginado.
     */
    public static CustomInventoryHolder createPaginatedHolder(String inventoryId, int currentPage) {
        return new CustomInventoryHolder(inventoryId, currentPage, CustomInventoryType.PAGINATED);
    }

    /**
     * Crea un holder genérico con tipo personalizado.
     *
     * @param inventoryId   Identificador único del inventario.
     * @param currentPage   Página actual.
     * @param inventoryType Tipo de inventario.
     * @return Una instancia de CustomInventoryHolder configurada.
     */
    public static CustomInventoryHolder createHolder(String inventoryId, int currentPage, String inventoryType) {
        return new CustomInventoryHolder(inventoryId, currentPage, CustomInventoryType.NORMAL);
    }
    
}
