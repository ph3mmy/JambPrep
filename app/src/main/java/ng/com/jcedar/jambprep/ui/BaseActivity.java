package ng.com.jcedar.jambprep.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.ui.activity.ProfileActivity;
import ng.com.jcedar.jambprep.ui.view.MultiSwipeRefreshLayout;
//import com.splasherstech.TripsMobile.ui.activity.DashboardActivity;
//import com.splasherstech.TripsMobile.ui.view.MultiSwipeRefreshLayout;

import java.util.ArrayList;


public abstract class BaseActivity extends ActionBarActivity implements
        MultiSwipeRefreshLayout.CanChildScrollUpCallback {

    protected static final String NAIRA = "\u20A6";
    protected static final String UNIT = "KWh";
    private static final String TAG = BaseActivity.class.getSimpleName();
    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;
    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
    private static final int MAIN_CONTENT_FADEIN_DURATION = 5;
    // SwipeRefreshLayout allows the user to swipe the screen down to trigger a manual refresh
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewGroup mDrawerItemsListContainer;
    // Primary toolbar and drawer toggle
    private Toolbar mActionBarToolbar;
    private boolean mManualSyncRequest;
    // private SyncStatusObserver mSyncStatusObserver = new SyncStatusObserver() {
    //@Override
        /*public void onStatusChanged(int which) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String accountName = AccountUtils.getChosenAccountName(BaseActivity.this);
                    if (TextUtils.isEmpty(accountName)) {
                        onRefreshingStateChanged(false);
                        mManualSyncRequest = false;
                        return;
                    }

                    Account account = new Account(accountName, getResources().getString(R.string.power_account_type));
                    boolean syncActive = ContentResolver.isSyncActive(
                            account, DataContract.CONTENT_AUTHORITY);
                    boolean syncPending = ContentResolver.isSyncPending(
                            account, DataContract.CONTENT_AUTHORITY);
                    if (!syncActive && !syncPending) {
                        mManualSyncRequest = false;
                    }
                    onRefreshingStateChanged(syncActive || (mManualSyncRequest && syncPending));
                }
            });
        }*/
    // };
    // handle to our sync observer (that notifies us about changes in our sync state)
    private Object mSyncObserverHandle;
    // asynctask that performs GCM registration in the background
    private AsyncTask<Void, Void, Void> mGCMRegisterTask;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    // Navigation drawer
    private DrawerLayout mDrawerLayout;
    private CharSequence mTitle;
    private Handler mHandler;

    /**
     * Converts an intent into a {@link Bundle} suitable for use as fragment arguments.
     */
    protected static Bundle intentToFragmentArguments(Intent intent) {
        Bundle arguments = new Bundle();
        if (intent == null) {
            return arguments;
        }

        final Uri data = intent.getData();
        if (data != null) {
            arguments.putParcelable("_uri", data);
        }

        final Bundle extras = intent.getExtras();
        if (extras != null) {
            arguments.putAll(intent.getExtras());
        }

        return arguments;
    }

    /**
     * Converts a fragment arguments bundle into an intent.
     */
    public static Intent fragmentArgumentsToIntent(Bundle arguments) {
        Intent intent = new Intent();
        if (arguments == null) {
            return intent;
        }

        final Uri data = arguments.getParcelable("_uri");
        if (data != null) {
            intent.setData(data);
        }

        intent.putExtras(arguments);
        intent.removeExtra("_uri");
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (!AccountUtils.isAuthenticated(this) || !AccountUtils.isSetupDone(this)) {
            Log.d(TAG, "exiting:"
                    + " isAuthenticated=" + AccountUtils.isAuthenticated(this)
                    + " isSetupDone=" + AccountUtils.isSetupDone(this));
            AccountUtils.startAuthenticationFlow(this, getIntent());
            finish();
        }*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mHandler = new Handler();

        // set orientation for screen sizes
        if (getResources().getConfiguration().smallestScreenWidthDp <= 600) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (!isFinishing()) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Long> convertToArray(Bundle bundle, String key) {
        ArrayList<Long> items = new ArrayList<Long>();
        int n = 0;
        while (true) {
            long item = bundle.getLong(key + Integer.toString(n++), -1);
            // terminate once we cant retrieve items
            if (item == -1) break;
            items.add(item);
        }
        return items;
    }

    private void trySetupSwipeRefresh() {
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//        if (mSwipeRefreshLayout != null) {
//            mSwipeRefreshLayout.setColorSchemeResources(
//                    R.color.refresh_progress_1,
//                    R.color.refresh_progress_2,
//                    R.color.refresh_progress_3,
//                    R.color.refresh_progress_1);
//            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    requestDataRefresh();
//                }
//            });
//
//            if (mSwipeRefreshLayout instanceof MultiSwipeRefreshLayout) {
//                MultiSwipeRefreshLayout mswrl = (MultiSwipeRefreshLayout) mSwipeRefreshLayout;
//                mswrl.setCanChildScrollUpCallback(this);
//            }
//        }
    }

    protected void onRefreshingStateChanged(boolean refreshing) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    protected void enableDisableSwipeRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }

    @Override
    public boolean canSwipeRefreshChildScrollUp() {
        return true;
    }

    protected void requestDataRefresh() {
    }

    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * of BaseActivity override this to indicate what nav drawer item corresponds to them
     * Return NAVDRAWER_ITEM_INVALID to mean that this Activity should not have a Nav Drawer.
     */
    protected int getSelfNavDrawerItem() {

        return NavigationDrawerFragment.MenuConstants.NAVDRAWER_ITEM_INVALID;
    }

    private void trySetupNavDrawer() {

        int selfItem = getSelfNavDrawerItem();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        mDrawerLayout.setStatusBarBackgroundColor(
                getResources().getColor(R.color.theme_primary_dark));

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        if (selfItem == NavigationDrawerFragment.MenuConstants.NAVDRAWER_ITEM_INVALID) {
            // do not show a nav drawer
            if (mNavigationDrawerFragment != null) {
                // ((ViewGroup) mNavigationDrawerFragment.getParent()).removeView(navDrawer);
            }
            mDrawerLayout = null;
            return;
        }

        if (mNavigationDrawerFragment != null) {
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
            mNavigationDrawerFragment.setAccount();
            // what nav drawer item should be selected?
            mNavigationDrawerFragment.setSelection(getSelfNavDrawerItem());

        }

        if (mActionBarToolbar != null) {
            mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDrawerLayout.openDrawer(Gravity.START);
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* //clear invoice count and notifications
        PrefUtils.setInvoices(this, 0);
        PrefUtils.setPayments(this, 0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancelAll();
        */

        // Watch for sync state changes
        /*mSyncStatusObserver.onStatusChanged(0);
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING |
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        mSyncObserverHandle = ContentResolver.addStatusChangeListener(mask, mSyncStatusObserver);
*/
        // reset selection to correct item when fragment becomes visible again
        if (mNavigationDrawerFragment != null) {
            mNavigationDrawerFragment.setSelection(getSelfNavDrawerItem());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        //EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSyncObserverHandle != null) {
            ContentResolver.removeStatusChangeListener(mSyncObserverHandle);
            mSyncObserverHandle = null;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        trySetupSwipeRefresh();
        trySetupNavDrawer();
/*        View mainContent = findViewById(R.id.container);
        if (mainContent != null) {
            mainContent.setAlpha(0);
            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
        }
        else {
            Log.w(TAG, "No view with ID main_content to fade in.");
        }*/
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null)
            super.setTitle(mTitle); // was getting a stackover flow error from recursion
        // then decided to call super
    }


    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed(); // is this really needed
            if (getSelfNavDrawerItem() == NavigationDrawerFragment.MenuConstants.NAVDRAWER_ITEM_INVALID) {
                super.onBackPressed();
            } else if (getSelfNavDrawerItem() == NavigationDrawerFragment.MenuConstants.NAVDRAWER_ITEM_PROFILE) {
                finish();
            } else {
                startActivity(new Intent(this, ProfileActivity.class));
                finish();
            }
        }
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }


    //  Log.d(TAG, "Device's registration id is " + regId);


    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public interface TRIP_STATUS {
        String CORPORATE = "corporate";
        String PERSONAL = "personal";
    }

    public interface DIRECTION {
        String TO = "to";
        String FROM = "from";
    }



}
