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
public class AffinitySkill extends AttackSkill implements AffinityUp{
    private final int[] affinitySequence;

    public AffinitySkill(String title, String decorationName, int cost, int[] affinitySequence) {
        super(title, decorationName, affinitySequence.length, cost);
        this.affinitySequence = affinitySequence;
    }

    @Override
    public int getAffinityUp(int level) {
        if(level == 0)
            return 0;
        return affinitySequence[level - 1];
    }

    @Override
    public void editExpectation(Expectation exp, int level) {
        exp.addAffinity(getAffinityUp(level));
    }
    
    
}
