/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import skillsmulator.Skill.AffinityMultiplierSkill;
import skillsmulator.Skill.AffinitySkill;
import skillsmulator.Skill.DamageAffinityUpSkill;
import skillsmulator.Skill.DamageUpMultiplePreSkill;
import skillsmulator.Skill.DamageUpSkill;
import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class Simulator {
    
    List<Armor>helms;
    List<Armor>chests;
    
    List<Armor>selectedHelms;
    List<Armor>selectedChests;
    
    Set<Skill>skills;
    Set<Skill>activeSkills;
    
    List<Equipment> equipments;
    
    public static final Skill attackBoost = 
            new DamageUpMultiplePreSkill("AttackBoost", 2, new int[]{3, 5, 9, 7, 8, 9, 10}, new double[]{1, 1, 1, 1.05, 1.06, 1.08, 1.1});
    public static final Skill peakPerformance = 
            new DamageUpSkill("PeakPerformance", 2, new int[]{5, 10, 20});
    public static final Skill criticalEye = 
            new AffinitySkill("CriticalEye", 2, new int[]{5, 10, 15, 20, 25, 30, 40});
    public static final Skill criticalBoost = 
            new AffinityMultiplierSkill("CriticalBoost", 2, new double[]{1.3, 1.35, 1.4});
    public static final Skill weaknessExploit = 
            new AffinitySkill("WeaknessExploit", 2, new int[]{15, 30, 50});
    public static final Skill criticalDraw = 
            new AffinitySkill("CriticalDraw", 3, new int[]{10, 20, 40});
    public static final Skill maximumMight = 
            new AffinitySkill("MaximumMight", 2, new int[]{10, 20, 30});
    public static final Skill agitator = 
            new DamageAffinityUpSkill("Agitator", 2, new int[]{4, 8, 12, 16, 20}, new int[]{3, 5, 7, 10, 15});
    public static final Skill counterstrike = 
            new DamageUpSkill("Counterstrike", 2, new int[]{10, 15, 25});
    public static final Skill punishingDraw = 
            new DamageUpSkill("Counterstrike", 2, new int[]{3, 5, 7});
        
    public Simulator()
    {
        helms = new ArrayList<>();
        chests = new ArrayList<>();

        selectedHelms = new ArrayList<>();
        selectedChests = new ArrayList<>();

        skills = new HashSet();
        activeSkills = new HashSet();
        
        equipments = new ArrayList<>();
                
        skills.add(attackBoost);
        skills.add(peakPerformance);
        skills.add(criticalEye);
        skills.add(criticalBoost);
        skills.add(weaknessExploit);
        skills.add(criticalDraw);
        skills.add(maximumMight);
        skills.add(agitator);
        skills.add(counterstrike);
        skills.add(punishingDraw);
        
        for(Skill skill: skills)
        {
            activeSkills.add(skill);
            skill.setActive(true);
        }
    }
    
    public void activateSkill(Skill skill)
    {
        activeSkills.add(skill);
        skill.setActive(true);
    }
    
    public void diactivateSkill(Skill skill)
    {
        activeSkills.remove(skill);
        skill.setActive(false);
    }
    
    private void DEFAULT_ACTIVE_SKILLS()
    {
        diactivateSkill(punishingDraw);
        diactivateSkill(counterstrike);
        diactivateSkill(criticalDraw);
    }
    
    public void addHelm(Armor helm)
    {
        helms.add(helm);
    }
    
    public void addChest(Armor chest)
    {
        chests.add(chest);
    }
    
    private void select(List<Armor>armors, List<Armor>selected)
    {
        selected.clear();
        
        boolean addFlag;
        for(Armor armor: armors)
        {
            addFlag = true;
            for(int i = 0; i < selected.size(); )
            {
                
                int compare = armor.isStrongerThan(selected.get(i));
                if(compare > 0)
                {
                    Armor selectedArmor = selected.remove(i);
                    System.out.println("Removing " + selectedArmor);
                }
                else if(compare < 0)
                {
                    System.out.println("Not adding " + armor);
                    addFlag = false;
                    break;
                }
                else
                    i++;
            }
            if(addFlag)
            {
                System.out.println("Adding " + armor);
                selected.add(armor);
            }
                
        }
    }
    
    private void prepare()
    {
        Weapon weapon = new Weapon("Sord", 200, 0);
        select(helms, selectedHelms);
        select(chests, selectedChests);
        
//        sortSeletced(selectedHelms);
//        sortSeletced(selectedChests);
        
        Collections.sort(selectedHelms, Comparator.reverseOrder());
        Collections.sort(selectedChests, Comparator.reverseOrder());
        
        for(int i = 0; i < selectedHelms.size(); i++)
        {
            for(int j = 0; j < selectedChests.size(); j++)
            {
//                System.out.println("1");
                equipments.add(new Equipment(weapon, selectedHelms.get(i), selectedChests.get(j)));
            }
        }
        Collections.sort(equipments);
        equipments.stream().forEach(e -> System.out.println(e.getScore()));
    }
    
//    private void sortSeletced(List<Armor> selected)
//    {
//        selected.stream().sorted((o1, o2) -> {
//            return o1.getScore() - o2.getScore();
//        });
//    }
    
    public void run()
    {
        prepare();
    }
    
    
    public static void main(String[] args)
    {
        Simulator simu = new Simulator();
        
        Armor helm = new Armor("Helm", 0, 1, 0);
        helm.addSkill(attackBoost, 2);
        Armor helm1 = new Armor("Helm1", 0, 0, 1);
        helm1.addSkill(criticalEye, 3);
        Armor helm2 = new Armor("Helm2", 0, 0, 2);
        helm2.addSkill(peakPerformance, 1);
        helm2.addSkill(criticalBoost, 1);
        Armor helm3 = new Armor("Helm3", 0, 1, 1);
        helm3.addSkill(maximumMight, 1);
        Armor helm4 = new Armor("Helm4", 0, 0, 3);
        helm4.addSkill(weaknessExploit, 2);
        Armor helm5 = new Armor("Helm5", 0, 1, 0);
        helm5.addSkill(agitator, 2);
        helm5.addSkill(counterstrike, 1);
        Armor helm6 = new Armor("Helm6", 0, 0, 1);
        helm6.addSkill(criticalEye, 1);
        Armor helm7 = new Armor("Helm7", 0, 0, 2);
        helm7.addSkill(peakPerformance, 2);
        helm7.addSkill(criticalBoost, 1);
        
        simu.addHelm(helm);
        simu.addHelm(helm1);
        simu.addHelm(helm2);
        simu.addHelm(helm3);
        simu.addHelm(helm4);
        simu.addHelm(helm5);
        simu.addHelm(helm6);
        simu.addHelm(helm7);
        
        Armor chest = new Armor("Chest", 0, 1, 0);
        chest.addSkill(criticalEye, 1);
        chest.addSkill(criticalBoost, 1);
        Armor chest1 = new Armor("Chest1", 0, 1, 0);
        chest1.addSkill(criticalEye, 2);
        Armor chest2 = new Armor("Chest2", 0, 0, 3);
        chest2.addSkill(attackBoost, 1);
        chest2.addSkill(criticalEye, 1);
        Armor chest3 = new Armor("Chest3", 0, 1, 0);
        chest3.addSkill(weaknessExploit, 1);
        Armor chest4 = new Armor("Chest4", 0, 0, 0);
        chest4.addSkill(attackBoost, 2);
        Armor chest5 = new Armor("Chest5", 0, 0, 0);
        chest5.addSkill(attackBoost, 1);
        Armor chest6 = new Armor("Chest5", 1, 0, 0);
        chest6.addSkill(criticalBoost, 1);
        chest6.addSkill(criticalDraw, 1);
        
        simu.addChest(chest);
        simu.addChest(chest1);
        simu.addChest(chest2);
        simu.addChest(chest3);
        simu.addChest(chest4);
        simu.addChest(chest5);
        simu.addChest(chest6);
        
        simu.run();
    }
}
