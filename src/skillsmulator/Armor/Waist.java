/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Armor;

import java.util.Map;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Waist extends Armor {

    public Waist(String title, Map<Skill, Integer> skills, int slot3, int slot2, int slot1) {
        super(title, skills, slot3, slot2, slot1);
    }

    public Waist(String title, int slot3, int slot2, int slot1) {
        super(title, slot3, slot2, slot1);
    }

    @Override
    public String toString() {
        return "Waist{"  + getName() +   '}';
    }
    
}
