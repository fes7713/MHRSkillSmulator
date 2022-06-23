/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import skillsmulator.Skill.Skill;

/**
 *
 * @author fes77
 */
public class SkillItem
{
    private String name;
    private String decoration;
    private int level;

    public SkillItem(Skill skill, int level) {
        name = skill.getName();
        decoration = skill.getDecorationName();
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

}
