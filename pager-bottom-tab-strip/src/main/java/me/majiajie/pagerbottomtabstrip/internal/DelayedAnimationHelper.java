package me.majiajie.pagerbottomtabstrip.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.transition.TransitionValues;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;


class DelayedAnimationHelper
{
    private static final long ACTIVE_ANIMATION_DURATION_MS = 115L;

    private final TransitionSet mSet;

    DelayedAnimationHelper() {
        mSet = new AutoTransition();
        mSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        mSet.setDuration(ACTIVE_ANIMATION_DURATION_MS);
        mSet.setInterpolator(new FastOutSlowInInterpolator());
        TextScale textScale = new TextScale();
        mSet.addTransition(textScale);
    }

    void beginDelayedTransition(ViewGroup view) {
        TransitionManager.beginDelayedTransition(view, mSet);
    }

    private class TextScale extends Transition {
        private static final String PROPNAME_SCALE = "android:textscale:scale";

        @Override
        public void captureStartValues(TransitionValues transitionValues) {
            captureValues(transitionValues);
        }

        @Override
        public void captureEndValues(TransitionValues transitionValues) {
            captureValues(transitionValues);
        }

        private void captureValues(TransitionValues transitionValues) {
            if (transitionValues.view instanceof TextView) {
                TextView textview = (TextView) transitionValues.view;
                transitionValues.values.put(PROPNAME_SCALE, textview.getScaleX());
            }
        }

        @Override
        public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
                                       TransitionValues endValues) {
            if (startValues == null || endValues == null || !(startValues.view instanceof TextView)
                    || !(endValues.view instanceof TextView)) {
                return null;
            }
            final TextView view = (TextView) endValues.view;
            Map<String, Object> startVals = startValues.values;
            Map<String, Object> endVals = endValues.values;
            final float startSize = startVals.get(PROPNAME_SCALE) != null ? (float) startVals.get(
                    PROPNAME_SCALE) : 1f;
            final float endSize = endVals.get(PROPNAME_SCALE) != null ? (float) endVals.get(
                    PROPNAME_SCALE) : 1f;
            if (startSize == endSize) {
                return null;
            }

            ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedValue = (float) valueAnimator.getAnimatedValue();
                    view.setScaleX(animatedValue);
                    view.setScaleY(animatedValue);
                }
            });
            return animator;
        }
    }
}
