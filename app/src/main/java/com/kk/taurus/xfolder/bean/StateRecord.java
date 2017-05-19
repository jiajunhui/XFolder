package com.kk.taurus.xfolder.bean;

import java.io.Serializable;

/**
 * Created by Taurus on 2017/5/12.
 */

public class StateRecord implements Serializable {
    private int focusPosition;
    private int scrollToPosition;
    private int offset;

    public int getFocusPosition() {
        return focusPosition;
    }

    public void setFocusPosition(int focusPosition) {
        this.focusPosition = focusPosition;
    }

    public int getScrollToPosition() {
        return scrollToPosition;
    }

    public void setScrollToPosition(int scrollToPosition) {
        this.scrollToPosition = scrollToPosition;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
