package me.meerkap.rpgmarketplace.bin.controller;

import me.meerkap.rpgmarketplace.bin.module.InventoryModel;
import me.meerkap.rpgmarketplace.bin.view.AbstractInventoryView;
import me.meerkap.rpgmarketplace.bin.view.InventoryView;

/**
 * Controlador para abrir inventarios y gestionar vistas anidadas.
 */
public class InventoryController {

    /**
     * Abre el inventario para un jugador.
     *
     * @param player Nombre o identificador del jugador.
     * @param model  Modelo de inventario.
     * @return Instancia de InventoryView.
     */
    public static InventoryView<T> openInventory(String player, InventoryModel model) {
        InventoryView<T> view = new InventoryView<T>(player, model);
        view.open();
        return view;
    }

    /**
     * Abre una vista anidada (por ejemplo, confirmación) a partir de la vista existente.
     *
     * @param parentView Vista padre.
     * @param nestedView Vista anidada.
     */
    public static void openNestedView(InventoryView<T> parentView, AbstractInventoryView<?> nestedView) {
        parentView.close();
        nestedView.open();
    }
}