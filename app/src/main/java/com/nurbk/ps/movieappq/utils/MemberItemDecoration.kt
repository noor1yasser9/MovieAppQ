package com.nurbk.ps.movieappq.utils


import android.graphics.Rect;
import android.util.Log
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Px

import androidx.recyclerview.widget.RecyclerView;


class MemberItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        // only for the last one
        if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                130f,
                parent.context.resources.displayMetrics
            ).toInt()
            outRect.bottom = px /* set your margin here */
        }
    }
}