package org.comics.stock.model;

public enum UpdateType {
    SALE,   // sold to customer
    RESTOCK, // add more to inventory
    RETURN, // customer returned
    CORRECTION, // fix mistake/inaccuracy in listing
    PRICE_DROP, // decrease price
    PRICE_INCREASE, // increase price
    DAMAGE, // damage to product
    THEFT, // item stolen
    ITEM_LOST, // item lost
    ADD_NOTE // update itemNotes
}
