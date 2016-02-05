package ng.com.jcedar.jambprep.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import ng.com.jcedar.jambprep.Communicator;
import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.fragment.FragmentB;

/**
 * Created by oluwafemi.bamisaye on 2/4/2016.
 */
public class PlayActivity extends Activity implements Communicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    @Override
    public void respond(String data) {
        FragmentManager manager = getFragmentManager();
        FragmentB fb = (FragmentB) manager.findFragmentById(R.id.fragmentB);
        fb.changeText(data);
    }
}
