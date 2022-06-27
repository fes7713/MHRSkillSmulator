/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillsmulator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author fes77
 */
public class Slot {
    private int slot3;
    private int slot2;
    private int slot1;

    public Slot(int slot3, int slot2, int slot1) {
        this.slot3 = slot3;
        this.slot2 = slot2;
        this.slot1 = slot1;
    }

    public int getSlot3() {
        return slot3;
    }

    public int getSlot2() {
        return slot2;
    }

    public int getSlot1() {
        return slot1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Integer> numStack = new ArrayList();
        IntStream.range(0, slot3).forEach(x -> numStack.add(3));
        IntStream.range(0, slot2).forEach(x -> numStack.add(2));
        IntStream.range(0, slot1).forEach(x -> numStack.add(1));
        
        for(int i = 0; i < numStack.size(); i++)
        {
            sb.append(numStack.get(i));
            if(i != numStack.size() - 1)
                sb.append("-");
        }
        
        return sb.toString();
    }
    
    public static void main(String[] args)
    {
        Slot slot = new Slot(0, 2, 1);
        System.out.println(slot);
    }
    
}
