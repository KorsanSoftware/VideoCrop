package net.vrgsoft.videcrop.cropinterface;

import android.graphics.Rect;

import androidx.annotation.NonNull;

public interface CropHandler {
    void handleCropOperation(CropParameters cropParameters);
    void dispose();
}