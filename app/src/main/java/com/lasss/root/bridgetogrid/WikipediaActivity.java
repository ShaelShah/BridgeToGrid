package com.lasss.root.bridgetogrid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WikipediaActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText searchBar;
    private TextView resultTextView;

    private Boolean receiverIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wikipedia);

        searchBar = (EditText) findViewById(R.id.searchBar);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);

        if(!receiverIsRegistered) {
            registerReceiver(messageReceiver, filter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!receiverIsRegistered) {
            registerReceiver();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiverIsRegistered) {
            unregisterReceiver(messageReceiver);
            receiverIsRegistered = false;
        }
    }

    public void performSearch(View view) {
        String searchTerm = searchBar.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("6474928225", null, "WIKI-SEARCH: Drake (rapper)" + searchTerm, null, null);
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<String> fullMessage = new ArrayList<>();
            final Bundle bundle = intent.getExtras();

            try {
                if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    Log.d(TAG, "In OnRecieve");
                    for (int i = 0; i < pdusObj.length; i++) {
                        SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                        fullMessage.add(msgs[i].getDisplayMessageBody());
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception smsReceive");
            }

            for (String s : fullMessage) {
                resultTextView.setText(resultTextView.getText() + " " + s);
            }
        }
    };

    private void registerReceiver() {
        registerReceiver(messageReceiver, new IntentFilter());
        receiverIsRegistered = true;
        Log.d(TAG, "Registered the reciever");
    }
}
