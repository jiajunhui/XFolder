package com.kk.taurus.xfolder.filter;

import com.kk.taurus.filebase.engine.FileEngine;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Taurus on 2017/5/24.
 */

public class TextFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return false;
        String extension = FileEngine.getExtFromFilename(file.getName());
        if("txt".equalsIgnoreCase(extension) || "log".equalsIgnoreCase(extension))
            return true;
        return false;
    }
}
