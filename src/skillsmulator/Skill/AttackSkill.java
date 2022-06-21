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
public abstract class AttackSkill extends Skill{

    public AttackSkill(String title, int maxLevel, int cost) {
        super(title, maxLevel, cost);
    }
    
    
    abstract public void editExpectation(Expectation exp, int level);
}
