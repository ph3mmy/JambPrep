package ng.com.jcedar.jambprep.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public class Subject {

    @SerializedName(value="1")
    private ArrayList<Question> english;

    @SerializedName(value="2")
    private ArrayList<Question> subject2;

    @SerializedName(value="3")
    private ArrayList<Question> subject3;

    @SerializedName(value="4")
    private ArrayList<Question> subject4;
}
