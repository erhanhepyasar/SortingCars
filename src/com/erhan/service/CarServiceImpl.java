package com.erhan.service;

import com.erhan.domain.Car;
import com.erhan.domain.CarColor;
import com.erhan.domain.DestinationCity;
import com.erhan.util.Sorter;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CarServiceImpl implements CarService{

    private static final String CAR_FILE = "cars.txt";
    private static final String SORTED_CAR_FILE = "cars_sorted.txt";
    private static final Logger LOGGER = Logger.getLogger("Logger");

    @Override
    public Car[] produceCars(final int quantity) {
        final Car[] cars = new Car[quantity];
        for (int i = 0; i < quantity; i++) {
            cars[i] = new Car(
                            genProductionOrder(i),
                            genSerialNo(),
                            genCarColor(),
                            genDestinationCity()
                    );
        }
        LOGGER.log(Level.INFO, "======================== STEP-1 ======= Cars has been produced.");
        return cars;
    }

    private int genProductionOrder(final int i) {
        return i + 1;
    }

    private long genSerialNo() {
        final long leftLimit =  100000000000L;
        final long rightLimit = 999999999999L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    private CarColor genCarColor() {
        return CarColor.getRandom();
    }

    private DestinationCity genDestinationCity() {
        return DestinationCity.getRandom();
    }

    @Override
    public void exportCarsToFile(final Car[] cars) throws IOException {
        final PrintWriter pw = new PrintWriter(new FileWriter(CAR_FILE));
        for (Car car : cars) {
            pw.println(car.getProductionOrder() + ":" +
                    car.getSerialNo() + ":" +
                    car.getCarColor() + ":" +
                    car.getDestinationCity()
            );
        }
        pw.close();
        LOGGER.log(Level.INFO, "======================== STEP-2 ======= Generated cars has been exported to the file: \"{0}\"", CAR_FILE);
    }

    @Override
    public void exportSortedCarsToFile(Car[] sortedCars) throws IOException {
        final PrintWriter pw = new PrintWriter(new FileWriter(SORTED_CAR_FILE));
        for (Car sortedCar : sortedCars) {
            pw.println(sortedCar.getDestinationCity() + ":" +
                    sortedCar.getCarColor() + ":" +
                    sortedCar.getSerialNo() + ":" +
                    sortedCar.getProductionOrder()
            );
        }
        pw.close();
        LOGGER.log(Level.INFO, "======================== STEP-5 ======= Sorted cars has been exported to the file: \"{0}\"", SORTED_CAR_FILE);
    }

    @Override
    public Car[] sortCars(final Car[] cars) {
        try {
            Sorter.quicksort(cars);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Sorting cars has been interrupted :", e);
            Thread.currentThread().interrupt();
        }
        return cars;
    }

}
