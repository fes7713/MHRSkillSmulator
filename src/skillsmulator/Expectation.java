/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

/**
 *
 * @author fes77
 */
public class Expectation {
    private int baseDamage;
    private int damageIncrease;
    private double damagePreMultiplier;
    private double damagePostMultiplier;
    
    private int affinity;
    private double affinityMultiplier;
    
    public static final double INITIAL_AFFINITY_MULTIPLIER = 1.25d;
    
    public Expectation(Weapon weapon)
    {
        baseDamage = weapon.getDamage();
        damageIncrease = weapon.getAffinity();
        damagePreMultiplier = 1;
        damagePostMultiplier = 1;
        affinity = 0;
        affinityMultiplier = INITIAL_AFFINITY_MULTIPLIER;
    }
    
    public void setBaseDamage(int amount)
    {
        baseDamage = amount;
    }
    
    public void addDamageIncrease(int amount)
    {
        damageIncrease += amount;
    }
    
    public void addAffinity(int amount)
    {
        affinity += amount;
    }
    
    public void setAffinityMultiplier(double coefficient)
    {
        affinityMultiplier = coefficient;
    }
    
    public void multiplyPost(double amount)
    {
        damagePostMultiplier *=  amount;
    }
    
    public void multiplyPre(double amount)
    {
        damagePreMultiplier *=  amount;
    }
    
    public double getExpectation()
    {
        return ((baseDamage * damagePreMultiplier + damageIncrease) * (100 - affinity) / 100 + 
                (baseDamage * damagePreMultiplier + damageIncrease) * affinity / 100 * affinityMultiplier) 
                * damagePostMultiplier;
    }
}
