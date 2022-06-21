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
public class DamageAffinityUpSkill extends Skill implements DamageUp, AffinityUp{
    private final int[] damageSequence;
    private final int[] affinitySequence;

    public DamageAffinityUpSkill(String title, int cost, int[] damageSequence, int[] affinitySequence) {
        super(title, damageSequence.length, cost);
        if(damageSequence.length != affinitySequence.length)
            throw new IllegalArgumentException("Length of damage and affinity sequence should match");
        
        this.damageSequence = damageSequence;
        this.affinitySequence = affinitySequence;
    }

    
    
    @Override
    public int getDamageUp(int level) {
        return damageSequence[level];
    }

    @Override
    public int getAffinityUp(int level) {
        return affinitySequence[level];
    }

    @Override
    public void editExpectation(Expectation exp, int level) {
        super.editExpectation(exp, level);
        exp.addDamageIncrease(getDamageUp(level));
        exp.addAffinity(getAffinityUp(level));
    }
    
    
}
