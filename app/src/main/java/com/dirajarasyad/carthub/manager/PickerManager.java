package com.dirajarasyad.carthub.manager;

import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;

public class PickerManager {
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    public PickerManager(ActivityResultLauncher<PickVisualMediaRequest> pickMedia) {
        this.pickMedia = pickMedia;
    }

    public void pickImageOnly() {
        this.launch(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE);
    }

    public void pickVideoOnly() {
        this.launch(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE);
    }

    private void launch(ActivityResultContracts.PickVisualMedia.VisualMediaType mediaType) {
        PickVisualMediaRequest request = new PickVisualMediaRequest.Builder().setMediaType(mediaType).build();
        pickMedia.launch(request);
    }
}
