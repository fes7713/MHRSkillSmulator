/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import skillsmulator.Skill.AffinitySkill;
import skillsmulator.Skill.AffinityUp;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.DamageUp;
import skillsmulator.Skill.DamageUpMultiplePreSkill;
import skillsmulator.Skill.DamageUpSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Armor {
    private final String title;
    private final Map<Skill, Integer> skills;
    private int slot1;
    private int slot2;
    private int slot3;

    private int score;
    
    private List<Armor> similar;
    private int totalDamage;
    private int totalAffinity;
    private float damageMultipler;
    private float affinityMultipler;
    
    public Armor(String title, Map<Skill, Integer> skills, int slot1, int slot2, int slot3) {
        this.title = title;
        this.skills = skills;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        totalDamage = findTotalDamage();
        totalAffinity = findTotalAffinity();
        updateScore();
        
    }
    
    public Armor(String title, int slot1, int slot2, int slot3) {
        this.title = title;
        this.skills = new HashMap();
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        totalDamage = findTotalDamage();
        totalAffinity = findTotalAffinity();
        
    }

    @Override
    public String toString() {
        return title;
    }
    
    public void updateScore()
    {
        score = slot1 * 1 + slot2 * 2 + slot3 * 3;
        score += skills
                .keySet()
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .map(skill -> skill.getCost() * skills.get(skill))
                .reduce(0, Integer::sum);
    }
    
    public void addSkill(Skill skill, int level)
    {
        if(level < 0)
            throw new IllegalArgumentException("Level should be mor than 0 in code");
        
        if(level > skill.getMax())
            level = skill.getMax() - 1;
        skills.put(skill, level);
        if(skill instanceof DamageUp)
            totalDamage = findTotalDamage();
        if(skill instanceof AffinityUp)
            totalAffinity = findTotalAffinity();
    }
    
    private int findTotalDamage()
    {
        skills. entrySet().
                stream().
//                filter(DamageUp.class::isInstance).
//                map (DamageUp.class::cast).
                forEach(skill -> System.out.println(skill.getKey() instanceof DamageUp));
        
        return skills.
                keySet().
                stream().
                filter(DamageUp.class::isInstance).
                map (DamageUp.class::cast).
                map(
                        skill -> skill.getDamageUp(skills.get(skill))
                ).reduce(0, Integer::sum);
    }
    
    private int findTotalAffinity()
    {
        return skills.
                keySet().
                stream().
                filter(AffinityUp.class::isInstance).
                map (AffinityUp.class::cast).
                map(
                        skill -> skill.getAffinityUp(skills.get(skill))
                ).reduce(0, Integer::sum);
    }
    
//    private int findTotalDamageMultiplier()
//    {
//        skills. entrySet().
//                stream().
////                filter(DamageUp.class::isInstance).
////                map (DamageUp.class::cast).
//                forEach(skill -> System.out.println(skill.getKey() instanceof DamageUp));
//        
//        return skills.
//                keySet().
//                stream().
//                filter(DamageUp.class::isInstance).
//                map (DamageUp.class::cast).
//                map(
//                        skill -> skill.getDamageUp(skills.get(skill))
//                ).reduce(0, Integer::sum);
//    }
//    
//    private int findTotalAffinityMultiplier()
//    {
//        skills. entrySet().
//                stream().
////                filter(DamageUp.class::isInstance).
////                map (DamageUp.class::cast).
//                forEach(skill -> System.out.println(skill.getKey() instanceof DamageUp));
//        
//        return skills.
//                keySet().
//                stream().
//                filter(DamageUp.class::isInstance).
//                map (DamageUp.class::cast).
//                map(
//                        skill -> skill.getDamageUp(skills.get(skill))
//                ).reduce(0, Integer::sum);
//    }
    
    private int getTotalDamage()
    {
        return totalDamage;
    }
    
    private int getTotalAffinity()
    {
        return totalAffinity;
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
    
    public static void main(String[] args)
    {
        Skill attackBoost = new DamageUpMultiplePreSkill("AttackBoost", 2, new int[]{3, 5, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
        Skill peakPerformance = new DamageUpSkill("PeakPerformance", 2, new int[]{5, 10, 20});
        Skill criticalEye = new AffinitySkill("CriticalEye", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
        
        
        Armor armor = new Armor("Helm", 0, 0, 0);
        armor.addSkill(attackBoost, 4);
        armor.addSkill(peakPerformance, 2);
        armor.addSkill(criticalEye, 4);
        System.out.println(armor.getTotalDamage());
        System.out.println(armor.getTotalAffinity());
    }
}
