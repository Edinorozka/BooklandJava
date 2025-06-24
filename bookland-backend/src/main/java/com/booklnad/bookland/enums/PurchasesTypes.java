package com.booklnad.bookland.enums;

public enum PurchasesTypes {
    ShoppingCart("Корзина"),
    paid("Оплачен"),
    collecting("Собираем"),
    delivering("Доставляем"),
    awaiting("Ожидает"),
    received("Получен"),
    cancelled("Отменен");

    private final String value;

    public String getValue() {
        return value;
    }

    PurchasesTypes(String value) {
        this.value = value;
    }

    public static PurchasesTypes fromValue(String value) {
        for (PurchasesTypes type : PurchasesTypes.values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with value: " + value);
    }
}
