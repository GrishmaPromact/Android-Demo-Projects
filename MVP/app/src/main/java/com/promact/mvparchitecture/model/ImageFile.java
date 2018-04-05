package com.promact.mvparchitecture.model;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * Created by grishma on 27-11-2017.
 */

public interface ImageFile {
    void create(Context context);

    void create(Context context, String filePath) throws IOException;

    void create(Context context, String name, String extention) throws IOException;

    boolean exists();

    void delete();

    String getPath();

    File getFile();

    void setFile(File file);

    //Content uri of file
    Uri getContentUri();

    void setContentUri(Uri uri);
}
