package com.erhan.service;

import com.erhan.domain.Car;
import java.io.IOException;


public interface CarService {
    Car[] produceCars(final int quantity);
    void exportCarsToFile(final Car[] cars) throws IOException;
    void exportSortedCarsToFile(final Car[] sortedCars) throws IOException;
    Car[] sortCars(final Car[] cars);
}
