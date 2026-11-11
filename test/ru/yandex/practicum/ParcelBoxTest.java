package ru.yandex.practicum;

import ru.yandex.practicum.delivery.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParcelBoxTest {

    @Test
    void parcelIsAddedWhenWeightIsWithinLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel parcel = new StandardParcel("Книги", 4, "Москва", 1);

        assertTrue(box.addParcel(parcel));
        assertEquals(1, box.getAllParcels().size());
        assertTrue(box.getAllParcels().contains(parcel));
    }

    @Test
    void parcelIsNotAddedWhenWeightExceedsLimit() {
        ParcelBox<FragileParcel> box = new ParcelBox<>(10);
        box.addParcel(new FragileParcel("Ваза", 7, "Казань", 1));

        assertFalse(box.addParcel(new FragileParcel("Люстра", 5, "Казань", 1)));
        assertEquals(1, box.getAllParcels().size());
    }

    @Test
    void parcelIsAddedWhenTotalWeightEqualsLimit() { // граничный случай
        ParcelBox<PerishableParcel> box = new ParcelBox<>(10);
        box.addParcel(new PerishableParcel("Сыр", 6, "Тверь", 1, 3));

        assertTrue(box.addParcel(new PerishableParcel("Молоко", 4, "Тверь", 1, 2)));
        assertEquals(2, box.getAllParcels().size());
        assertEquals(10, box.getCurrentWeight());
    }
}
