package com.promact.audiorecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    public static final int ACTIVITY_RECORD_SOUND = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


              /*  Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(intent, ACTIVITY_RECORD_SOUND);*/
                String pdf = "http://www.pc-hardware.hu/PDF/konfig.pdf";
                String doc="<iframe src='http://docs.google.com/gview?embedded=true&url=http://www.pc-hardware.hu/PDF/konfig.pdf' width='100%' height='100%' style='border: none;'></iframe>";
                WebView webView = (WebView) findViewById(R.id.webview_of_pdf);
        webView.setVisibility(WebView.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadData(doc, "text/html", "UTF-8");

    }
}

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_RECORD_SOUND) {
            File folder = new File(Environment.getExternalStorageDirectory(), "/Sounds");
            long folderModi = folder.lastModified();

            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return (name.endsWith(".m4a"));
                }
            };

            File[] folderList = folder.listFiles(filter);

            String recentName = "";

            for (int i = 0; i < folderList.length; i++) {
                long fileModi = folderList[i].lastModified();

                if (folderModi == fileModi) {
                    recentName = folderList[i].getName();
                }
            }
        }
    }
}
*/