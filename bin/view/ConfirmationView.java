package me.meerkap.rpgmarketplace.bin.view;

import me.meerkap.rpgmarketplace.bin.controller.ViewRegistry;
import me.meerkap.rpgmarketplace.bin.enums.InventoryBuilderAction;
import me.meerkap.rpgmarketplace.bin.module.ConfirmationModel;
import me.meerkap.rpgmarketplace.bin.states.ConfirmationState;
import me.meerkap.rpgmarketplace.bin.states.InventoryStateContext;
import me.meerkap.rpgmarketplace.bin.utils.SchedulerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Vista para confirmación de acción, con timeout.
 */
public class ConfirmationView extends AbstractInventoryView<ConfirmationModel> {
    private final String playerName;
    private InventoryStateContext stateContext;
    private ScheduledExecutorService scheduler = SchedulerFactory.getScheduler();
    private ScheduledFuture<?> timeoutTask;
    private boolean open = false;
    private static final Logger LOGGER = Logger.getLogger(ConfirmationView.class.getName());

    public ConfirmationView(String playerName, ConfirmationModel model) {
        super(model);
        this.playerName = playerName;
        this.stateContext = new InventoryStateContext();
        stateContext.setState(new ConfirmationState());
        ViewRegistry.registerView(model.getUniqueId(), this);
    }

    @Override
    public void open() {
        if (scheduler.isShutdown()) {
            scheduler = SchedulerFactory.getScheduler();
        }

        open = true;
        LOGGER.info("Abriendo vista de confirmación para " + playerName);
        stateContext.open();
        refresh();
        timeoutTask = scheduler.schedule(() -> {
            if (open) {
                LOGGER.info("Confirmación cancelada por timeout.");
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
        LOGGER.info("Confirmar: " + model.getMessage());
    }

    @Override
    public void onAction(InventoryBuilderAction action) {
        switch (action) {
            case CONFIRM:
                stateContext.confirm();
                model.getOnConfirm().run();
                close();
                break;
            case CANCEL:
                stateContext.cancel();
                close();
                break;
            default:
                System.out.println("Acción no reconocida en confirmación: " + action);
                break;
        }
    }

    public boolean isOpen() {
        return open;
    }
}

