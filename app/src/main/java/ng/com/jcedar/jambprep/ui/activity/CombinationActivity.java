package ng.com.jcedar.jambprep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.adapter.SubjectListAdapter;
import ng.com.jcedar.jambprep.helper.AppSettings;
import ng.com.jcedar.jambprep.model.Subject;
import ng.com.jcedar.jambprep.ui.BaseActivity;

/**
 * Created by oluwafemi.bamisaye on 2/3/2016.
 */
public class CombinationActivity extends BaseActivity implements OnCheckedChangeListener{

    private static final String TAG = CombinationActivity.class.getSimpleName();

    LinearLayout linearLayout;
    Button btProceed;
    CheckBox chkEng, chkAcct, chkAgr, chkBio, chkChem, chkCrk, chkCom, chkEco, chkGeo, chkGov, chkLit, chkMath, chkPhy;
    int count = 0, mCount = 0;
    ArrayList<String> checkBoxArrayList = new ArrayList<String>();
    String [] selected;

    ArrayList<Subject> subjects1 = new ArrayList<Subject>();
    SubjectListAdapter boxAdapter;
    String questionUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);


        //initialize variables

  /*      fillData();
        boxAdapter = new SubjectListAdapter(this, subjects1);

        ListView lvMain = (ListView) findViewById(android.R.id.list);
        lvMain.setAdapter(boxAdapter);
    }



    void fillData() {
            subjects1.add(new Subject("English Language ", true, 2));
            subjects1.add(new Subject("Accounting ", false, 10));
            subjects1.add(new Subject("Agricultural Science ", false, 14));
            subjects1.add(new Subject("Biology ", false, 5));
            subjects1.add(new Subject("Chemistry", false, 3));
            subjects1.add(new Subject("Commerce", false, 9));
            subjects1.add(new Subject("Christian Religious Knowledge", false, 12));
            subjects1.add(new Subject("Economics", false, 8));
            subjects1.add(new Subject("Geography", false, 6));
            subjects1.add(new Subject("Government", false, 11));
            subjects1.add(new Subject("Literature-in-English", false, 7));
            subjects1.add(new Subject("Mathematics", false, 1));
            subjects1.add(new Subject("Physics", false, 4));


    }

    public void showResult(View v) {
        String result = "Selected Subjects are :";
        int totalSelected=0;
        ArrayList<Integer> selectedIds = new ArrayList<>();
        Integer[] selected;
        String subjectName = "";

        for (Subject p : boxAdapter.getBox()) {
            if (p.getBox()){
                result += "\n" + p.getName();
                totalSelected++;
                selectedIds.add( p.getId() );
            }
//            subjectName = p.name;
        }
        if (totalSelected == 4) {
            selected = new Integer[4];
            selected = selectedIds.toArray( selected );
            String forUrl="";
            int urlIndex = 1;

            for( int i=0; i < selected.length; i++ ){
                if ( selected[i] == 2) continue;

                if ( i == selected.length - 1)
                    forUrl += "subject"+urlIndex++ +"="+selected[i];
                else
                    forUrl += "subject"+urlIndex++ +"=" +selected[i]+"&";
            }
            Log.e(TAG, forUrl);
            questionUrl = AppSettings.SERVER_URL+"insert_user_subject.php?"+forUrl;
            Intent dash = new Intent(CombinationActivity.this, ProfileActivity.class);
            dash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(dash);
        }else
            Toast.makeText(this, "You can only choose 4 subjects", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, result+"\nSelected: "+totalSelected, Toast.LENGTH_LONG).show();
    }*/

        linearLayout = (LinearLayout) findViewById(R.id.chkLL);
        chkAcct = (CheckBox) findViewById(R.id.accounting);
        chkAgr = (CheckBox) findViewById(R.id.agric);
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


        btProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCount == 4) {
                    int listSize = checkBoxArrayList.size();

                    for (int i = 0; i < listSize; i++) {
                        Log.e("Member name: ", checkBoxArrayList.get(i));
                    }
                }

            }
        });
        chkAcct.setOnCheckedChangeListener(this);
        chkAgr.setOnCheckedChangeListener(this);
        chkBio.setOnCheckedChangeListener(this);
        chkChem.setOnCheckedChangeListener(this);
        chkCom.setOnCheckedChangeListener(this);
        chkCrk.setOnCheckedChangeListener(this);
        chkEco.setOnCheckedChangeListener(this);
        chkGeo.setOnCheckedChangeListener(this);
        chkGov.setOnCheckedChangeListener(this);
        chkLit.setOnCheckedChangeListener(this);
        chkMath.setOnCheckedChangeListener(this);
        chkPhy.setOnCheckedChangeListener(this);
    }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (buttonView == chkEng) {
                chkEng.isChecked();
                checkBoxArrayList.add(chkEng.getText().toString());
                count++;
            }

            if (buttonView == chkAcct) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkAcct.getText().toString());
                    Log.e(TAG, "Checked count after Acct  " + count);

                    Log.e(TAG, "Checked count Name acct  " + chkAcct.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkAcct);
            }


            if (buttonView == chkAgr) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkAgr.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkAgr);
            }
            if (buttonView == chkBio) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkBio.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkBio);
            }
            if (buttonView == chkChem) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkChem.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkChem);
            }
            if (buttonView == chkCom) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkCom.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkCom);
            }
            if (buttonView == chkCrk) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkCrk.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkCrk);
            }
            if (buttonView == chkEco) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkEco.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkEco);
            }
            if (buttonView == chkGeo) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkGeo.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkGeo);
            }
            if (buttonView == chkGov) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkGov.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkGov);
            }
            if (buttonView == chkLit) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkLit.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkLit);
            }
            if (buttonView == chkMath) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkMath.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkMath);
            }


            if (buttonView == chkPhy) {
                if (isChecked){
                    count++;
                    checkBoxArrayList.add(chkPhy.getText().toString());
                }else
                    count--;
                checkBoxArrayList.remove(chkPhy.getText().toString());

                Log.e(TAG, "Checked count after PHY  " + count);
            }

            mCount = count;
            if (mCount == 4){

//            String sd = (String)
//            checkBoxArrayList.addAll()
                int listSize = checkBoxArrayList.size();
                for (int i = 0; i<listSize; i++){
                    Toast.makeText(this, "Selected Subjects " + checkBoxArrayList.size() + "They are : \n" + checkBoxArrayList.get(i), Toast.LENGTH_SHORT).show();
                    Log.e("Member name: ", checkBoxArrayList.get(i) + "\nbreak\n");
                }
//            Toast.makeText(this, "Selected Subjects " + checkBoxArrayList.size() + "They are : " + checkBoxArrayList.get(i), Toast.LENGTH_SHORT).show();
                btProceed.setVisibility(View.VISIBLE);

            }else
                btProceed.setVisibility(View.GONE);
        }
    }



