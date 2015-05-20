package edu.uvu.cpg.projecttwoclintgundersen;
import android.content.EntityIterator;

import java.util.Random;

/**
 * Created by clint on 9/22/14.
 */
public class DieRoll
{
    private String polyhedral;
    private int maxFace;
    private int face;
    private Random roll = new Random();

    //constants for handling dice without magic numbers
    static final private int TWO_SIDES = 2;
    static final private int FOUR_SIDES = 4;
    static final private int SIX_SIDES = 6;
    static final private int EIGHT_SIDES = 8;
    static final private int TEN_SIDES = 10;
    static final private int TWELVE_SIDES = 12;
    static final private int TWENTY_SIDES = 20;
    static final private int THIRTY_SIDES = 30;
    static final private int HUNDRED_SIDES = 100;

    DieRoll(int polyhedral)
    {
        setPolyhedral(polyhedral);
        rollDie();
        System.out.println("Die created " + this.polyhedral + ": " + face);
    }

    public void rollDie()
    {
        face = roll.nextInt(maxFace) + 1;
    }

    public void setPolyhedral(int poly)
    {
        if (poly == TWO_SIDES) {
            maxFace = TWO_SIDES;
            polyhedral = "D2";
        }
        else if (poly == FOUR_SIDES)
        {
            maxFace = FOUR_SIDES;
            polyhedral = "D4";
        }
        else if (poly == SIX_SIDES)
        {
            maxFace = SIX_SIDES;
            polyhedral = "D6";
        }
        else if (poly == EIGHT_SIDES)
        {
            maxFace = EIGHT_SIDES;
            polyhedral = "D8";
        }
        else if (poly == TEN_SIDES)
        {
            maxFace = TEN_SIDES;
            polyhedral = "D10";
        }
        else if (poly == TWELVE_SIDES)
        {
            maxFace = TWELVE_SIDES;
            polyhedral = "D12";
        }
        else if (poly == TWENTY_SIDES)
        {
            maxFace = TWENTY_SIDES;
            polyhedral = "D20";
        }
        else if (poly == THIRTY_SIDES)
        {
            maxFace = THIRTY_SIDES;
            polyhedral = "D30";
        }
        else if (poly == HUNDRED_SIDES)
        {
            maxFace = HUNDRED_SIDES;
            polyhedral = "D100";
        }
    }

    public String getPolyhedral()
    {
        return polyhedral;
    }

    public int getFace()
    {
        return face;
    }
}
