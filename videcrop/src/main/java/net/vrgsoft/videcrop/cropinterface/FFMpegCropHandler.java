package net.vrgsoft.videcrop.cropinterface;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.util.Util;

import net.vrgsoft.videcrop.ffmpeg.ExecuteBinaryResponseHandler;
import net.vrgsoft.videcrop.ffmpeg.FFmpeg;
import net.vrgsoft.videcrop.ffmpeg.FFtask;

import java.util.Formatter;
import java.util.Locale;

public class FFMpegCropHandler implements CropHandler {

    private StringBuilder formatBuilder = new StringBuilder();
    private Formatter formatter = new Formatter(formatBuilder, Locale.getDefault());

    private FFmpeg mFFMpeg;
    private FFtask mFFTask;

    private @NonNull Context context;

    public FFMpegCropHandler(@NonNull Context context)
    {
        this.context = context;
    }

    @Override
    public void handleCropOperation(@NonNull final CropParameters cropParameters,@NonNull final CropHandlerCallback cropHandlerCallback) {
        String start = Util.getStringForTime(formatBuilder, formatter, cropParameters.getStartCrop());
        String duration = Util.getStringForTime(formatBuilder, formatter, cropParameters.getCropDuration());
        start += "." + cropParameters.getStartCrop() % 1000;
        duration += "." + cropParameters.getCropDuration() % 1000;
        mFFMpeg = FFmpeg.getInstance(context);
        Rect cropRect = cropParameters.getCropRect();
        if (mFFMpeg.isSupported()) {
            String crop = String.format(Locale.getDefault(),
                    "crop=%d:%d:%d:%d:exact=0",
                    cropRect.right,
                    cropRect.bottom,
                    cropRect.left,
                    cropRect.top);
            String[] cmd = {"-y", "-ss", start, "-i", cropParameters.getInputPath(), "-t", duration, "-vf", crop, cropParameters.getOutputPath()};
            mFFTask = mFFMpeg.execute(cmd, new ExecuteBinaryResponseHandler() {
                @Override
                public void onSuccess(String message) {
                    cropHandlerCallback.onSuccess(message);
                }

                @Override
                public void onProgress(String message) {
                    cropHandlerCallback.onProgress(message);
                    Log.e("onProgress", message);
                }

                @Override
                public void onFailure(String message) {
                    cropHandlerCallback.onFailure(message);
                    //Toast.makeText(VideoCropActivity.this, "Failed to crop!", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure", message);
                }

                @Override
                public void onProgressPercent(float percent) {
                    cropHandlerCallback.onProgressPercent(percent);
                }

                @Override
                public void onStart() {
                    cropHandlerCallback.onStart();
                }

                @Override
                public void onFinish() {
                    cropHandlerCallback.onFinish();
                }
            }, cropParameters.getCropDuration() * 1.0f / 1000);
        }
    }

    public void dispose()
    {
        if (mFFTask != null && !mFFTask.isProcessCompleted()) {
            mFFTask.sendQuitSignal();
        }
        if (mFFMpeg != null) {
            mFFMpeg.deleteFFmpegBin();
        }
    }
}