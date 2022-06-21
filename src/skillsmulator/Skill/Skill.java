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
    private String title;
    protected int maxLevel;
    private int cost;
    boolean active;


    public Skill(String title, int maxLevel, int cost) {
        if(maxLevel <= 0)
            throw new IllegalArgumentException("Skill max level should be more than 0");
        this.title = title;
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
    
    @Override
    public String toString() {
        return "Skill{" + "title=" + title + '}';
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
