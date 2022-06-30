/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Skill;

/**
 *
 * @author fes77
 */
public class SeriesSkill extends Skill{

    public SeriesSkill(String name, int maxLevel) {
        super(name, "UNKNOWN", 2, maxLevel);
    }
    
    @Override
    public void updateScore()
    {
        score = getRequired() * getCost() * getCost() * 3;
    }
}
