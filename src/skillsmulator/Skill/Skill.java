/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Skill;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author fes77
 */
public class Skill {
    private String name;
    private String decorationName;
    protected int maxLevel;
    private int cost;

    private SimpleIntegerProperty required;
    
    BooleanProperty activeProperty;

    public Skill(String name, String decorationName, int cost, int maxLevel) {
        if(maxLevel <= 0)
            throw new IllegalArgumentException("Skill max level should be more than 0");
        this.name = name;
        this.decorationName = decorationName;
        this.maxLevel = maxLevel;
        this.cost = cost;
 
        activeProperty = new SimpleBooleanProperty(true);
        required = new SimpleIntegerProperty(0);
    }
    
    public BooleanProperty activeProperty()
    {
        return activeProperty;
    }
    
    public void setActive(boolean active)
    {
        activeProperty.set(active);
    }
    
    public int getMax()
    {
        return maxLevel;
    }

    public String getName() {
        return name;
    }

    public String getDecorationName() {
        return decorationName;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public int getCost()
    {
        if(activeProperty.get())
            return cost;
        else
            return 0;
    }

    public boolean isActive() {
        return activeProperty.get();
    }

    public int getRequired() {
        return required.get();
    }

    public void setRequired(int required) {
        if(required <= 0)
            this.required.set(0);
        else if(required >= maxLevel)
            this.required.set(maxLevel);
        else
            this.required.set(required);
    }
    
    public SimpleIntegerProperty requiredProperty()
    {
        return required;
    }
}
