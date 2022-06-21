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
    


    public Skill(String title, int maxLevel, int cost) {
        this.title = title;
        this.maxLevel = maxLevel;
        this.cost = cost;
        if(maxLevel <= 0)
            throw new IllegalArgumentException("Skill max level should be more than 0");
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
        return cost;
    }
}
