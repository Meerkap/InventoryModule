package me.meerkap.rpgmarketplace.bin.components;


import me.meerkap.rpgmarketplace.bin.module.InventoryModel;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Componente para administrar el historial de vistas.
 */
public class HistoryComponent {
    private final Deque<HistoryState> history = new ArrayDeque<>();

    public void registrarAccion(InventoryModel model, int page) {
        history.push(new HistoryState(model, page));
    }

    public HistoryState popState() {
        return history.poll();
    }
}