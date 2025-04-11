package me.meerkap.rpgmarketplace.bin.view;


/**
 * Vista base utilizando genéricos para trabajar con el modelo específico.
 */
public abstract class AbstractInventoryView<T> {
    protected T model;

    public AbstractInventoryView(T model) {
        this.model = model;
    }

    public abstract void open();

    public abstract void close();

    public abstract void refresh();

    public abstract void onAction(String action);
}
