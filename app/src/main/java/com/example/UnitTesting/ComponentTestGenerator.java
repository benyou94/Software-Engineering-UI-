package com.example.UnitTesting;

import com.example.Data.Component;
import com.example.Data.MedProduct;

import java.util.ArrayList;

public class ComponentTestGenerator {
    private MedProduct alcBottle;
    public ComponentTestGenerator() { }

    public ArrayList<Component> getTestProducts() {
        ArrayList<Component> testSamples = new ArrayList<>();
        testSamples.add(alcBottle);
        return testSamples;
    }

    //public MedProduct(String sku, String name, String supplier, String orderID, String orderDate) {
    private void createProductsAndSubComponents() {
        alcBottle = new MedProduct("Alc123", "32 Fl Oz Bottle of Alcohol", "CVS",
                "order123", "05/07/2019");

        Component alcohol100 = new Component("alc100", "100% Alcohol", "ChemicalsRUS");
        Component distWater = new Component("distwater20", "Distilled Water", "Dasani");
        ArrayList<Component> alc70Comp = new ArrayList<>();
        alc70Comp.add(alcohol100);
        alc70Comp.add(distWater);
        Component alcohol70 = new Component("alc70", "70% Alcohol", "ChemicalsRUS", alc70Comp);

        Component cap = new Component("cap222", "Bottle Cap", "Plastico");
        Component bottle = new Component("bottle123", "32 Fl Oz Bottle", "Plastico");
        ArrayList<Component> bottleconfig = new ArrayList<>();
        bottleconfig.add(bottle);
        bottleconfig.add(cap);
        Component bottleSet = new Component("bottleconfig1", "32 Fl Oz Bottle Set", "Plastico", bottleconfig);

        Component transparentResin = new Component("resin123", "Transparent Clear Resin", "CheapResins");
        Component whiteResin = new Component("resin123", "Opaque White Resin", "CheapResins");

        ArrayList<Component> capComponents = new ArrayList<>();
        capComponents.add(whiteResin);
        ArrayList<Component> bottleComponents = new ArrayList<>();
        bottleComponents.add(transparentResin);

        cap.setComponents(capComponents);
        bottle.setComponents(bottleComponents);

        ArrayList<Component> alcBottleComponents = new ArrayList<>();
        alcBottleComponents.add(bottleSet);
        alcBottleComponents.add(alcohol70);

        alcBottle.setComponents(alcBottleComponents);
    }
}
