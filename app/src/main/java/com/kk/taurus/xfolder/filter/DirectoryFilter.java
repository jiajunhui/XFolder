package com.kk.taurus.xfolder.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Taurus on 2017/5/25.
 */

public class DirectoryFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        if(pathname.isDirectory())
            return true;
        return false;
    }
}
