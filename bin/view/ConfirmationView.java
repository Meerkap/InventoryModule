package me.meerkap.rpgmarketplace.bin.view;

import me.meerkap.rpgmarketplace.bin.controller.ViewRegistry;
import me.meerkap.rpgmarketplace.bin.module.ConfirmationModel;
import me.meerkap.rpgmarketplace.bin.states.ConfirmationState;
import me.meerkap.rpgmarketplace.bin.states.InventoryStateContext;
import me.meerkap.rpgmarketplace.bin.utils.SchedulerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Vista para confirmación de acción, con timeout.
 */
public class ConfirmationView extends AbstractInventoryView<ConfirmationModel> {
    private final String playerName;
    private InventoryStateContext stateContext;
    private final ScheduledExecutorService scheduler = SchedulerFactory.getScheduler();
    private ScheduledFuture<?> timeoutTask;
    private boolean open = false;

    public ConfirmationView(String playerName, ConfirmationModel model) {
        super(model);
        this.playerName = playerName;
        this.stateContext = new InventoryStateContext();
        stateContext.setState(new ConfirmationState());
        ViewRegistry.registerView(model.getUniqueId(), this);
    }

    @Override
    public void open() {
        open = true;
        System.out.println("Abriendo vista de confirmación para " + playerName);
        stateContext.open();
        refresh();
        timeoutTask = scheduler.schedule(() -> {
            if (open) {
                System.out.println("Confirmación cancelada por timeout.");
                close();
            }
        }, 30, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        open = false;
        if (timeoutTask != null) {
            timeoutTask.cancel(true);
        }
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
        stateContext.close();
        ViewRegistry.unregisterView(model.getUniqueId(), this);
    }

    @Override
    public void refresh() {
        System.out.println("Confirmar: " + model.getMessage());
    }

    @Override
    public void onAction(String action) {
        if ("confirmar".equals(action)) {
            stateContext.confirm();
            model.getOnConfirm().run();
            close();
        } else if ("cancelar".equals(action)) {
            stateContext.cancel();
            close();
        } else {
            System.out.println("Acción no reconocida en confirmación: " + action);
        }
    }

    public boolean isOpen() {
        return open;
    }
}

