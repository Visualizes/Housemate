package com.visual.android.housemate;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by RamiK on 4/1/2017.
 */

public class SearchEditText extends android.support.v7.widget.AppCompatEditText {

    private List<Account> accounts = new ArrayList<>();
    private List<Integer> availableCursorSpots = new ArrayList<>();
    private int textEngineStart = 0;

    public SearchEditText(Context context) {
        super(context);
    }

    public SearchEditText (Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SearchEditText (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void updateSelectedAccounts(List<Account> accounts){
        this.accounts = accounts;
    }

    public void updateObjects(){
        String text = "";
        availableCursorSpots = new ArrayList<>();
        availableCursorSpots.add(0);
        for (Account account : accounts){
            text += account.getFirstName() + " " + account.getLastName() + ", ";
            availableCursorSpots.add(text.length());
        }
        //TODO: FIX BAND-AID
        /*
        if (textEngineStart == 0){
            text = " " + text;
            text = text.substring(0, text.length()-1);
        }
*/

        System.out.println(text);
        text = text.replaceAll(" ","&nbsp;");
        setText(Html.fromHtml("<font color=\"#BA68C8\">" + text +  "</font>"));
        //setText(text);
        System.out.println(getText().toString());
        setSelection(getText().length(), getText().length());
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd){
        CharSequence text = getText();
        if (text != null && availableCursorSpots != null) {
            List<Integer> differences = new ArrayList<>();
            for (int cursorSpot : availableCursorSpots){
                differences.add(cursorSpot - selEnd);
            }

            int closestIndex = 0;
            int diff = Integer.MAX_VALUE;
            for (int i = 0; i < differences.size(); ++i) {
                int abs = Math.abs(differences.get(i));
                if (abs < diff) {
                    closestIndex = i;
                    diff = abs;
                } else if (abs == diff && differences.get(i) > 0 && differences.get(closestIndex) < 0) {
                    //same distance to zero but positive
                    closestIndex = i;
                }
            }

            setSelection(availableCursorSpots.get(closestIndex), availableCursorSpots.get(closestIndex));
            return;
        }

        super.onSelectionChanged(selStart, selEnd);

    }

    public List<Integer> getAvailableCursorSpots() {
        return availableCursorSpots;
    }

    public void setTextEngineStart(int textEngineStart){
        this.textEngineStart = textEngineStart;
    }
}
