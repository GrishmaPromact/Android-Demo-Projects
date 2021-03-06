package com.promact.mvparchitecture.view;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.promact.mvparchitecture.R;
import com.promact.mvparchitecture.injection.Injection;
import com.promact.mvparchitecture.presenter.ContractUserActionListener;
import com.promact.mvparchitecture.presenter.ImagePresenter;

import java.io.IOException;

/**
 * Created by grishma on 22-11-2017.
 */

public class ImageViewerFragment extends Fragment implements ContractView {
    private static final int SELECT_PICTURE = 2;
    final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private ContractUserActionListener mContractUserActionListener;
    private ImageView mImageThumbnail;
    private TextView mImageName;

    private String mNewFileName = "";

    public ImageViewerFragment() {
    }

    public static ImageViewerFragment newInstance() {

        Bundle args = new Bundle();

        ImageViewerFragment fragment = new ImageViewerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        mContractUserActionListener = new ImagePresenter(this,
                Injection.provideImageFile());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mImageThumbnail = (ImageView) root.findViewById(R.id.imageThumbnail);
        mImageName = (TextView) root.findViewById(R.id.imageName);
        Button btnCopyImageIntoAppDir = (Button) root.findViewById(R.id.copyImageIntoAppDir);
        btnCopyImageIntoAppDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mContractUserActionListener.copyImageIntoAppDir(getActivity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnRenameFile = (Button) root.findViewById(R.id.renameFile);
        btnRenameFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }

            private void showInputDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter New Image Name");

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mNewFileName = input.getText().toString();

                        //send request to rename file once new name is received.
                        mContractUserActionListener.renameImage(getActivity(), mNewFileName);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        FloatingActionButton fab =
                (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preparePhotoSelection();
                Snackbar.make(view, "Image selected", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            private void preparePhotoSelection() {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Snackbar.make(getView(), "Please grant permissions to change profile photo", Snackbar.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                } else if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    showImagePicker();
                }
            }


        });
        return root;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    showImagePicker();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Snackbar.make(getView(), "Permissions Denied to access Photos", Snackbar.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void showImagePicker() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            try {
                mContractUserActionListener.loadImage(getActivity(), selectedImageUri);
                mContractUserActionListener.loadImageInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showImagePreview(@NonNull Uri uri) {
        if (uri == null) return;

        mImageThumbnail.setVisibility(View.VISIBLE);

        // This app uses Glide for image loading
        Glide.with(this)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(mImageThumbnail) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                    }
                });
    }

    @Override
    public void showImageInfo(String infoString) {
        mImageName.setText(infoString);
    }

    @Override
    public void setUserActionListener(ContractUserActionListener userActionListener) {
        mContractUserActionListener = userActionListener;
    }
}
