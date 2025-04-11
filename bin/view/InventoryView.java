package me.meerkap.rpgmarketplace.bin.view;

import me.meerkap.rpgmarketplace.bin.components.HistoryComponent;
import me.meerkap.rpgmarketplace.bin.controller.ViewRegistry;
import me.meerkap.rpgmarketplace.bin.enums.InventoryBuilderAction;
import me.meerkap.rpgmarketplace.bin.module.InventoryModel;
import me.meerkap.rpgmarketplace.bin.states.InventoryStateContext;

import java.util.logging.Logger;

/**
 * Vista principal del inventario (por ejemplo, la tienda).
 */
public class InventoryView<T> extends AbstractInventoryView<InventoryModel> {
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
        LOGGER.info("Abriendo inventario para " + playerName);
        stateContext.open();
        refresh();
    }

    @Override
    public void close() {
        LOGGER.info("Cerrando inventario para " + playerName);
        stateContext.close();
        ViewRegistry.unregisterView(model.getUniqueId(), this);
    }

    @Override
    public void refresh() {
        LOGGER.info("Actualizando vista de " + playerName + " en la página " + currentPage);
        // Aquí se integraría la lógica de renderizado real, por ejemplo, mostrando model.getPage(currentPage).
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
