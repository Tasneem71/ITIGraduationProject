package com.example.domain.core.subFeature

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewAnimation {
    fun animate(holder: RecyclerView.ViewHolder, goesDown: Boolean) {
        val animatorSet = AnimatorSet()
        val animatorTranslateY: ObjectAnimator = ObjectAnimator
            .ofFloat(
                holder.itemView,
                "translationY",
                if (goesDown)
                    100f
                else
                    -100f
                , 0f
            )
        animatorTranslateY.duration = 1000
        animatorSet.playTogether(animatorTranslateY)
        animatorSet.start()
    }

    /**
     * Converting dp to pixel
     */

    fun dpToPx(dp: Int): Int {
        val r: Resources = Resources.getSystem()
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }
}
