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
public class AffinitySkill extends Skill implements AffinityUp{
    private final int[] affinitySequence;

    public AffinitySkill(String title, int cost, int[] affinitySequence) {
        super(title, affinitySequence.length, cost);
        this.affinitySequence = affinitySequence;
    }

    @Override
    public int getAffinityUp(int level) {
        return affinitySequence[level];
    }

    @Override
    public void editExpectation(Expectation exp, int level) {
        super.editExpectation(exp, level);
        exp.addAffinity(getAffinityUp(level));
    }
    
    
}
