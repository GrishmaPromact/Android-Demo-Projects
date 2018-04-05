package com.promact.mvparchitecture.injection;

import com.promact.mvparchitecture.model.ImageFile;
import com.promact.mvparchitecture.model.ImageFileImpl;

/**
 * Created by grishma on 27-11-2017.
 */

public class Injection {
    public static ImageFile provideImageFile(){
        return new ImageFileImpl();
    }
}
