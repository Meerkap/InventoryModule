package me.meerkap.rpgmarketplace.bin.states;

public interface InventoryState {
    void onOpen();

    void onClose();

    void onConfirm();

    void onCancel();
}
