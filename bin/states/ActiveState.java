package me.meerkap.rpgmarketplace.bin.states;

public class ActiveState implements InventoryState {
    @Override
    public void onOpen() {
        System.out.println("Inventario activo abierto.");
    }

    @Override
    public void onClose() {
        System.out.println("Inventario activo cerrado.");
    }

    @Override
    public void onConfirm() {
        System.out.println("No se puede confirmar en estado activo.");
    }

    @Override
    public void onCancel() {
        System.out.println("No hay acción de cancelación en estado activo.");
    }
}