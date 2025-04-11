package me.meerkap.rpgmarketplace.bin.states;

public class ConfirmationState implements InventoryState {
    @Override
    public void onOpen() {
        System.out.println("Vista de confirmación abierta.");
    }

    @Override
    public void onClose() {
        System.out.println("Vista de confirmación cerrada.");
    }

    @Override
    public void onConfirm() {
        System.out.println("Confirmación procesada.");
    }

    @Override
    public void onCancel() {
        System.out.println("Confirmación cancelada.");
    }
}