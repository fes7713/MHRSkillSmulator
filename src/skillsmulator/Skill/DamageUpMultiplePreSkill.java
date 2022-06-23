/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Skill;

import skillsmulator.Expectation;

/**
 *
 * @author fes77
 */
public class DamageUpMultiplePreSkill extends DamageUpSkill implements DamageMultiplier{

    private double[] multiplerSequece;

    public DamageUpMultiplePreSkill(String title, int cost, int[] damageSequence, double[] multiplerSequece) {
        super(title, cost, damageSequence);
        if(damageSequence.length != multiplerSequece.length)
            throw new IllegalArgumentException("Length of damage and affinity sequence should match");
        this.multiplerSequece = multiplerSequece;
    }
    
    
    @Override
    public double getDamageMultiplier(int level) {
        if(level == 0)
            return 1;
        return multiplerSequece[level - 1];
    }
    
    @Override
    public void editExpectation(Expectation exp, int level){
        super.editExpectation(exp, level);
        exp.multiplyPre(getDamageMultiplier(level));
    }
}
