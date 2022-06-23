/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Skill;

/**
 *
 * @author fes77
 */
public class Skill {
    private String name;
    private String decorationName;
    protected int maxLevel;
    private int cost;
    boolean active;


    public Skill(String name, String decorationName,  int maxLevel, int cost) {
        if(maxLevel <= 0)
            throw new IllegalArgumentException("Skill max level should be more than 0");
        this.name = name;
        this.decorationName = decorationName;
        this.maxLevel = maxLevel;
        this.cost = cost;
        active = true;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
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
        return "Skill{" + "title=" + name + '}';
    }
    
    public int getCost()
    {
        if(active)
            return cost;
        else
            return 0;
    }

    public boolean isActive() {
        return active;
    }
}
