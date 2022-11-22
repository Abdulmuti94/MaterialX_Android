package com.material.components.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class BackdropViewAnimation {

    private static String TAG = BackdropViewAnimation.class.getSimpleName();

    private final AnimatorSet animatorSet = new AnimatorSet();

    private Context context;
    private View backdrop, sheet;
    private int height;
    private boolean backdropShown;
    private Integer openIcon, closeIcon;
    private Integer colorIcon;
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private View buttonView;
    private DisplayMetrics displayMetrics;

    private StateListener stateListener;

    public interface StateListener {
        void onOpen(ObjectAnimator animator);

        void onClose(ObjectAnimator animator);
    }

    public void addStateListener(final StateListener stateListener) {
        this.stateListener = stateListener;
    }

    public BackdropViewAnimation(Context context, View backdrop, View sheet) {
        this.context = context;
        this.backdrop = backdrop;
        this.sheet = sheet;

        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
    }

    public BackdropViewAnimation(Context context, View backdrop, View sheet,
                                 @Nullable @DrawableRes Integer openIcon,
                                 @Nullable @DrawableRes Integer closeIcon,
                                 @Nullable @ColorRes Integer colorIcon
    ) {
        this.context = context;
        this.backdrop = backdrop;
        this.sheet = sheet;
        this.openIcon = openIcon;
        this.closeIcon = closeIcon;
        this.colorIcon = colorIcon;

        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
    }

    public ObjectAnimator toggle() {
        return toggle(null);
    }

    public ObjectAnimator toggle(@Nullable View buttonView) {
        backdropShown = !backdropShown;
        if (buttonView != null) this.buttonView = buttonView;
        if (this.buttonView != null) {
            updateIcon(this.buttonView);
        }

        // Cancel the existing animations
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();


        int positionY = backdrop.getBottom() - sheet.getTop();
        int backdropBottom = backdrop.getBottom() + sheet.getTop();
        if (backdropBottom > height && getActionBarSize() > 0) {
            positionY = height - sheet.getTop() - (getActionBarSize() * 4 / 3);
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(sheet, "translationY", backdropShown ? positionY : 0);
        animator.setDuration(500);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();

        if (stateListener != null) {
            if (backdropShown) {
                stateListener.onOpen(animator);
            } else {
                stateListener.onClose(animator);
            }
        }

        return animator;
    }

    private int getActionBarSize() {
        TypedValue tv = new TypedValue();
        int actionBarHeight = -1;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, displayMetrics);
        }
        return actionBarHeight;
    }

    public ObjectAnimator open() {
        return open(null);
    }

    public ObjectAnimator open(@Nullable View buttonView) {
        backdropShown = false;
        return toggle(buttonView);
    }

    public ObjectAnimator close() {
        backdropShown = true;
        return toggle(buttonView);
    }

    private void updateIcon(View view) {
        if (openIcon != null && closeIcon != null) {
            if ((openIcon != null && closeIcon != null) && !(view instanceof ImageView)) {
                Log.e(TAG, "updateIcon() must be called on an ImageView/ImageButton");
                return;
            }
            ImageView icon = (ImageView) view;
            icon.setImageDrawable(ContextCompat.getDrawable(context, backdropShown ? closeIcon : openIcon));
            if (closeIcon != null) {
                icon.setColorFilter(ContextCompat.getColor(context, colorIcon), android.graphics.PorterDuff.Mode.SRC_IN);
            }
        }
    }

    public void setButtonView(View buttonView) {
        this.buttonView = buttonView;
    }

    public View getButtonView() {
        return buttonView;
    }

    public boolean isBackdropShown() {
        return backdropShown;
    }

}
