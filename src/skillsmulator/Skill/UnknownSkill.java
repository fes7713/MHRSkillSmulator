/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Skill;

/**
 *
 * @author fes77
 */
public class UnknownSkill extends Skill{

    public UnknownSkill(String name, int cost) {
        super(name, "UNKNOWN", cost, Integer.MAX_VALUE);
    }
    
    public int getMax()
    {
        return 0;
    }
    
}
