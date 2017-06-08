package puzzleleaf.tistory.com.teamclinic.processing;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cmtyx on 2017-06-07.
 */



public class StageData {

    static public int stageNum = 0;
    static public int treeNum = 3;

    Context context;
    public StageData(Context context)
    {
        this.context = context;
    }

    public void saveStage(int b)
    {
        SharedPreferences prefs = context.getSharedPreferences("stage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("stage", b);
        editor.commit();
    }

    public void getStage(){
        SharedPreferences prefs = context.getSharedPreferences("stage", Context.MODE_PRIVATE);
        stageNum = prefs.getInt("stage",0); //원래는 null
    }

    public void saveTree(int b)
    {
        SharedPreferences prefs = context.getSharedPreferences("tree", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("tree", b);
        editor.commit();
    }

    public void getTree(){
        SharedPreferences prefs = context.getSharedPreferences("tree", Context.MODE_PRIVATE);
        treeNum = prefs.getInt("tree",0); //원래는 null
    }
}
