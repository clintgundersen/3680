package edu.uvu.cpg.projecttwoclintgundersen;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

//added by me
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


public class SelectDice extends Activity implements OnClickListener
{
    private Button addDieButton;
    private Button rollDiceButton;
    private Button resetButton;
    private Button reRollButton;
    TextView totalLabel;
    TextView outputLabel;
    TextView pileTotalLabel;

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

    DicePile myPile;
    int polyhedral = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dice);

        //initialize our dice pile object
        myPile = new DicePile();

        //setup buttons
        addDieButton = (Button) findViewById(R.id.addDieButton);
        addDieButton.setOnClickListener(this);

        rollDiceButton = (Button) findViewById(R.id.rollDiceButton);
        rollDiceButton.setOnClickListener(this);

        resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this);

        reRollButton = (Button) findViewById(R.id.reRollButton);
        reRollButton.setOnClickListener(this);

        //setup dynamic labels
        totalLabel = (TextView) findViewById(R.id.totalDiceLabel);
        outputLabel = (TextView) findViewById(R.id.outputLabel);
        pileTotalLabel = (TextView) findViewById(R.id.pileTotalLabel);
    }

    public void onClick(View myView)
    {
        Button clickedBtn = (Button)myView;
        if (clickedBtn == addDieButton)
        {
            final int DICE_IN_OUTPUT = 12;
            if (polyhedral > 0 && myPile.getSize() < DICE_IN_OUTPUT)
            {
                System.out.println("Adding Die");
                myPile.addDie(polyhedral);
                totalLabel.setText(Integer.toString(myPile.getSize()));
            }
            else if ( myPile.getSize() >= DICE_IN_OUTPUT)
            {
                totalLabel.setTextColor(Color.RED);
                outputLabel.setText("Too many dice.");
            }
        }
        else if(clickedBtn == rollDiceButton)
        {
            System.out.println("Rolling Dice");
            printRolls();
        }
        else if(clickedBtn == resetButton)
        {
            myPile.clear();
            totalLabel.setText(Integer.toString(myPile.getSize()));
            //outputLabel.setText(null);
            totalLabel.setTextColor(Color.BLACK);
            printRolls();
        }
        else if (clickedBtn == reRollButton)
        {
            System.out.println("Rerolling current pile");
            for (int i = 0; i < myPile.getSize(); i++)
            {
                myPile.getDieRoll(i).rollDie();
            }
            printRolls();
        }
    }

    public void printRolls()
    {
        String output = "";
        int total = 0;
        for (int i = 0; i < myPile.getSize(); i++)
        {
            output += myPile.getDieRoll(i).getPolyhedral() + ": " + myPile.getDieRoll(i).getFace() + ", ";
            total += myPile.getDieRoll(i).getFace();
        }
        outputLabel.setText(output);
        pileTotalLabel.setText(Integer.toString(total));
    }

    public void onRadioButtonClicked(View view)
    {
        //is something checked?
        boolean checked = ((RadioButton) view).isChecked();

        //find out what was checked
        switch(view.getId())
        {
            case R.id.twoSidedRadio:
                if (checked)
                    System.out.println("2 Sided Checked");
                    polyhedral = TWO_SIDES;
                break;
            case R.id.fourSidedRadio:
                if (checked)
                    System.out.println("4 Sided Checked");
                    polyhedral = FOUR_SIDES;
                break;
            case R.id.sixSidedRadio:
                if (checked)
                    System.out.println("6 Sided Checked");
                    polyhedral = SIX_SIDES;
                break;
            case R.id.eightSidedRadio:
                if (checked)
                    System.out.println("8 Sided Checked");
                    polyhedral = EIGHT_SIDES;
                break;
            case R.id.tenSidedRadio:
                if (checked)
                    System.out.println("10 Sided Checked");
                    polyhedral = TEN_SIDES;
                break;
            case R.id.twelveSidedRadio:
                if (checked)
                    System.out.println("12 Sided Checked");
                    polyhedral = TWELVE_SIDES;
                break;
            case R.id.twentySidedRadio:
                if (checked)
                    System.out.println("20 Sided Checked");
                    polyhedral = TWENTY_SIDES;
                break;
            case R.id.thirtySidedRadio:
                if (checked)
                    System.out.println("30 Sided Checked");
                    polyhedral = THIRTY_SIDES;
                break;
            case R.id.hundredSidedRadio:
                if (checked)
                    System.out.println("100 Sided Checked");
                    polyhedral = HUNDRED_SIDES;
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_dice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
