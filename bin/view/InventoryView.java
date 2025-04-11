package me.meerkap.rpgmarketplace.bin.view;

import me.meerkap.rpgmarketplace.bin.components.HistoryComponent;
import me.meerkap.rpgmarketplace.bin.controller.ViewRegistry;
import me.meerkap.rpgmarketplace.bin.module.InventoryModel;
import me.meerkap.rpgmarketplace.bin.states.InventoryStateContext;

/**
 * Vista principal del inventario (por ejemplo, la tienda).
 */
public class InventoryView<T> extends AbstractInventoryView<InventoryModel> {
    private final String playerName;
    private int currentPage = 1;
    private InventoryStateContext stateContext;
    private HistoryComponent historyComponent;

    public InventoryView(String playerName, InventoryModel model) {
        super(model);
        this.playerName = playerName;
        this.stateContext = new InventoryStateContext();
        this.historyComponent = new HistoryComponent();
        ViewRegistry.registerView(model.getUniqueId(), this);
    }

    @Override
    public void open() {
        System.out.println("Abriendo inventario para " + playerName);
        stateContext.open();
        refresh();
    }

    @Override
    public void close() {
        System.out.println("Cerrando inventario para " + playerName);
        stateContext.close();
        ViewRegistry.unregisterView(model.getUniqueId(), this);
    }

    @Override
    public void refresh() {
        System.out.println("Actualizando vista de " + playerName + " en la página " + currentPage);
        // Aquí se integraría la lógica de renderizado real, por ejemplo, mostrando model.getPage(currentPage).
    }

    /**
     * Maneja acciones, por ejemplo, "comprar", asociadas a un slot local.
     *
     * @param action Nombre de la acción.
     * @param slot   Slot local en la página.
     */
    public void onAction(String action, int slot) {
        if ("siguiente".equals(action)) {
            currentPage++;
            refresh();
        } else if ("anterior".equals(action)) {
            if (currentPage > 1) currentPage--;
            refresh();
        } else if ("comprar".equals(action)) {
            historyComponent.registrarAccion(model, currentPage);
            System.out.println("Iniciando compra, se cerrará esta vista.");
            close();
            // La apertura de la vista de confirmación se delega al controlador.
        } else {
            System.out.println("Acción no reconocida: " + action);
        }
    }

    @Override
    public void onAction(String action) {
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
            System.out.println("Volviendo al inventario anterior para " + playerName);
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
