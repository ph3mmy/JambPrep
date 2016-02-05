package ng.com.jcedar.jambprep.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ng.com.jcedar.jambprep.Communicator;
import ng.com.jcedar.jambprep.R;

/**
 * Created by oluwafemi.bamisaye on 2/4/2016.
 */
public class FragmentA extends Fragment implements View.OnClickListener{

    Button button;
    int counter = 0;
    Communicator com;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = (Button) getActivity().findViewById(R.id.butA);

        com = (Communicator) getActivity();

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        counter++;
        com.respond("You have clicked the button" + counter + "times");
    }
}
