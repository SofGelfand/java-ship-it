package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final List<Trackable> trackableParcels = new ArrayList<>();

    private static final int BOX_MAX_WEIGHT = 50;
    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(BOX_MAX_WEIGHT);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(BOX_MAX_WEIGHT);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(BOX_MAX_WEIGHT);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    reportTrackingStatus();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить местоположение отслеживаемых посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        if (type < 1 || type > 3) {
            System.out.println("Неверный тип посылки.");
            return;
        }

        System.out.print("Описание: ");
        String description = scanner.nextLine();
        System.out.print("Вес: ");
        int weight = Integer.parseInt(scanner.nextLine());
        System.out.print("Адрес доставки: ");
        String address = scanner.nextLine();
        System.out.print("День отправки: ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1: {
                StandardParcel parcel = new StandardParcel(description, weight, address, sendDay);
                allParcels.add(parcel);
                standardBox.addParcel(parcel);
                break;
            }
            case 2: {
                FragileParcel parcel = new FragileParcel(description, weight, address, sendDay);
                allParcels.add(parcel);
                trackableParcels.add(parcel);
                fragileBox.addParcel(parcel);
                break;
            }
            case 3: {
                System.out.print("Срок хранения (дней): ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                PerishableParcel parcel = new PerishableParcel(description, weight, address, sendDay, timeToLive);
                allParcels.add(parcel);
                perishableBox.addParcel(parcel);
                break;
            }
        }
        System.out.println("Посылка добавлена.");
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int total = 0;
        for (Parcel parcel : allParcels) {
            total += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставки: " + total);
    }

    private static void reportTrackingStatus() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет посылок с трекингом.");
            return;
        }
        System.out.print("Введите новое местоположение: ");
        String newLocation = scanner.nextLine();
        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(newLocation);
        }
    }

    private static void showBoxContents() {
        System.out.println("Выберите коробку:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");
        int type = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1:
                printBox(standardBox);
                break;
            case 2:
                printBox(fragileBox);
                break;
            case 3:
                printBox(perishableBox);
                break;
            default:
                System.out.println("Неверный тип коробки.");
        }
    }

    private static void printBox(ParcelBox<? extends Parcel> box) {
        List<? extends Parcel> parcels = box.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }
        System.out.println("Содержимое коробки (" + box.getCurrentWeight() + "/" + box.getMaxWeight() + "):");
        for (Parcel parcel : parcels) {
            System.out.println("- " + parcel.getDescription());
        }
    }
}
