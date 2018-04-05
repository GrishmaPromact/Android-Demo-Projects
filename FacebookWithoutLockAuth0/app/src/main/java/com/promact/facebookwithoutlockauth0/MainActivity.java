package com.promact.facebookwithoutlockauth0;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.facebook.FacebookAuthProvider;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.result.Credentials;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private static final String TAG = MainActivity.class.getName();
    private static final int RC_PERMISSIONS = 110;
    private static final int RC_AUTHENTICATION = 111;

    private FacebookAuthProvider provider;
    private AuthCallback callback;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message = (TextView) findViewById(R.id.textView);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        loginButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        Auth0 auth0 = new Auth0(getString(R.string.com_auth0_client_id), getString(R.string.com_auth0_domain));
        final AuthenticationAPIClient client = new AuthenticationAPIClient(auth0);
        provider = new FacebookAuthProvider(client);
        callback = new AuthCallback() {
            @Override
            public void onFailure(@NonNull final Dialog dialog) {
                Log.e(TAG, "Failed with dialog");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                    }
                });
            }

            @Override
            public void onFailure(final AuthenticationException exception) {
                Log.e(TAG, "Failed with message " + exception.getMessage());
                exception.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.com_auth0_facebook_authentication_failed_title)
                                .setMessage(exception.getMessage())
                                .create();
                        dialog.show();
                    }
                });
            }

            @Override
            public void onSuccess(@NonNull final Credentials credentials) {
                Log.d(TAG, "Authenticated with accessToken " + credentials.getAccessToken());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        message.setText(String.format("Welcome %s", credentials.getAccessToken()));
                    }
                });
            }

        };

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }
    }

    @Override
    public void onClick(View view) {
        final int id = view.getId();
        switch (id) {
            case R.id.loginButton:
                provider.start(this, callback, RC_PERMISSIONS, RC_AUTHENTICATION);
                break;
            case R.id.logoutButton:
                provider.clearSession();
                message.setText("");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_AUTHENTICATION) {
            provider.authorize(requestCode, resultCode, data);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}



