package net.vrgsoft.videcrop.cropinterface;

import androidx.annotation.NonNull;

public class ValueDeliveringCropHandler implements CropHandler {

    @Override
    public void handleCropOperation(@NonNull CropParameters cropParameters, @NonNull CropHandlerCallback cropHandlerCallback) {
        cropHandlerCallback.onSuccess("");
    }

    @Override
    public void dispose() {

    }
}