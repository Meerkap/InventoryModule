package me.meerkap.rpgmarketplace.bin.view;

import me.meerkap.rpgmarketplace.bin.components.HistoryComponent;
import me.meerkap.rpgmarketplace.bin.controller.ViewRegistry;
import me.meerkap.rpgmarketplace.bin.enums.InventoryBuilderAction;
import me.meerkap.rpgmarketplace.bin.module.InventoryModel;
import me.meerkap.rpgmarketplace.bin.states.InventoryStateContext;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.logging.Logger;

/**
 * Vista principal del inventario (por ejemplo, la tienda).
 */
public class InventoryView extends AbstractInventoryView<InventoryModel> {
    private final String playerName;
    private int currentPage = 1;
    private final InventoryStateContext stateContext;
    private final HistoryComponent historyComponent;
    private static final Logger LOGGER = Logger.getLogger(InventoryView.class.getName());

    public InventoryView(String playerName, InventoryModel model) {
        super(model);
        this.playerName = playerName;
        this.stateContext = new InventoryStateContext();
        this.historyComponent = new HistoryComponent();
        ViewRegistry.registerView(model.getUniqueId(), this);
    }

    @Override
    public void open() {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            LOGGER.warning("No se encontró al jugador: " + playerName);
            return;
        }
        // Crea un inventario de Bukkit (puedes ajustar el tamaño y el título)
        Inventory bukkitInventory = Bukkit.createInventory(null, 54, "Inventario del Mercado");

        // Rellenamos el inventario con los ítems correspondientes a la página actual
        List<ItemStack> itemsEnPagina = model.getPage(currentPage);
        for (int i = 0; i < itemsEnPagina.size(); i++) {
            bukkitInventory.setItem(i, itemsEnPagina.get(i));
        }

        // Abre el inventario al jugador
        player.openInventory(bukkitInventory);
        LOGGER.info("Abriendo inventario para " + playerName + " en la página " + currentPage);
        stateContext.open();
        refresh();
    }

    @Override
    public void close() {
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null) {
            player.closeInventory();
        }
        stateContext.close();
        ViewRegistry.unregisterView(model.getUniqueId(), this);
    }

    @Override
    public void refresh() {
        LOGGER.info("Actualizando vista de " + playerName + " en la página " + currentPage);
        // Opcional: Actualizar el inventario visible al jugador.
        Player player = Bukkit.getPlayerExact(playerName);
        if (player != null && player.getOpenInventory().getTopInventory() != null) {
            Inventory inv = player.getOpenInventory().getTopInventory();
            // Vaciar el inventario
            inv.clear();
            // Rellenar nuevamente según la página actual
            List<ItemStack> itemsEnPagina = model.getPage(currentPage);
            for (int i = 0; i < itemsEnPagina.size(); i++) {
                inv.setItem(i, itemsEnPagina.get(i));
            }
        }
    }

    /**
     * Maneja acciones, por ejemplo, "comprar", asociadas a un slot local.
     *
     * @param action Nombre de la acción.
     * @param slot   Slot local en la página.
     */
    public void onAction(InventoryBuilderAction action, int slot) {
        switch (action) {

            case NEXT_PAGE:
                currentPage++;
                refresh();
                break;
            case PREV_PAGE:
                if (currentPage > 1) currentPage--;
                refresh();
                break;
            case BUY_ITEM:
                historyComponent.registrarAccion(model, currentPage);
                LOGGER.info("Iniciando compra; se cerrará la vista actual.");
                close();
                // La apertura de la vista de confirmación se delega al controlador.
                break;
            case CANCEL:
                stateContext.cancel();
                close();
                break;
            default:
                LOGGER.warning("Acción no reconocida: " + action);
                break;
        }
    }

    @Override
    public void onAction(InventoryBuilderAction action) {
        // Llamada sin slot, se delega a la versión con slot (por defecto, slot 0).
        onAction(action, 0);
    }

    public void goBack() {
        var previous = historyComponent.popState();
        if (previous != null) {
            ViewRegistry.unregisterView(model.getUniqueId(), this);
            this.model = previous.getModel();
            this.currentPage = previous.getPage();
            refresh();
            ViewRegistry.registerView(model.getUniqueId(), this);
            LOGGER.info("Volviendo al inventario anterior para " + playerName);
            open();
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public String getPlayerName() {
        return playerName;
    }
}
