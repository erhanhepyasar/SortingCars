package com.erhan;

import com.erhan.domain.Car;
import com.erhan.service.CarService;
import com.erhan.service.CarServiceImpl;

import java.io.IOException;



public class Main {
    private static final int PRODUCTION_QUANTITY = 10000;
    private static CarService carService = new CarServiceImpl();

    public static void main(String[] args) throws IOException {
        final Car[] cars = carService.produceCars(PRODUCTION_QUANTITY);
        carService.exportCarsToFile(cars);
        final Car[] sortedCars = carService.sortCars(cars);
        carService.exportSortedCarsToFile(sortedCars);
        System.exit(0);
    }
}

