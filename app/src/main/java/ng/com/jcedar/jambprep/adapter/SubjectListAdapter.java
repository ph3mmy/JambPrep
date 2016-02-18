package ng.com.jcedar.jambprep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import ng.com.jcedar.jambprep.R;
import ng.com.jcedar.jambprep.model.Subject;
import ng.com.jcedar.jambprep.ui.activity.CombinationActivity;

/**
 * Created by oluwafemi.bamisaye on 2/13/2016.
 */
public class SubjectListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater inflater;
    ArrayList<Subject> subject;

    public SubjectListAdapter(Context context, ArrayList<Subject> subjects){
        ctx = context;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        subject = subjects;
    }


    @Override
    public int getCount() {
        return subject.size();
    }

    @Override
    public Object getItem(int position) {
        return subject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.row_item_combination, parent, false);
        }

        Subject sub = getSubject(position);

        ((TextView) view.findViewById(R.id.subName)).setText(sub.getName());
        CheckBox chkBox = (CheckBox) view.findViewById(R.id.chkBox);
        chkBox.setOnCheckedChangeListener(myCheckChangList);
        chkBox.setTag(position);
        chkBox.setChecked(sub.getBox());

        if ( sub.getName().startsWith("English")) {
            chkBox.setChecked(true);
            chkBox.setClickable(false);
            chkBox.setSelected(true);
        }
        return view;

    }

    Subject getSubject(int position) {
        return ((Subject) getItem(position));
    }

    public ArrayList<Subject> getBox() {
        ArrayList<Subject> box = new ArrayList<Subject>();
        for (Subject p : subject) {
            if (p.getBox())
                box.add(p);
        }
        return box;
    }

    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getSubject((Integer) buttonView.getTag()).setBox( isChecked );
            if (getBox().size() == 4){

            }
        }
    };

}
