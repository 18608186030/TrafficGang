package com.stjy.baselib.wigiet.refreshheader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ProgressBar;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;

/**
 * @author daifalin
 * @date 2019-05-21 16:16
 * @ClassName CustomHeader
 * @Description
 */
public class CustomHeader extends InternalAbstract implements RefreshHeader {


    public CustomHeader(Context context) {
        this(context, null);
    }

    public CustomHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER);
        addView(new ProgressBar(context));
    }
}
