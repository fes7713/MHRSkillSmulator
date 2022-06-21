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
public class DamageUpSkill extends Skill implements DamageUp{
    private final int[] damageSequence;

    public DamageUpSkill(String title, int cost, int[] damageSequence) {
        super(title, damageSequence.length, cost);
        this.damageSequence = damageSequence;
    }
    
    @Override
    public int getDamageUp(int level) {
        return damageSequence[level];
    }
    
    @Override
    public void editExpectation(Expectation exp, int level){
        super.editExpectation(exp, level);
        exp.addDamageIncrease(getDamageUp(level));
    }
}
