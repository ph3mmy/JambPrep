package ng.com.jcedar.jambprep.ui.activity;

import android.os.Bundle;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.ui.BaseActivity;
import ng.com.jcedar.jambprep.ui.NavigationDrawerFragment;

/**
 * Created by oluwafemi.bamisaye on 1/22/2016.
 */
public class TestActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_a_test);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NavigationDrawerFragment.MenuConstants.NAVDRAWER_ITEM_TEST;
    }
}
