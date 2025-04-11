package me.meerkap.rpgmarketplace.bin.module;

/**
 * Modelo para confirmaciones. Usa composición para separar la funcionalidad.
 */
public class ConfirmationModel implements Model {
    private final String uniqueId;
    private final Runnable onConfirm;
    private final String message;

    public ConfirmationModel(String uniqueId, String message, Runnable onConfirm) {
        this.uniqueId = uniqueId;
        this.message = message;
        this.onConfirm = onConfirm;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    public Runnable getOnConfirm() {
        return onConfirm;
    }

    public String getMessage() {
        return message;
    }
}
