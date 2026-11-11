package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final List<T> parcels = new ArrayList<>();
    private final int maxWeight;
    private int currentWeight = 0;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        if (currentWeight + parcel.getWeight() > maxWeight) {
            System.out.println("Внимание: посылка <<" + parcel.getDescription()
                    + ">> не помещается в коробку, превышен максимальный вес (" + maxWeight + ").");
            return false;
        }
        parcels.add(parcel);
        currentWeight += parcel.getWeight();
        return true;
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels); // копия, чтобы снаружи нельзя было сломать подсчёт веса
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }
}