package ng.com.jcedar.jambprep.ui.view.nav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface NavDrawerItem {

	int getId();
	
	int getLabel();
	
	int getType();

	boolean updateActionBarTitle();

    boolean isCheckable();

    View getView(View convertView, ViewGroup parentView, NavDrawerItem navDrawerItem, LayoutInflater inflater);
}
