/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
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
            new AffinitySkill("CriticalDraw", 2, new int[]{10, 20, 40});
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
    
    private void prepare()
    {
        selectedHelms.clear();
        selectedChests.clear();
        
        boolean addFlag;
        for(Armor helm: helms)
        {
            addFlag = true;
            for(int i = 0; i < selectedHelms.size(); )
            {
                int compare = helm.compareTo(selectedHelms.get(i));
                if(compare > 0)
                    selectedHelms.remove(i);
                else if(compare < 0)
                {
                    addFlag = false;
                    break;
                }
                else
                    i++;
            }
            if(addFlag)
                selectedHelms.add(helm);
        }
        
        for(Armor chest: chests)
        {
            addFlag = true;
            for(int i = 0; i < selectedChests.size(); )
            {
                int compare = chest.compareTo(selectedChests.get(i));
                if(compare > 0)
                    selectedChests.remove(i);
                else if(compare < 0)
                {
                    addFlag = false;
                    break;
                }
                else
                    i++;
            }
            if(addFlag)
                selectedChests.add(chest);
        }
            
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
        helm4.addSkill(agitator, 2);
        helm4.addSkill(counterstrike, 1);
        Armor helm6 = new Armor("Helm6", 1, 1, 0);
        helm4.addSkill(criticalEye, 1);
        
        List<Armor> helms = new LinkedList();
        helms.add(helm);
        helms.add(helm1);
        helms.add(helm2);
        helms.add(helm3);
        helms.add(helm4);
        helms.add(helm5);
        helms.add(helm6);
        
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
        
        List<Armor> chests = new LinkedList();
        chests.add(chest);
        chests.add(chest1);
        chests.add(chest2);
    }
}
