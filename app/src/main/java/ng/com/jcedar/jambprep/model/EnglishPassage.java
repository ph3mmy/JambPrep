package ng.com.jcedar.jambprep.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public class EnglishPassage {

    @SerializedName(value="id")
    private String Id;

    @SerializedName(value="ref_text")
    private String refText;

    @SerializedName(value="q_count")
    private String count;

    public String getId() {
        return Id;
    }

    public String getRefText() {
        return refText;
    }

    public String getCount() {
        return count;
    }
}
