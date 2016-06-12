package com.lasss.root.bridgetogrid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button wikipedia, finance, weather, news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wikipedia = (Button) findViewById(R.id.wikipediaButton);
        finance = (Button) findViewById(R.id.financeButton);
        weather = (Button) findViewById(R.id.weatherButton);
        news = (Button) findViewById(R.id.newsButton);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions();
        }
    }

    public void searchWikipedia(View view) {
        Intent wikiIntent = new Intent(this, WikipediaActivity.class);
        startActivity(wikiIntent);
    }

    public void searchFinance(View view) {

    }

    public void searchWeather(View view) {

    }

    public void searchNews(View view) {

    }

    private void requestPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasReceieveSmsPermission = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            int hasSmsReadPermission = checkSelfPermission(Manifest.permission.READ_SMS);
            int hasSmsSendPermission = checkSelfPermission(Manifest.permission.SEND_SMS);
            List<String> permissions = new ArrayList<>();

            if( hasReceieveSmsPermission != PackageManager.PERMISSION_GRANTED ) {
                permissions.add( Manifest.permission.RECEIVE_SMS );
            }

            if( hasSmsReadPermission != PackageManager.PERMISSION_GRANTED ) {
                permissions.add( Manifest.permission.READ_SMS );
            }

            if( hasSmsSendPermission != PackageManager.PERMISSION_GRANTED ) {
                permissions.add( Manifest.permission.SEND_SMS );
            }

            if( !permissions.isEmpty() ) {
                //private static final int REQUEST_SMS_HANDLE_PERMISSIONS = 1;
                requestPermissions( permissions.toArray( new String[permissions.size()] ), 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch ( requestCode ) {
            case 1: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] == PackageManager.PERMISSION_GRANTED ) {
                        Log.d( "Permissions", "Permission Granted: " + permissions[i] );
                    } else if( grantResults[i] == PackageManager.PERMISSION_DENIED ) {
                        Log.d( "Permissions", "Permission Denied: " + permissions[i] );
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}
