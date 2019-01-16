package net.vrgsoft.videcrop.cropinterface;

public interface CropHandlerCallback {
    void onSuccess(String message);

    void onProgress(String message);

    void onFailure(String message);

    void onProgressPercent(float percent);

    void onStart();

    void onFinish();
}