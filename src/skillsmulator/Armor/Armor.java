/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator.Armor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import skillsmulator.Decoratable;
import static skillsmulator.Simulator.attackBoost;
import static skillsmulator.Simulator.criticalEye;
import static skillsmulator.Simulator.peakPerformance;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public abstract class Armor implements Comparable<Armor>, Decoratable{
    private final String title;
    protected final Map<Skill, Integer> skills;
    private int slot1;
    private int slot2;
    private int slot3;

    private int score;
    
    private List<Armor> similar;
    
    public Armor(String title, Map<Skill, Integer> skills, int slot3, int slot2, int slot1) {
        this.title = title;
        this.skills = skills;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        updateScore();
        
    }
    
    public Armor(String title, int slot3, int slot2, int slot1) {
        this.title = title;
        this.skills = new HashMap();
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---").append(title).append("---")
            .append("\n[Slots]")
                .append(slot3).append("-")
                .append(slot2).append("-")
                .append(slot1)
            .append("\n[Skills]");
        for(Skill skill: skills.keySet())
        {
            sb.append("\n\t")
                    .append(skill)
                    .append("->")
                    .append(skills.get(skill));
        }
                
        return sb.toString();
    }
    
    public void updateScore()
    {
        score = slot1 * 1 + slot2 * 2 + slot3 * 3;
        score += skills
                .keySet()
                .stream()
                .filter(skill -> AttackSkill.class.isInstance(skill) || skill.getRequired() > 0)
                .map(skill -> 
                        skill.getCost() * 
                        skills.get(skill) * 
                        (skill.getRequired() > 0 ? skill.getRequired() * 2 : 1) * 
                        (skill.getCost() >= 2 ? 2 : 1))
                .reduce(0, Integer::sum);

    }
    
    public void addSkill(Skill skill, int level)
    {
        if(level <= 0)
            throw new IllegalArgumentException("Level should be mor than 0 in code");
        
        if(level > skill.getMax())
            level = skill.getMax();
        skills.put(skill, level);
        updateScore();
    }

    public String getName() {
        return title;
    }
    
    public int getScore()
    {
        return score;
    }
    
    public Map<Skill, Integer> getSkills() {
        return skills;
    }

    public int getSlot1() {
        return slot1;
    }

    public int getSlot2() {
        return slot2;
    }

    public int getSlot3() {
        return slot3;
    }
    
    public void addSimilar(Armor armor)
    {
        similar.add(armor);
    }
    
    public void clearSimilar(Armor armor)
    {
        similar.clear();
    }
    
    public int isStrongerThan(Armor o) {

        
        int slotBattle, skillBattle;
        boolean skillEqual = false, slotEqual = false;
        
        if(slot1 == o.getSlot1() && slot2 == o.getSlot2() && slot3 == o.getSlot3())
        {
            slotEqual = true;
            slotBattle = 0;
        }
        else{
            int slot3Battle = slot3 - o.getSlot3();
            int slot2Battle = slot3 + slot2 - (o.getSlot3() + o.getSlot2());
            int slot1Battle = slot3 + slot2 + slot1 - (o.getSlot3() + o.getSlot2() + o.getSlot1());

            if(slot3Battle >= 0 && slot2Battle >= 0 && slot1Battle >= 0)
                slotBattle = 1;
            else if(slot3Battle <= 0 && slot2Battle <= 0 && slot1Battle <= 0)
                slotBattle = -1;
            else
                slotBattle = 0;
        }
        
        // skill battle
        
        
        boolean allSkillExist1 = skills
                .keySet()
                .stream()
                .filter(skill -> skill.isActive())
                .allMatch(skill -> o.getSkills().containsKey(skill));
        boolean allSkillExist2 = o.getSkills()
                .keySet()
                .stream()
                .filter(skill -> skill.isActive())
                .allMatch(skill -> skills.containsKey(skill));
        
        if(allSkillExist1 && allSkillExist2)
        {
            skillEqual = skills
                    .keySet()
                    .stream()
                    .filter(skill -> skill.isActive())
                    .allMatch(skill -> skills.get(skill) == o.getSkills().get(skill));
            
            if(skillEqual)
            {
                skillBattle = 0;
            }
                
            else{
                boolean compare1to2 = skills
                        .keySet()
                        .stream()
                        .filter(skill -> skill.isActive())
                        .allMatch(skill -> skills.get(skill) >= o.getSkills().get(skill));
            
                boolean compare2to1 = skills
                        .keySet()
                        .stream()
                        .filter(skill -> skill.isActive())
                        .allMatch(skill -> skills.get(skill) <= o.getSkills().get(skill));
                
                if(compare1to2)
                    skillBattle = 1;
                else if(compare2to1)
                    skillBattle = -1;
                else
                    throw new RuntimeException("Something wrong with skill calc");
            }
        }
        // One has all of skills of anothers but the opposite is not true
        else if(allSkillExist1)
        {
            boolean compare = skills
                        .keySet()
                        .stream()
                        .filter(skill -> skill.isActive())
                        .allMatch(skill -> skills.get(skill) <= o.getSkills().get(skill));
            if(compare)
                skillBattle = -1;
            else
                skillBattle = 0;
        }
        else if(allSkillExist2)
        {
            boolean compare = o.getSkills()
                        .keySet()
                        .stream()
                        .filter(skill -> skill.isActive())
                        .allMatch(skill -> skills.get(skill) >= o.getSkills().get(skill));
            if(compare)
                skillBattle = 1;
            else
                skillBattle = 0;
        }
        else{
            skillBattle = 0;
        }
        
        if(slotEqual && skillEqual)
            return -1;
        if(slotEqual)
            return skillBattle;
        if(skillEqual)
            return slotBattle;
        
//        if(skillBattle == 0 || slotBattle == 0)
//            return 0;
//        if(skillBattle == 0)
//            return 0;
        if(skillBattle > 0 && slotBattle > 0)
            return 1;
        if(skillBattle < 0 && slotBattle < 0)
            return -1;
        return 0;
    }
    
    @Override
    public int compareTo(Armor o) {
        return score - o.getScore();
    }
    
    
    
    public static void main(String[] args)
    {
        
        attackBoost.setRequired(2);
        Armor armor = new Helm("Helm", 0, 2, 1);
        armor.addSkill(attackBoost, 4);
        armor.addSkill(peakPerformance, 2);
        armor.addSkill(criticalEye, 4);
        
        Armor armor1 = new Helm("Helm1", 1, 1, 1);
        armor1.addSkill(attackBoost, 4);
        armor1.addSkill(peakPerformance, 2);
        armor1.addSkill(criticalEye, 2);
        criticalEye.setActive(false);
        
        System.out.println(armor.isStrongerThan(armor1));
        System.out.println(armor.getScore());
        System.out.println(armor);
    }

    

    
}
