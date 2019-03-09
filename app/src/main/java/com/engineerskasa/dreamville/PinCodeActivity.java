package com.engineerskasa.dreamville;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

import io.paperdb.Paper;

public class PinCodeActivity extends AppCompatActivity {

    public static final String TAG = "PinLockView";

    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;

    //pin code
    String save_pin_code = "pin_code";
    String final_pincode = "";
    String save_pin = "";

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            //Toast.makeText(PinCodeActivity.this, pin, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Pin complete: " + pin);
            final_pincode = pin;
            if (final_pincode.equals(save_pin)) {
                Toast.makeText(PinCodeActivity.this, "Pin Matched", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PinCodeActivity.this, PatternActivity.class));
                finish();

            } else {
                Toast.makeText(PinCodeActivity.this, "Pin MisMatched", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
            //Toast.makeText(PinCodeActivity.this,"Pin changed, new length "+ pinLength + " with intermediate pin " + intermediatePin, Toast.LENGTH_SHORT).show();

        }
    };

    private PinLockListener mPinLockListener2 = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            //Toast.makeText(PinCodeActivity.this, pin, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Pin complete: " + pin);
            final_pincode = pin;
            Paper.book().write(save_pin_code, final_pincode);
            Toast.makeText(PinCodeActivity.this, "Pin Saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PinCodeActivity.this, PatternActivity.class));
            finish();
        }

        @Override
        public void onEmpty() {

        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pin_code);

        Paper.init(this);

         save_pin = Paper.book().read(save_pin_code);
        if (save_pin != null && !save_pin.equals("null")) {
            setContentView(R.layout.pin_screen);
            mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
            mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

            mPinLockView.attachIndicatorDots(mIndicatorDots);
            mPinLockView.setPinLockListener(mPinLockListener);

            mPinLockView.setPinLength(4);
            mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

            mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        } else {
            setContentView(R.layout.activity_pin_code);
            mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
            mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

            mPinLockView.attachIndicatorDots(mIndicatorDots);
            mPinLockView.setPinLockListener(mPinLockListener2);

            mPinLockView.setPinLength(4);
            mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

            mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);

        }
    }


}
