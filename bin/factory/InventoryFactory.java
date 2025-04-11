package me.meerkap.rpgmarketplace.bin.factory;

import me.meerkap.rpgmarketplace.bin.module.PaginatedModel;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryFactory {
    public static PaginatedModel createMarketInventory(String uniqueId, List<ItemStack> items, int itemsPerPage) {
        return new PaginatedModel(uniqueId, items, itemsPerPage);
    }
}