package ng.com.jcedar.jambprep.model;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public class Question {

    @SerializedName(value="id")
    private String Id;

    @SerializedName(value="subject_id")
        private String subjectId;

    @SerializedName(value="exam_year")
        private String examYear;

    @SerializedName(value="ref_q_text")
            private String refText;

    @SerializedName(value="questions")
        private String questions;

    @SerializedName(value="option_a")
        private String optionA;

    @SerializedName(value="option_b")
        private String optionB;

    @SerializedName(value="option_c")
        private String optionC;

    @SerializedName(value="option_d")
        private String optionD;

    @SerializedName(value="option_e")
            private String optionE;

    @SerializedName(value="answer")
            private String answer;

    @SerializedName(value="explanation")
            private String explanation;

    @SerializedName(value="photo")
            private String photo;

    @SerializedName(value="answer_photo")
    private String answerPhoto;

    public String getId() {
        return Id;
    }

    public String getRefText() {
        return refText;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getExamYear() {
        return examYear;
    }

    public String getQuestions() {
        return questions;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public String getAnswer() {
        return answer;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getPhoto() {
        return photo;
    }

    public String getAnswerPhoto() {
        return answerPhoto;
    }
}
