package ng.com.jcedar.jambprep.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.ui.BaseActivity;

/**
 * Created by oluwafemi.bamisaye on 2/3/2016.
 */
public class CombinationActivity extends BaseActivity implements View.OnClickListener {

    Button btProceed;
    CheckBox chkEng, chkAcct, chkBio, chkChem, chkCrk, chkCom, chkEco, chkGeo, chkGov, chkLit, chkMath, chkPhy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);

        //initialize variables

        chkAcct = (CheckBox) findViewById(R.id.accounting);
        chkBio = (CheckBox) findViewById(R.id.biology);
        chkChem = (CheckBox) findViewById(R.id.chemistry);
        chkCom = (CheckBox) findViewById(R.id.comm);
        chkCrk = (CheckBox) findViewById(R.id.crk);
        chkEco = (CheckBox) findViewById(R.id.economics);
        chkEng = (CheckBox) findViewById(R.id.english);
        chkGeo = (CheckBox) findViewById(R.id.geography);
        chkGov = (CheckBox) findViewById(R.id.govt);
        chkLit = (CheckBox) findViewById(R.id.literature);
        chkMath = (CheckBox) findViewById(R.id.maths);
        chkPhy = (CheckBox) findViewById(R.id.physics);
        btProceed = (Button) findViewById(R.id.btProceed);


        btProceed.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}
