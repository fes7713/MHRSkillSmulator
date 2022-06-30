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

    
    public AttackSkill(String title, String decorationName, int cost, int maxLevel) {
        super(title, decorationName, cost, maxLevel);
        
    }
    
    public void evalExpectation(Expectation exp, int level)
    {
        if(isActive())
            editExpectation(exp, level);
    }
    
    abstract protected void editExpectation(Expectation exp, int level);
    
    @Override
    public void updateScore()
    {
        score = (getRequired() + 1) * getCost() * (getCost() >= 2 ? 2 : 1) * (isActive() ? 1 : 0);
    }
}
