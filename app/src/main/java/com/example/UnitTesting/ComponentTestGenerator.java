package com.example.UnitTesting;

import com.example.Data.Component;
import com.example.Data.MedProduct;

import java.util.ArrayList;

public class ComponentTestGenerator {
    private MedProduct alcBottle;
    private MedProduct ben;
    public ComponentTestGenerator() { }


    public void main(String[] args) {
        ComponentTestGenerator test = new ComponentTestGenerator();
        ArrayList<Component> testSamples = test.getTestProducts();
    }
    public ArrayList<Component> getTestProducts() {
        ArrayList<Component> testSamples = new ArrayList<>();
        testSamples.add(alcBottle);
        return testSamples;
    }

    //public MedProduct(String sku, String name, String supplier, String orderID, String orderDate) {
    private void createProductsAndSubComponents() {

        //parent
        alcBottle = new MedProduct("Alc123", "32 Fl Oz Bottle of Alcohol", "CVS",
                "order123", "05/07/2019");

        //70% alcohol and components
        Component alcohol100 = new Component("alc100", "100% Alcohol", "ChemicalsRUS");
        Component distWater = new Component("distwater20", "Distilled Water", "Dasani");
        ArrayList<Component> alc70Comp = new ArrayList<>();
        alc70Comp.add(alcohol100);
        alc70Comp.add(distWater);
        Component alcohol70 = new Component("alc70", "70% Alcohol", "ChemicalsRUS", alc70Comp);

        //bottle configuration setup
        Component cap = new Component("cap222", "Bottle Cap", "Plastico");
        Component bottle = new Component("bottle123", "32 Fl Oz Bottle", "Plastico");
        ArrayList<Component> bottleconfig = new ArrayList<>();
        bottleconfig.add(bottle);
        bottleconfig.add(cap);
        Component bottleSet = new Component("bottleconfig1", "32 Fl Oz Bottle Set", "Plastico", bottleconfig);

        //create resin components
        Component transparentResin = new Component("resin123", "Transparent Clear Resin", "CheapResins");
        Component whiteResin = new Component("resin123", "Opaque White Resin", "CheapResins");

        //add resin components to cap and bottle components list
        ArrayList<Component> capComponents = new ArrayList<>();
        capComponents.add(whiteResin);
        ArrayList<Component> bottleComponents = new ArrayList<>();
        bottleComponents.add(transparentResin);

        //add components list to cap and bottle
        cap.setComponents(capComponents);
        bottle.setComponents(bottleComponents);

        //add 70% alcohol and bottle configuration to overall med product
        ArrayList<Component> alcBottleComponents = new ArrayList<>();
        alcBottleComponents.add(bottleSet);
        alcBottleComponents.add(alcohol70);
        alcBottle.setComponents(alcBottleComponents);

        //the ben product parent
        ben = new MedProduct("ben3.0", "Ben You", "Ben You's parents", "", "");

        //sushi and its components
        Component caliSushi = new Component("", "Ootoro (Fatty Tuna) Sushi", "California");
        Component rice = new Component("rice2019", "Sushi Rice", "Mexico Rice Farmers");
        Component tuna = new Component("tunaX", "Was Frozen Raw Tuna", "Random Fishers");
        ArrayList<Component> sushiComponents = new ArrayList<>();
        sushiComponents.add(rice);
        sushiComponents.add(tuna);
        caliSushi.setComponents(sushiComponents);

        //overall stress and its components
        Component stress = new Component("stress10000", "Stress", "School Life");
        Component csStress = new Component("CS123", "Computer Science Major Stress", "Cal Poly Pomona");
        Component lifeStress = new Component("life411", "Outside School Life Stress", "");
        ArrayList<Component> stressComponents = new ArrayList<>();
        stressComponents.add(lifeStress);
        stressComponents.add(csStress);
        stress.setComponents(stressComponents);

        //CS stress components and add it to CS stress
        Component softEngStress = new Component("softengXX", "Software Engineering Stress", "CS4800");
        Component automataStress = new Component("automataXX", "Formal Languages and Automata Stress", "CS3110");
        Component mathStress = new Component("awfulmath", "Required Math Courses", "Can't remember, too awful");
        ArrayList<Component> csStressComponents = new ArrayList<>();
        csStressComponents.add(softEngStress);
        csStressComponents.add(automataStress);
        csStressComponents.add(mathStress);
        csStress.setComponents(csStressComponents);

        //Software Engineering Components
        Component codeBreaking = new Component("", "Code doesn't work stress", "Team");
        Component paperwork = new Component("GoodFireFeed", "Lots of paperwork stress", "CS4800");
        Component presentation = new Component("presentation123", "Presentation stress", "CS4800");
        Component lisaStress = new Component("", "Lisa stress", "Lisa");
        ArrayList<Component> seComponents = new ArrayList<>();
        seComponents.add(codeBreaking);
        seComponents.add(paperwork);
        seComponents.add(presentation);
        seComponents.add(lisaStress);
        softEngStress.setComponents(seComponents);

        //Lisa Stress Components
        ArrayList<Component> lisaStressPack = new ArrayList<>();
        Component lisaWorkStress = new Component("", "Lisa adding more work stress", "Lisa");
        Component lisaComplainingStress = new Component("", "Lisa complaining stress", "Lisa");
        Component lisaMessingStress = new Component("", "Lisa messing around stress", "Lisa");
        Component lisaBeingLisa = new Component("", "Lisa being Lisa stress", "Lisa");
        lisaStressPack.add(lisaWorkStress);
        lisaStressPack.add(lisaComplainingStress);
        lisaStressPack.add(lisaMessingStress);
        lisaStressPack.add(lisaBeingLisa);
        lisaStress.setComponents(lisaStressPack);

        //add benComponents to ben
        ArrayList<Component> benComponents = new ArrayList<>();
        benComponents.add(stress);
        benComponents.add(caliSushi);
        ben.setComponents(benComponents);
    }
}
