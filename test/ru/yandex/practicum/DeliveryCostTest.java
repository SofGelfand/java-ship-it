package ru.yandex.practicum;

import ru.yandex.practicum.delivery.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryCostTest {

    @Test
    void standardParcelCostIsWeightTimesTwo() {
        Parcel parcel = new StandardParcel("Книги", 5, "Москва", 1);
        assertEquals(10, parcel.calculateDeliveryCost());
    }

    @Test
    void fragileParcelCostIsWeightTimesFour() {
        Parcel parcel = new FragileParcel("Ваза", 3, "Казань", 1);
        assertEquals(12, parcel.calculateDeliveryCost());
    }

    @Test
    void perishableParcelCostIsWeightTimesThree() {
        Parcel parcel = new PerishableParcel("Пирог", 4, "Тверь", 1, 2);
        assertEquals(12, parcel.calculateDeliveryCost());
    }

    @Test
    void zeroWeightParcelCostsNothing() { // граничный случай
        assertEquals(0, new StandardParcel("Пусто", 0, "Омск", 1).calculateDeliveryCost());
        assertEquals(0, new FragileParcel("Пусто", 0, "Омск", 1).calculateDeliveryCost());
        assertEquals(0, new PerishableParcel("Пусто", 0, "Омск", 1, 1).calculateDeliveryCost());
    }
}