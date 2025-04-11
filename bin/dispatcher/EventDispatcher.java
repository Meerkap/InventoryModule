package me.meerkap.rpgmarketplace.bin.dispatcher;

import me.meerkap.rpgmarketplace.bin.view.InventoryView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;

/**
 * Permite suscribirse y publicar eventos.
 */
public class EventDispatcher {
    private static final Map<Class<?>, List<BiConsumer<Object, InventoryView>>> listeners = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <E> void subscribe(Class<E> eventType, BiConsumer<E, InventoryView> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                .add((BiConsumer<Object, InventoryView>) listener);
    }

    public static <E> void publish(E event, InventoryView source) {
        List<BiConsumer<Object, InventoryView>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            eventListeners.forEach(listener -> listener.accept(event, source));
        }
    }
}