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
public class DamageAffinityUpSkill extends AttackSkill implements DamageUp, AffinityUp{
    private final int[] damageSequence;
    private final int[] affinitySequence;

    public DamageAffinityUpSkill(String title, String decorationName, int cost, int[] damageSequence, int[] affinitySequence) {
        super(title, decorationName, damageSequence.length, cost);
        if(damageSequence.length != affinitySequence.length)
            throw new IllegalArgumentException("Length of damage and affinity sequence should match");
        
        this.damageSequence = damageSequence;
        this.affinitySequence = affinitySequence;
    }

    
    
    @Override
    public int getDamageUp(int level) {
        if(level == 0)
            return 0;
        return damageSequence[level - 1];
    }

    @Override
    public int getAffinityUp(int level) {
        if(level == 0)
            return 0;
        return affinitySequence[level - 1];
    }

    @Override
    public void editExpectation(Expectation exp, int level) {
        exp.addDamageIncrease(getDamageUp(level));
        exp.addAffinity(getAffinityUp(level));
    }
    
    
}
