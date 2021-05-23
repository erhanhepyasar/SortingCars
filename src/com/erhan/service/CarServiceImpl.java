package com.erhan.service;

import com.erhan.domain.Car;
import com.erhan.domain.CarColor;
import com.erhan.domain.DestinationCity;
import com.erhan.util.QuickSort;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;


public class CarServiceImpl implements CarService{

    private static final String CAR_FILE = "cars.txt";
    private static final String SORTED_CAR_FILE = "cars_sorted.txt";

    Logger logger = Logger.getLogger("Logger");

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
        return cars;
    }

    private int genProductionOrder(final int i) {
        return i + 1;
    }

    private long genSerialNo() {
        long leftLimit =  100000000000L;
        long rightLimit = 999999999999L;
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
        PrintWriter pw = new PrintWriter(new FileWriter(CAR_FILE));
        for (Car car : cars) {
            pw.println(car.getProductionOrder() + ":" +
                    car.getSerialNo() + ":" +
                    car.getCarColor() + ":" +
                    car.getDestinationCity()
            );

        }
        pw.close();
    }

    @Override
    public void exportSortedCarsToFile(Car[] sortedCars) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(SORTED_CAR_FILE));
        for (Car sortedCar : sortedCars) {
            pw.println(sortedCar.getDestinationCity() + ":" +
                    sortedCar.getCarColor() + ":" +
                    sortedCar.getSerialNo() + ":" +
                    sortedCar.getProductionOrder()
            );

        }
        pw.close();
    }



    @Override
    public Car[] sortCars(final Car[] cars) {
        final long start = System.currentTimeMillis();
        quickSort(cars);
        logger.info("Cars sorted in " + (System.currentTimeMillis() - start) + " ms");
        return cars;
    }

    private void quickSort(final Car[] cars) {
        QuickSort<Car> quickSort = new QuickSort<>(cars);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(quickSort);

    }

    /*
        An alternate way of sorting with threads
     */
    private void parallelSort(final Car[] cars) {
        Arrays.parallelSort(cars);
    }

}
