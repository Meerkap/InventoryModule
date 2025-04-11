package me.meerkap.rpgmarketplace.bin.pagination;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Calcula la página a mostrar filtrando ítems eliminados.
 */
public class PageCalculator {
    public static List<ItemStack> getPage(List<ItemStack> allItems, int page, int itemsPerPage) {
        List<ItemStack> itemsActivos = allItems.stream().filter(item -> item != null).collect(Collectors.toList());
        int inicio = (page - 1) * itemsPerPage;
        int fin = Math.min(inicio + itemsPerPage, itemsActivos.size());
        if (inicio >= itemsActivos.size()) {
            return List.of();
        }
        return itemsActivos.subList(inicio, fin);
    }
}