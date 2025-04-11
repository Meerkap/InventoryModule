package me.meerkap.rpgmarketplace.bin.controller;

import me.meerkap.rpgmarketplace.bin.view.AbstractInventoryView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maneja el stack de vistas anidadas de cada jugador.
 */
public class NestedViewManager {
    private static NestedViewManager instance;
    private final Map<UUID, Deque<AbstractInventoryView<?>>> playerViewStack = new ConcurrentHashMap<>();

    private NestedViewManager() {
    }

    public static synchronized NestedViewManager getInstance() {
        if (instance == null) {
            instance = new NestedViewManager();
        }
        return instance;
    }

    public void pushView(UUID playerId, AbstractInventoryView<?> view) {
        playerViewStack.computeIfAbsent(playerId, k -> new ArrayDeque<>()).push(view);
    }

    public AbstractInventoryView<?> popView(UUID playerId) {
        Deque<AbstractInventoryView<?>> stack = playerViewStack.get(playerId);
        if (stack == null || stack.isEmpty()) {
            throw new IllegalStateException("No hay vistas para restaurar.");
        }
        AbstractInventoryView<?> oldView = stack.pop();
        oldView.close();
        return stack.isEmpty() ? null : stack.peek();
    }
}