package ng.com.jcedar.jambprep.app;

import android.app.Application;

import ng.com.jcedar.jambprep.volley.RequestManager;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public class JambPrep extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    /**
     * Initialize the request manager
     */
    private void init(){
        RequestManager.init(this);
    }
}
