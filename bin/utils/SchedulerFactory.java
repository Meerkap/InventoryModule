package me.meerkap.rpgmarketplace.bin.utils;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Utilidad para obtener un ScheduledExecutorService.
 */
public class SchedulerFactory {
    public static ScheduledExecutorService getScheduler() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}