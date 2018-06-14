package com.kanoksilp.javamediaplayerremote;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_disconnect) {
            if (mcc != null) {
                mcc.close();
                mcc = null;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private MediaControlClient mcc;
    private static final String MCC_THREAD_NAME = "MediaControlClientThread";

    public void onButtonClick(View view) {
        if (mcc != null) {
            mcc.close();
            mcc = null;
        }

        Log.i(LOG_TAG, "Will connect to server.");

        String ip = ((EditText) findViewById(R.id.tbxIP)).getText().toString();
        int port = Integer.parseInt(
                ((EditText) findViewById(R.id.tbxPort)).getText().toString());

        mcc = new MediaControlClient(MCC_THREAD_NAME,ip,port);
        mcc.start();
    }

    public void sendMessage(View view) {
        mcc.send(view.getTag().toString());
    }

}
