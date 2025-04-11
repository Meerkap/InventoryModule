package me.meerkap.rpgmarketplace.bin.module;


import me.meerkap.rpgmarketplace.bin.dispatcher.EventDispatcher;
import me.meerkap.rpgmarketplace.bin.dispatcher.events.InventoryUpdateEvent;
import me.meerkap.rpgmarketplace.bin.dispatcher.events.ItemRemovedEvent;
import me.meerkap.rpgmarketplace.bin.exceptions.InvalidSlotException;
import me.meerkap.rpgmarketplace.bin.exceptions.ItemNotFoundException;
import me.meerkap.rpgmarketplace.bin.pagination.PageCalculator;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Representa un inventario paginado.
 */
public class PaginatedModel extends InventoryModel {
    private final int itemsPerPage;
    private final CopyOnWriteArrayList<ItemStack> allItems;
    private final transient Map<Integer, List<ItemStack>> pageCache = new ConcurrentHashMap<>();

    public PaginatedModel(String uniqueId, List<ItemStack> items, int itemsPerPage) {
        super(uniqueId);
        this.allItems = new CopyOnWriteArrayList<>(items);
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * Traduce un slot local y página al índice global, validando los límites.
     */
    public int getGlobalIndex(int page, int localSlot) {
        int startIndex = (page - 1) * itemsPerPage;
        if (page < 1 || localSlot < 0 || localSlot >= itemsPerPage) {
            throw new IllegalArgumentException("Página o slot local inválido.");
        }
        if (startIndex + localSlot >= allItems.size()) {
            throw new InvalidSlotException("Slot local fuera de rango: " + localSlot + ". Rango permitido: 0-" + (itemsPerPage - 1));
        }
        return startIndex + localSlot;
    }

    public List<ItemStack> getPage(int page) {
        return pageCache.computeIfAbsent(page, p ->
                PageCalculator.getPage(allItems, p, itemsPerPage)
        );
    }

    @Override
    public synchronized void updateItem(int globalIndex, Object item) {
        if (globalIndex < 0 || globalIndex >= allItems.size()) return;
        allItems.set(globalIndex, (ItemStack) item);
        // Se elimina de la cache la página afectada
        pageCache.keySet().removeIf(page ->
                globalIndex >= (page - 1) * itemsPerPage && globalIndex < page * itemsPerPage
        );
        EventDispatcher.publish(new InventoryUpdateEvent(this), null);
    }

    /**
     * Marca el ítem como eliminado (set a null) sin reacomodar la lista.
     */
    public synchronized boolean removeItem(int globalIndex) {
        if (globalIndex < 0 || globalIndex >= allItems.size() || allItems.get(globalIndex) == null) {
            return false;
        }
        allItems.set(globalIndex, null);
        pageCache.clear();
        notifyViews();
        EventDispatcher.publish(new ItemRemovedEvent(this, globalIndex), null);
        return true;
    }

    public ItemStack getItem(int globalIndex) {
        if (globalIndex < 0 || globalIndex >= allItems.size() || allItems.get(globalIndex) == null) {
            throw new ItemNotFoundException(globalIndex);
        }
        return allItems.get(globalIndex);
    }

    public String getItemName(int globalIndex) {
        ItemStack item = getItem(globalIndex);
        return (item != null) ? item.getType().name() : "N/A"; // ✅
    }

    public double getPrice(int globalIndex) {
        // Ejemplo: Precio fijo
        return 100.0;
    }
}