//        chkEng.setOnCheckedChangeListener(this);

/*
        if(verifyChecked()){
            btProceed.setVisibility(View.VISIBLE);
        }
        else{
            btProceed.setVisibility(View.INVISIBLE);
        }


    }


    public boolean verifyChecked(){
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View v = linearLayout.getChildAt(i);
            if(v instanceof CheckBox){
                if(((CheckBox)v).isChecked() ){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btProceed:
                if (checkBoxArrayList.size() > 4){
                    Toast.makeText(this, "You can only choose 4 Subject combinations", Toast.LENGTH_SHORT).show();
                }if (checkBoxArrayList.size() < 4){
                    Toast.makeText(this, "You must choose up to 4 Subject combinations", Toast.LENGTH_SHORT).show();
                }
                else
                Toast.makeText(this, "Subject Combination saved", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Elements of the ArrayList  " + checkBoxArrayList.toString());
        }


    }*/

/*    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (chkEng.isChecked()) count++;{
            chkEng.setChecked(true);
            checkBoxArrayList.add(chkEng);
        }
        count--;

        if (chkAcct.isChecked())count++; {
            chkAcct.setChecked(true);
            checkBoxArrayList.add(chkAcct);
        }if (chkBio.isChecked()) count++;{
            chkBio.setChecked(true);
            checkBoxArrayList.add(chkBio);
        }if (chkChem.isChecked()) count++;{
            chkChem.setChecked(true);
            checkBoxArrayList.add(chkChem);
        }if (chkCom.isChecked())count++; {
            chkCom.setChecked(true);
            checkBoxArrayList.add(chkCom);
        }if (chkCrk.isChecked()) count++;{
            chkCrk.setChecked(true);
            checkBoxArrayList.add(chkCrk);
        }if (chkEco.isChecked()) count++;{
            chkEco.setChecked(true);
            checkBoxArrayList.add(chkEco);
        }if (chkGeo.isChecked())count++; {
            chkGeo.setChecked(true);
            checkBoxArrayList.add(chkGeo);
        }if (chkGov.isChecked())count++; {
            chkGov.setChecked(true);
            checkBoxArrayList.add(chkGov);
        }if (chkLit.isChecked()) count++;{
            chkLit.setChecked(true);
            checkBoxArrayList.add(chkLit);
        }if (chkMath.isChecked()) count++;{
            chkMath.setChecked(true);
            checkBoxArrayList.add(chkMath);
        }if (chkPhy.isChecked()) count++;{
            chkPhy.setChecked(true);
            checkBoxArrayList.add(chkPhy);
        }


        int mCount = count++;

        do
            btProceed.setVisibility(View.VISIBLE);
        while (mCount == 4);

        Log.e(TAG, "counted checkboxes  " + mCount);

    }*/


  /*  @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btProceed:
                if (mCount == 4){*/
/*                    String result = "Selected Subjects are :";
                    int totalSelected=0;
                    ArrayList<Integer> selectedIds = new ArrayList<>();
                    Integer[] selected;
                    String subjectName = "";
*//*
                    for (Subject p : boxAdapter.getBox()) {
                        if (p.getBox()){
                            result += "\n" + p.getName();
                            totalSelected++;
                            selectedIds.add( p.getId() );
                        }
//            subjectName = p.name;
                    }*//*
                    if (totalSelected == 4) {
                        selected = new Integer[4];
                        selected = selectedIds.toArray( selected );
                        String forUrl="";
                        int urlIndex = 1;

                        for( int i=0; i < selected.length; i++ ){
                            if ( selected[i] == 2) continue;

                            if ( i == selected.length - 1)
                                forUrl += "subject"+urlIndex++ +"="+selected[i];
                            else
                                forUrl += "subject"+urlIndex++ +"=" +selected[i]+"&";
                        }
                        Log.e(TAG, forUrl);
                        questionUrl = AppSettings.SERVER_URL+"insert_user_subject.php?"+forUrl;
                        Intent dash = new Intent(CombinationActivity.this, ProfileActivity.class);
                        dash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(dash);
                    }else
                        Toast.makeText(this, "You can only choose 4 subjects", Toast.LENGTH_SHORT).show(); */
     /*               int listSize = checkBoxArrayList.size();

                    for (int i = 0; i<listSize; i++){
                        Log.e("Member name: ", checkBoxArrayList.get(i));
                    }
                }
        }

    }*/


