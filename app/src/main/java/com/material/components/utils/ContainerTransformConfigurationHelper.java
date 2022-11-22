/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.material.components.utils;

import android.annotation.TargetApi;
import android.os.Build.VERSION_CODES;
import android.util.SparseIntArray;
import android.view.animation.Interpolator;

import androidx.annotation.RequiresApi;

import com.google.android.material.transition.MaterialArcMotion;
import com.google.android.material.transition.MaterialContainerTransform;
import com.material.components.R;


@TargetApi(VERSION_CODES.ICE_CREAM_SANDWICH)
public class ContainerTransformConfigurationHelper {

    private static final long NO_DURATION = 500;

    private boolean arcMotionEnabled;
    private long enterDuration;
    private long returnDuration;
    private Interpolator interpolator;
    private boolean drawDebugEnabled;

    private static final SparseIntArray FADE_MODE_MAP = new SparseIntArray();

    public ContainerTransformConfigurationHelper() {
        setUpDefaultValues();
    }

    /**
     * Set up the androidx transition according to the config helper's parameters.
     */
    public void configure(MaterialContainerTransform transform, boolean entering) {
        long duration = entering ? getEnterDuration() : getReturnDuration();
        if (duration != NO_DURATION) {
            transform.setDuration(duration);
        }
        transform.setInterpolator(getInterpolator());
        if (isArcMotionEnabled()) {
            transform.setPathMotion(new MaterialArcMotion());
        }
        transform.setFadeMode(entering ? MaterialContainerTransform.FADE_MODE_IN
                : MaterialContainerTransform.FADE_MODE_OUT);
        transform.setDrawDebugEnabled(isDrawDebugEnabled());
    }

    /** Set up the platform transition according to the config helper's parameters. */
    @RequiresApi(VERSION_CODES.LOLLIPOP)
    public void configure(com.google.android.material.transition.platform.MaterialContainerTransform transform, boolean entering) {
        long duration = entering ? getEnterDuration() : getReturnDuration();
        if (duration != NO_DURATION) {
            transform.setDuration(duration);
        }
        transform.setInterpolator(getInterpolator());
        if (isArcMotionEnabled()) {
            transform.setPathMotion(new com.google.android.material.transition.platform.MaterialArcMotion());
        }
        transform.setFadeMode(entering ? com.google.android.material.transition.platform.MaterialContainerTransform.FADE_MODE_IN
                : com.google.android.material.transition.platform.MaterialContainerTransform.FADE_MODE_OUT);
        transform.setDrawDebugEnabled(isDrawDebugEnabled());
    }

    /**
     * Whether or not to a custom container transform should use {@link
     * MaterialArcMotion}.
     */
    boolean isArcMotionEnabled() {
        return arcMotionEnabled;
    }

    /**
     * The enter duration to be used by a custom container transform.
     */
    long getEnterDuration() {
        return enterDuration;
    }

    /**
     * The return duration to be used by a custom container transform.
     */
    long getReturnDuration() {
        return returnDuration;
    }

    /**
     * The interpolator to be used by a custom container transform.
     */
    Interpolator getInterpolator() {
        return interpolator;
    }
    /**
     * Whether or not the custom transform should draw debugging lines.
     */
    boolean isDrawDebugEnabled() {
        return drawDebugEnabled;
    }

    private void setUpDefaultValues() {
        arcMotionEnabled = false;
        enterDuration = NO_DURATION;
        returnDuration = NO_DURATION;
        interpolator = null;
        drawDebugEnabled = false;
    }

}
