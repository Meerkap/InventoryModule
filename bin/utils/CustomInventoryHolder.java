package me.meerkap.rpgmarketplace.bin.utils;

import me.meerkap.rpgmarketplace.bin.enums.CustomInventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class CustomInventoryHolder implements InventoryHolder {

    private final String inventoryId;
    private final int currentPage;
    private final CustomInventoryType inventoryType;

    public CustomInventoryHolder(String inventoryId, int currentPage, CustomInventoryType inventoryType) {
        this.inventoryId = inventoryId;
        this.currentPage = currentPage;
        this.inventoryType = inventoryType;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public CustomInventoryType getInventoryType() {
        return inventoryType;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }

}
