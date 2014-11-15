package org.opencv.samples.facedetect;

import android.content.Context;
import android.util.Log;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Face Detector.
 */
public final class FaceDetector {
    private static final String TAG = "FaceDetector";
    private DetectionBasedTracker mNativeDetector;

    public FaceDetector() { /* not used */ }

    public void onResume(final Context pContext, final IOnLoaded pIOnLoaded) {
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, pContext, new BaseLoaderCallback(pContext) {
                    @Override
                    public void onManagerConnected(int status) {
                        switch (status) {
                            case LoaderCallbackInterface.SUCCESS: {
                                Log.i(TAG, "OpenCV loaded successfully");

                                // Load native library after(!) OpenCV initialization
                                System.loadLibrary("detection_based_tracker");

                                try {
                                    // load cascade file from application resources
                                    InputStream is = pContext.getResources().openRawResource(R.raw.lbpcascade_frontalface);
                                    File cascadeDir = pContext.getDir("cascade", Context.MODE_PRIVATE);
                                    File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
                                    FileOutputStream os = new FileOutputStream(mCascadeFile);

                                    byte[] buffer = new byte[4096];
                                    int bytesRead;
                                    while ((bytesRead = is.read(buffer)) != -1) {
                                        os.write(buffer, 0, bytesRead);
                                    }
                                    is.close();
                                    os.close();

                                    mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);

                                    cascadeDir.delete();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                                }
                            }
                            break;
                            default: {
                                super.onManagerConnected(status);
                            }

                            break;
                        }
                        pIOnLoaded.onLoaded();
                    }
                }
        );
    }

    public Rect[] Detectfaces(Mat pGray) {
        MatOfRect faces = new MatOfRect();

        if (mNativeDetector != null)
            mNativeDetector.detect(pGray, faces);

        return faces.toArray();
    }

    public void start() {
        mNativeDetector.start();
    }


    public void stop() {
        mNativeDetector.stop();
    }


    public void setMinFaceSize(int mAbsoluteFaceSize) {
        mNativeDetector.setMinFaceSize(mAbsoluteFaceSize);
    }
}
