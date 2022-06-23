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
public class DamageUpSkill extends AttackSkill implements DamageUp{
    private final int[] damageSequence;

    public DamageUpSkill(String title, int cost, int[] damageSequence) {
        super(title, damageSequence.length, cost);
        this.damageSequence = damageSequence;
    }
    
    @Override
    public int getDamageUp(int level) {
        if(level == 0)
            return 0;
        return damageSequence[level - 1];
    }
    
    @Override
    public void editExpectation(Expectation exp, int level){
        exp.addDamageIncrease(getDamageUp(level));
    }
}
