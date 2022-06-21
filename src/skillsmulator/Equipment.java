/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.HashMap;
import java.util.Map;
import skillsmulator.Skill.AffinityMultiplierSkill;
import skillsmulator.Skill.AffinitySkill;
import skillsmulator.Skill.DamageUpMultiplePreSkill;
import skillsmulator.Skill.DamageUpSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Equipment {
    private Weapon weapon;
    private Armor helm;
    private Armor chest;

    private Map<Skill, Integer> skills;
    private Map<Class, Map<Skill, Integer>> skillMap;
    
    public Equipment(Weapon weapon, Armor helm, Armor chest) {
        this.weapon = weapon;
        this.helm = helm;
        this.chest = chest;
        skills = getSkillMap();
    }
   
//    private void putSkillInterface()
//    {
//        skillMap.put(DamageUp.class, new HashMap());
//        skillMap.put(DamageMultiplier.class, new HashMap());
//        skillMap.put(AffinityUp.class, new HashMap());
//        skillMap.put(AffinityMultiplier.class, new HashMap());
//    }
//    
    public Map<Skill, Integer> getSkillMap()
    {
        Map<Skill, Integer> skillMap = new HashMap();
        skillMap.putAll(helm.getSkills());
        
        Map<Skill, Integer> chestSkills = chest.getSkills();
        for(Skill skill : chestSkills.keySet())
        {
            if(skillMap.containsKey(skill))
            {
                int sum = skillMap.get(skill) + chestSkills.get(skill);
                if(skill.getMax() > sum)
                {
                    skillMap.put(skill, sum);
                }
                else{
                    skillMap.put(skill, skill.getMax() - 1);
                }
            }
            else{
                skillMap.put(skill, chestSkills.get(skill));
            }
        }
        
        
        
        return skillMap;
    }
    
    public double getExpectation()
    {
//        int damage = weapon.getDamage();
//        int affinity = weapon.getAffinity();
//        
//        int damageIncrease = skills.keySet()
//                .stream()
//                .filter(DamageUp.class::isInstance)
//                .map (DamageUp.class::cast)
//                .map(
//                        skill -> skill.getDamageUp(skills.get(skill))
//                )
//                .reduce(0, Integer::sum);
//        
//        double newDamage = skills.keySet()
//                .stream()
//                .filter(DamageMultiplier.class::isInstance)
//                .map (DamageMultiplier.class::cast)
//                .map(multiplier -> multiplier.getDamageMultiplier(skills.get(multiplier)))
//                .reduce((double)damage, (x, y)-> x*y);
//        
//        System.out.println("New damage :" + newDamage);
//        int affinityIncrease = skills.
//                keySet().
//                stream().
//                filter(AffinityUp.class::isInstance).
//                map (AffinityUp.class::cast).
//                map(
//                        skill -> skill.getAffinityUp(skills.get(skill))
//                ).reduce(0, Integer::sum);
//        
//        
//        double affinityMultiplier = skills.
//                keySet().
//                stream().
//                filter(AffinityMultiplier.class::isInstance).
//                map (AffinityMultiplier.class::cast).
//                map(
//                        skill -> skill.getAffinityMultiplier(skills.get(skill))
//                ).reduce(1d, (x, y)-> x*y);
//        
//        if(affinity + affinityIncrease >= 100)
//            affinity = 100;
//        else
//            affinity += affinityIncrease;
//        
//        return (newDamage + damageIncrease) * (100 - affinity) / 100 + (newDamage + damageIncrease) * affinity / 100 * 1.25;
        Expectation exp = new Expectation(weapon);
        for(Skill skill: skills.keySet())
        {
            skill.editExpectation(exp, skills.get(skill));
        }
        return exp.getExpectation();
    }
            
    public static void main(String[] args)
    {
        Skill attackBoost = new DamageUpMultiplePreSkill("AttackBoost", 2, new int[]{3, 5, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
        Skill peakPerformance = new DamageUpSkill("PeakPerformance", 2, new int[]{5, 10, 20});
        Skill criticalEye = new AffinitySkill("CriticalEye", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
        Skill criticalBoost = new AffinityMultiplierSkill("CriticalBoost", 2, new double[]{1.3, 1.35, 1.4});
        
        
        Armor helm = new Armor("Helm", 0, 0, 0);
        helm.addSkill(attackBoost, 4);
        helm.addSkill(peakPerformance, 1);
        helm.addSkill(criticalEye, 4);
        
        Armor chest = new Armor("Chest", 0, 0, 0);
//        armor.addSkill(attackBoost, 4);
//        chest.addSkill(peakPerformance, 0);
        chest.addSkill(criticalEye, 5);
        chest.addSkill(criticalEye, 5);
        chest.addSkill(criticalBoost, 1);
        
        Weapon weapon = new Weapon("Sord", 200, 0);
        
        Equipment equipment = new Equipment(weapon, helm, chest);
        System.out.println(equipment.getSkillMap());
        System.out.println(equipment.getExpectation());
    }
}
