package com.promact.mvparchitecture.presenter;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by grishma on 27-11-2017.
 */

public interface ContractUserActionListener {

    //An action taken by user to load a image from gallery into ImageView widget
    void loadImage(Context context, Uri uri) throws IOException;

    //User initiates action to display image file information in textView UI
    void loadImageInfo();

    //User action to copy an image from gallery in to app's picture directory (app's data directory)
    void copyImageIntoAppDir(Context context) throws IOException;

    //User can rename a image file (in app's data directory)
    void renameImage(Context context, String newName);
}
