package ng.com.jcedar.jambprep.jsonhandlers;

import android.content.ContentProviderOperation;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public abstract class JsonHandler {
    protected static Context mContext;

    public JsonHandler(Context context){
        mContext = context;
    }

    public abstract ArrayList<ContentProviderOperation> parse(String json, int where) throws IOException;

}
