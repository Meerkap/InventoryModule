package me.meerkap.rpgmarketplace.bin;

import me.meerkap.rpgmarketplace.bin.controller.InventoryController;
import me.meerkap.rpgmarketplace.bin.controller.InventoryManager;
import me.meerkap.rpgmarketplace.bin.enums.InventoryBuilderAction;
import me.meerkap.rpgmarketplace.bin.factory.ConfirmationFactory;
import me.meerkap.rpgmarketplace.bin.factory.InventoryFactory;
import me.meerkap.rpgmarketplace.bin.module.ConfirmationModel;
import me.meerkap.rpgmarketplace.bin.module.PaginatedModel;
import me.meerkap.rpgmarketplace.bin.view.ConfirmationView;
import me.meerkap.rpgmarketplace.bin.view.InventoryView;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Ejemplo de uso: Simula la apertura de la tienda, la compra de un ítem y la confirmación.
 */
public class MarketAppTestReady {
    public static void main(String[] args) {
        // Lista de ítems en la tienda (por ejemplo, nombres de ítems)
        ItemStack item1 = new ItemStack(Material.STONE);
        ItemStack item2 = new ItemStack(Material.DIAMOND);
        ItemStack item3 = new ItemStack(Material.IRON_INGOT);
        ItemStack item4 = new ItemStack(Material.GOLD_INGOT);
        ItemStack item5 = new ItemStack(Material.EMERALD);
        List<ItemStack> items = Arrays.asList(item1, item2, item3, item4, item5);

        // Creación y registro del modelo paginado de la tienda
        PaginatedModel shopModel = InventoryFactory.createMarketInventory("tienda_principal", items, 36);
        InventoryManager.getInstance().registerModel(shopModel);

        // Jugador1 abre la tienda
        InventoryView playerView = InventoryController.openInventory("Jugador1", shopModel);

        // Simulación: Jugador1 pulsa "comprar" sobre el slot local 1 (por ejemplo, "Escudo")
        int page = playerView.getCurrentPage();
        int localSlot = 1;
        int globalIndex = shopModel.getGlobalIndex(page, localSlot);
        System.out.println("\nJugador1 ha pulsado 'comprar' sobre el slot local " + localSlot);
        playerView.onAction(InventoryBuilderAction.BUY_ITEM, localSlot);

        // Se crea un modelo de confirmación que, al confirmar, elimina el ítem
        Runnable onConfirm = () -> {
            if (shopModel.removeItem(globalIndex)) {
                System.out.println("Compra confirmada: El ítem se ha eliminado de la tienda.");
                // Aquí se puede agregar lógica adicional, por ejemplo, añadir el ítem al inventario del jugador.
            } else {
                System.out.println("Error: El ítem ya no está disponible.");
            }
        };
        ConfirmationModel confModel = ConfirmationFactory.create(
                "confirmar_compra_" + globalIndex,
                "¿Comprar " + shopModel.getItemName(globalIndex) + " por $" + shopModel.getPrice(globalIndex) + "?",
                onConfirm
        );
        ConfirmationView confirmView = new ConfirmationView("Jugador1", confModel);

        // Se abre la vista de confirmación (vista anidada)
        InventoryController.openNestedView(playerView, confirmView);

        // Simulación: Jugador1 confirma la compra
        confirmView.onAction(InventoryBuilderAction.CONFIRM);

        // Se vuelve a la vista anterior del mercado
        playerView.goBack();
    }
}