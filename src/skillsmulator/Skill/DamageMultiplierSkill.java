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
public class DamageMultiplierSkill extends AttackSkill implements DamageMultiplier{

    private double[] multiplerSequece;

    public DamageMultiplierSkill(String title, String decorationName, int cost, double[] multiplerSequece) {
        super(title, decorationName, cost, multiplerSequece.length);
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
        exp.multiplyPre(getDamageMultiplier(level));
    }
    
}
