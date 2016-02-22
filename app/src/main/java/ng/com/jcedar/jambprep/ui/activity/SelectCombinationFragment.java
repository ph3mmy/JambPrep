package ng.com.jcedar.jambprep.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ng.com.jcedar.jambprep.R;

/**
 * Created by oluwafemi.bamisaye on 2/16/2016.
 */
public class SelectCombinationFragment extends DialogFragment {



//    Communicator communicator;
    Snackbar snackbar;
    private View snackView;

    HashMap<String, Integer> selection = new HashMap<String, Integer>();
    TextView final_text;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        communicator= (Communicator) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.linear_layout, null);
        final_text = (TextView)view.findViewById(R.id.final_result);
        final_text.setEnabled(false);

        builder.setView(view);
        builder.setTitle("Combination Dialog");


        setCancelable(false);
        Dialog dialog = builder.create();
        return dialog;
    }

//    interface Communicator{
//        public void requ (String url);
//    }
}

