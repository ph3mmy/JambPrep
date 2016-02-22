package ng.com.jcedar.jambprep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.helper.PrefUtils;
import ng.com.jcedar.jambprep.ui.BaseActivity;
import ng.com.jcedar.jambprep.ui.NavigationDrawerFragment;

/**
 * Created by oluwafemi.bamisaye on 1/15/2016.
 */
public class ProfileActivity extends BaseActivity {

    private static boolean quit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if ( !PrefUtils.isDataBootstrapDone(this)){
            startActivity( new Intent( this, CombinationActivity.class));
        }
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NavigationDrawerFragment.MenuConstants.NAVDRAWER_ITEM_PROFILE;
    }
}
