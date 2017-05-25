package com.kk.taurus.xfolder.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Taurus on 2017/5/25.
 */

public class NonDirectoryFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        if(pathname.isFile())
            return true;
        return false;
    }
}
