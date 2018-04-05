package com.promact.mvparchitecture.presenter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.promact.mvparchitecture.Util;
import com.promact.mvparchitecture.model.ImageFile;
import com.promact.mvparchitecture.view.ContractView;

import java.io.File;
import java.io.IOException;

/**
 * Created by grishma on 27-11-2017.
 */

public class ImagePresenter implements ContractUserActionListener {

    @NonNull
    private ImageFile mImageFile;

    //Hold reference to View contract to update UI
    @NonNull
    private ContractView mImageViewer;

    public ImagePresenter(ContractView imageViewer, ImageFile imageFile) {
        mImageViewer = imageViewer;
        mImageFile = imageFile;

        mImageViewer.setUserActionListener(this);
    }

    @Override
    public void loadImage(Context context, Uri uri) throws IOException {
        mImageFile.create(context, uri.toString());
        mImageFile.setContentUri(uri);
        mImageViewer.showImagePreview(uri);
    }

    @Override
    public void loadImageInfo() {
        String infoString = mImageFile.toString();
        mImageViewer.showImageInfo(infoString);
    }

    @Override
    public void copyImageIntoAppDir(Context context) throws IOException {
        File srcFile = new File(Util.getImageRealPathFromURI(context, mImageFile.getContentUri()));

        //create new file in app's dir
        mImageFile.create(context, "newFile", ".jpg");
        File destFile = mImageFile.getFile();

        Util.copyFile(srcFile, destFile);

        String infoString = mImageFile.toString();

        mImageViewer.showImageInfo(infoString);
    }

    @Override
    public void renameImage(Context context, String newName) {
        File srcFile = mImageFile.getFile();
        File renamedFile = Util.renameFile(srcFile, newName, ".jpg");

        mImageFile.setFile(renamedFile);

        String infoString = mImageFile.toString();

        mImageViewer.showImageInfo(infoString);
    }
}
