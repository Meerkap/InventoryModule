package me.meerkap.rpgmarketplace.bin.states;

public class InventoryStateContext {
    private InventoryState currentState;

    public InventoryStateContext() {
        this.currentState = new ActiveState();
    }

    public void setState(InventoryState state) {
        this.currentState = state;
    }

    public void open() {
        currentState.onOpen();
    }

    public void close() {
        currentState.onClose();
    }

    public void confirm() {
        currentState.onConfirm();
    }

    public void cancel() {
        currentState.onCancel();
    }
}
