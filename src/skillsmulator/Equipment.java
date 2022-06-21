/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import skillsmulator.Skill.AffinityMultiplierSkill;
import skillsmulator.Skill.AffinitySkill;
import skillsmulator.Skill.AttackSkill;
import skillsmulator.Skill.DamageAffinityUpSkill;
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
        
        skills.keySet()
                .stream()
                .filter(AttackSkill.class::isInstance)
                .map(AttackSkill.class::cast)
                .forEach(skill -> skill.evalExpectation(exp, skills.get(skill)));
        
//        for(Skill skill: skills.keySet())
//        {
//            skill.editExpectation(exp, skills.get(skill));
//        }
        return exp.getExpectation();
    }
            
    public static void main(String[] args)
    {
        Skill attackBoost = new DamageUpMultiplePreSkill("AttackBoost", 2, new int[]{3, 5, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
        Skill peakPerformance = new DamageUpSkill("PeakPerformance", 2, new int[]{5, 10, 20});
        Skill criticalEye = new AffinitySkill("CriticalEye", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
        Skill criticalBoost = new AffinityMultiplierSkill("CriticalBoost", 2, new double[]{1.3, 1.35, 1.4});
        Skill weaknessExploit = new AffinitySkill("WeaknessExploit", 2, new int[]{15, 30, 50});
        Skill criticalDraw = new AffinitySkill("CriticalDraw", 2, new int[]{10, 20, 40});
        Skill maximumMight = new AffinitySkill("MaximumMight", 2, new int[]{10, 20, 30});
        Skill agitator = new DamageAffinityUpSkill("Agitator", 2, new int[]{4, 8, 12, 16, 20}, new int[]{3, 5, 7, 10, 15});
        Skill counterstrike = new DamageUpSkill("Counterstrike", 2, new int[]{10, 15, 25});
        Skill punishingDraw = new DamageUpSkill("Counterstrike", 2, new int[]{3, 5, 7});
        
        
        Armor helm = new Armor("Helm", 0, 0, 0);
        helm.addSkill(attackBoost, 2);
        helm.addSkill(peakPerformance, 1);
        Armor helm1 = new Armor("Helm", 0, 0, 0);
        helm1.addSkill(criticalEye, 3);
        helm1.addSkill(criticalBoost, 1);
        Armor helm2 = new Armor("Helm", 0, 0, 0);
        helm2.addSkill(peakPerformance, 1);
        helm2.addSkill(criticalBoost, 1);
        helm2.addSkill(attackBoost, 1);
        
        List<Armor> helms = new LinkedList();
        helms.add(helm);
        helms.add(helm1);
        helms.add(helm2);
        
        Armor chest = new Armor("Chest", 0, 0, 0);
        chest.addSkill(criticalEye, 1);
        chest.addSkill(criticalBoost, 1);
        Armor chest1 = new Armor("Chest", 0, 0, 0);
        chest1.addSkill(criticalEye, 2);
        Armor chest2 = new Armor("Chest", 0, 0, 0);
        chest2.addSkill(attackBoost, 1);
        chest2.addSkill(criticalEye, 1);
        
        List<Armor> chests = new LinkedList();
        chests.add(chest);
        chests.add(chest1);
        chests.add(chest2);
        
        Weapon weapon = new Weapon("Sord", 200, 0);
        
        Equipment equipment = new Equipment(weapon, helm, chest);
        System.out.println(equipment.getSkillMap());
        System.out.println(equipment.getExpectation());
    }
}
