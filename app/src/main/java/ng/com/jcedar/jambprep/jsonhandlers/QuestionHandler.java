package ng.com.jcedar.jambprep.jsonhandlers;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ng.com.jcedar.jambprep.helper.FormatUtils;
import ng.com.jcedar.jambprep.helper.Lists;
import ng.com.jcedar.jambprep.model.Question;
import ng.com.jcedar.jambprep.provider.DataContract;

/**
 * Created by Seyi.Afolayan on 2/19/2016.
 */
public class QuestionHandler extends JsonHandler {
    String TAG = QuestionHandler.class.getSimpleName();
    int count;

    public QuestionHandler(Context context) {
        super(context);
    }

    @Override
    public ArrayList<ContentProviderOperation> parse(String json, int where) throws IOException {

        Log.e(TAG+" "+where, TextUtils.isEmpty(json) ? "Empty json" : "Json is not empty");

        final ArrayList<ContentProviderOperation> batch = Lists.newArrayList();
        JSONArray array;

        try {
            array = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
        Type listType = new TypeToken<List<Question>>(){}.getType();

        ArrayList<Question> questions = gson.fromJson(array.toString(), listType);
        count  = questions.size();

        for (Question question: questions){
            parseQuestionData(question, batch, where);
        }

        return batch;
    }

    private void parseQuestionData(Question question,
                                   ArrayList<ContentProviderOperation> batch,
                                   int where) {
        Uri uri;
        switch ( where ){
            case 1: //English
                uri = DataContract.addCallerIsSyncAdapterParameter(
                DataContract.EnglishLanguage.CONTENT_URI);
                break;
            case 2: //Subject 1
                uri = DataContract.addCallerIsSyncAdapterParameter(
                DataContract.Subject1.CONTENT_URI);
                break;
            case 3: //Subject 2
                uri = DataContract.addCallerIsSyncAdapterParameter(
                DataContract.Subject2.CONTENT_URI);
                break;
            case 4: //Subject 3
                uri = DataContract.addCallerIsSyncAdapterParameter(
                DataContract.Subject3.CONTENT_URI);
                break;
            default:
                uri =  null;
        }


        ContentProviderOperation.Builder builder = ContentProviderOperation
                .newInsert(uri)
                .withValue(DataContract.QuestionColumns.ID, question.getId())
                .withValue(DataContract.QuestionColumns.SUBJECT_ID, question.getSubjectId())
                .withValue(DataContract.QuestionColumns.EXAM_YEAR, question.getExamYear())
                .withValue(DataContract.QuestionColumns.REF_TEXT_ID, question.getRefText())
                .withValue(DataContract.QuestionColumns.QUESTION, question.getQuestions())
                .withValue(DataContract.QuestionColumns.OPTION_A, question.getOptionA())
                .withValue(DataContract.QuestionColumns.OPTION_B, question.getOptionB())
                .withValue(DataContract.QuestionColumns.OPTION_C, question.getOptionC())
                .withValue(DataContract.QuestionColumns.OPTION_D, question.getOptionD())
                .withValue(DataContract.QuestionColumns.OPTION_E, question.getOptionE())
                .withValue(DataContract.QuestionColumns.ANSWER, question.getAnswer())
                .withValue(DataContract.QuestionColumns.EXPLANATION, question.getExplanation())
                .withValue(DataContract.QuestionColumns.PHOTO, question.getPhoto())
                .withValue(DataContract.QuestionColumns.ANSWER_PHOTO, question.getAnswerPhoto())
                .withValue(DataContract.SyncColumns.UPDATED, FormatUtils.getCurrentDate());

        batch.add( builder.build() );
    }
}
