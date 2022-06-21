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
public class AffinityMultiplierSkill extends Skill implements AffinityMultiplier{
    private double[] multiplierSequence;

    public AffinityMultiplierSkill(String title, int cost, double[] multiplierSequence) {
        super(title, multiplierSequence.length, cost);
        this.multiplierSequence = multiplierSequence;
    }

    @Override
    public double getAffinityMultiplier(int level) {
        return multiplierSequence[level];
    }

    @Override
    public void editExpectation(Expectation exp, int level) {
        super.editExpectation(exp, level);
        exp.setAffinityMultiplier(getAffinityMultiplier(level));
    }
    
    
}
