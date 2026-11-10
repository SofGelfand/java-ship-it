package ru.yandex.practicum;

import ru.yandex.practicum.delivery.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PerishableParcelTest {

    @Test
    void notExpiredWithinTimeToLive() {
        PerishableParcel parcel = new PerishableParcel("Пирог", 2, "Тула", 10, 5);
        assertFalse(parcel.isExpired(12));
    }

    @Test
    void expiredAfterTimeToLive() {
        PerishableParcel parcel = new PerishableParcel("Пирог", 2, "Тула", 10, 5);
        assertTrue(parcel.isExpired(20));
    }

    @Test
    void notExpiredOnLastAllowedDay() { // граничный: sendDay + timeToLive == currentDay
        PerishableParcel parcel = new PerishableParcel("Пирог", 2, "Тула", 10, 5);
        assertFalse(parcel.isExpired(15));
    }

    @Test
    void expiredOnDayAfterLastAllowed() { // граница с другой стороны
        PerishableParcel parcel = new PerishableParcel("Пирог", 2, "Тула", 10, 5);
        assertTrue(parcel.isExpired(16));
    }
}
