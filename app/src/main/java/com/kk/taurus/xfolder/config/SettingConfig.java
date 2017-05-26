package com.kk.taurus.xfolder.config;

import android.content.Context;

import com.kk.taurus.baseframe.FrameApplication;
import com.kk.taurus.baseframe.manager.SharedPrefer;
import com.kk.taurus.filebase.comparators.DateComparator;
import com.kk.taurus.filebase.comparators.DirectoryFirstComparator;
import com.kk.taurus.filebase.comparators.NameComparator;
import com.kk.taurus.filebase.filefilter.HiddenFileFilter;
import com.kk.taurus.filebase.filefilter.NullFilter;

import java.io.FileFilter;
import java.util.Comparator;

/**
 * Created by Taurus on 2017/5/8.
 */

public class SettingConfig {

    public static final String KEY_IS_SHOW_HIDDEN_FILES = "is_show_hidden_files";
    public static final String KEY_FILES_SORT_TYPE = "sort_type";

    public static final String KEY_SEARCH_IGNORE_CASE = "ignore_case";

    private static final int SORT_TYPE_DIRECTORY_FIRST = 1001;
    private static final int SORT_TYPE_FILE_FIRST = 1002;
    public static final int SORT_TYPE_NAME_A_Z = 1003;
    public static final int SORT_TYPE_NAME_Z_A = 1004;
    public static final int SORT_TYPE_DATE_ASC = 1005;
    public static final int SORT_TYPE_DATE_DSC = 1006;

    public static boolean isShowHiddenFiles(Context context){
        return SharedPrefer.getInstance().getBoolean(context,KEY_IS_SHOW_HIDDEN_FILES,false);
    }

    public static boolean isSearchIgnoreCase(Context context){
        return SharedPrefer.getInstance().getBoolean(context,KEY_SEARCH_IGNORE_CASE,false);
    }

    public static FileFilter getFileFilter(){
        if(isShowHiddenFiles(getContext())){
            return new NullFilter();
        }
        return new HiddenFileFilter();
    }

    private static Context getContext(){
        return FrameApplication.getInstance().getApplicationContext();
    }

    public static int getSortType(Context context){
        return SharedPrefer.getInstance().getInt(context,KEY_FILES_SORT_TYPE,SORT_TYPE_NAME_A_Z);
    }

    public static Comparator[] getComparators(){
        int type = getSortType(getContext());
        Comparator[] comparators = new Comparator[2];
        comparators[1] = new DirectoryFirstComparator(true);
        switch (type){
            case SORT_TYPE_NAME_A_Z:
                comparators[0] = new NameComparator(true);
                break;
            case SORT_TYPE_NAME_Z_A:
                comparators[0] = new NameComparator(false);
                break;
            case SORT_TYPE_DATE_ASC:
                comparators[0] = new DateComparator(true);
                break;
            case SORT_TYPE_DATE_DSC:
                comparators[0] = new DateComparator(false);
                break;

        }
        return comparators;
    }

}
