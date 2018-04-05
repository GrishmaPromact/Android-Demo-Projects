package com.promact.mvparchitecture.view;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.promact.mvparchitecture.presenter.ContractUserActionListener;

/**
 * Created by grishma on 27-11-2017.
 */

public interface ContractView {
    //displays image in Imageview widget
    void showImagePreview(@NonNull Uri uri);

    //Displays image file information in textview
    void showImageInfo(String infoString);

    //That's how a presenter is assigned to a view
    void setUserActionListener(ContractUserActionListener userActionListener);
}
