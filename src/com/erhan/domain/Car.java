package com.erhan.domain;

import java.util.Comparator;
import java.util.Objects;

public class Car implements Comparable<Car>{
    private int productionOrder;
    private long serialNo;
    private CarColor carColor;
    private DestinationCity destinationCity;

    public Car(int productionOrder, long serialNo, CarColor carColor, DestinationCity destinationCity) {
        this.productionOrder = productionOrder;
        this.serialNo = serialNo;
        this.carColor = carColor;
        this.destinationCity = destinationCity;
    }

    public int getProductionOrder() {
        return productionOrder;
    }

    public long getSerialNo() {
        return serialNo;
    }

    public CarColor getCarColor() {
        return carColor;
    }

    public DestinationCity getDestinationCity() {
        return destinationCity;
    }

    @Override
    public String toString() {
        return "Car{" +
                "productionOrder=" + productionOrder +
                ", serialNo=" + serialNo +
                ", carColor=" + carColor +
                ", destinationCity=" + destinationCity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return productionOrder == car.productionOrder && serialNo == car.serialNo && carColor == car.carColor && destinationCity == car.destinationCity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productionOrder, serialNo, carColor, destinationCity);
    }

    /*
        Sorting Order:
        City (Los Angeles, Houston, Miami, New York)
        Color (Red, Blue, Black, White, Green)
        Serial No - Descending
     */
    @Override
    public int compareTo(Car car) {
        return Comparator.comparing(Car::getDestinationCity)
                .thenComparing(Car::getCarColor)
                .thenComparing(Car::getSerialNo, Comparator.reverseOrder())
                .compare(this, car);
    }
}
