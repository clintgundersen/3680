package edu.uvu.cpg.projecttwoclintgundersen;

import java.util.ArrayList;

/**
 * Created by clint on 9/22/14.
 */
public class DicePile
{
    private ArrayList<DieRoll> rollValues;

    DicePile()
    {
     rollValues = new ArrayList();
    }

    public void addDie(int polyhedral)
    {
        rollValues.add(new DieRoll(polyhedral));
    }

    public void clear()
    {
        rollValues.clear();
    }

    public int getSize()
    {
        return rollValues.size();
    }

    public DieRoll getDieRoll(int index)
    {
        return rollValues.get(index);
    }

}
