package com.visual.android.housemate;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RamiK on 4/1/2017.
 */

public class SearchEditText extends android.support.v7.widget.AppCompatEditText {

    private List<Account> accounts = new ArrayList<>();
    private List<Integer> availableCursorSpots = new ArrayList<>();
    private SpannableStringBuilder sb;
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
        int totalTextLength = 0;
        availableCursorSpots = new ArrayList<>();
        availableCursorSpots.add(0);
        sb = new SpannableStringBuilder();
        for (Account account : accounts){
            text = account.getFirstName() + " " + account.getLastName() + ", ";
            totalTextLength += text.length();
            availableCursorSpots.add(totalTextLength);
            TextView tv = createContactTextView(text);
            BitmapDrawable bd = (BitmapDrawable) convertViewToDrawable(tv);
            bd.setBounds(0, 0, bd.getIntrinsicWidth(),bd.getIntrinsicHeight());
            sb.append(text);
            sb.setSpan(new ImageSpan(bd), sb.length()-text.length(), sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public void updateObjectsAndUpdateText(){
        updateObjects();
        setText(sb);
        setSelection(getText().length(), getText().length());
    }

    public void addToText(String text){
        String extras = getText().toString().substring(availableCursorSpots.get(availableCursorSpots.size() - 1) + 1);
        updateObjects();
        sb.append(extras + text);
        setText(sb);
        setSelection(getText().length(), getText().length());
    }


    public TextView createContactTextView(String text){
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(getContext().getResources().getDimension(R.dimen.create_group_edittext));
        tv.setTextColor(this.getResources().getColor(R.color.colorPrimary));
        //tv.setBackgroundResource(R.drawable.bubble);
        //tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,android.R.drawable.presence_offline, 0);
        return tv;
    }

    public Object convertViewToDrawable(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        return new BitmapDrawable(view.getDrawingCache());

    }
/*
    @Override
    protected void onSelectionChanged(int selStart, int selEnd){
        CharSequence text = getText();
        if (text != null && availableCursorSpots != null) {

            if (selEnd > availableCursorSpots.get(availableCursorSpots.size()-1)){
                setSelection(selStart, selEnd);
                return;
            }

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
*/
    public List<Integer> getAvailableCursorSpots() {
        return availableCursorSpots;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
/*
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new SearchInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }

    private class SearchInputConnection extends InputConnectionWrapper {

        public SearchInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                System.out.println("MADE IT");
                System.out.println(availableCursorSpots.size());
                if (availableCursorSpots.size() > 1) {
                    for (int i = 1; i < availableCursorSpots.size(); i++) {
                        System.out.println(getSelectionStart());
                        System.out.println(getSelectionEnd());
                        System.out.println(availableCursorSpots.get(i));
                        if (getSelectionStart() == getSelectionEnd() && getSelectionEnd() == availableCursorSpots.get(i)) {
                            System.out.println("AYYYYYYYYY");
                            accounts.remove(i-1);
                            updateObjectsAndUpdateText();
                        }
                    }
                }
                // Un-comment if you wish to cancel the backspace:
                // return false;
            }
            return super.sendKeyEvent(event);
        }


        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
            if (beforeLength == 1 && afterLength == 0) {
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }

    }
    */
}
