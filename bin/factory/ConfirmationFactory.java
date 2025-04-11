package me.meerkap.rpgmarketplace.bin.factory;


import me.meerkap.rpgmarketplace.bin.module.ConfirmationModel;

public class ConfirmationFactory {
    public static ConfirmationModel create(String uniqueId, String message, Runnable onConfirm) {
        return new ConfirmationModel(uniqueId, message, onConfirm);
    }
}
