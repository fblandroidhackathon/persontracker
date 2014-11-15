package org.opencv.samples.facedetect.interfaces;

import android.content.Context;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

/**
 * Facedetector
 */
public interface IFaceDetector {

    public void onResume(final Context pContext, final IOnLoaded pIOnLoaded);

    public Rect[] Detectfaces(Mat pGray);

    public void start();

    public void stop();

    public void setMinFaceSize(int mAbsoluteFaceSize);

}
