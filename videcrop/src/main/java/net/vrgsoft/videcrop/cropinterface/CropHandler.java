package net.vrgsoft.videcrop.cropinterface;

import androidx.annotation.NonNull;

public interface CropHandler {
    void handleCropOperation(@NonNull final CropParameters cropParameters,@NonNull final CropHandlerCallback cropHandlerCallback);
    void dispose();
}