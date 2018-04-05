package com.promact.filecopy;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
private  EditText editText;
private Button submit;
    private String path = "/DropContactsApp/Images/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.enter_file_name);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fileName = editText.getText().toString();
                editText.setText("");
                String  filePath=Environment.getExternalStorageDirectory().getAbsolutePath();


                String  filePath1 = filePath + "\\" + fileName;
                String  newFileName = " - Copy";
                String  newFilePath = filePath + "\\";
                File file = new File(filePath);

/*
                try
                {
                    boolean isFileExist = false;
                    int i = 0;

                    do
                    {
                        newFileName = ' ' + newFileName.substring(newFileName.lastIndexOf('-'));
                        newFileName = fileName.split(".").length + newFileName;
                        if (i == 0)
                        {
                            newFilePath = filePath + "\\" + newFileName + fileName.substring(fileName.lastIndexOf('.'));
                        }
                        else
                        {
                            newFilePath = filePath + "\\" + newFileName + " (" + i + ")" + fileName.substring(fileName.lastIndexOf('.'));
                        }

                        isFileExist = File.Exists(newFilePath);
                        i++;
                    }
                    while (isFileExist);

                    File.Copy(filePath, newFilePath);
                    Console.WriteLine("Copied successfully!");
                }
                catch (Exception ex)
                {
                    Console.Write(ex.Message);
                }*/
            }
        });
    }
   /* public static void copyFile(String content, String name) throws IOException {
        File file=new File(name);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            //source = new FileInputStream(sourceFile).getChannel();
            //destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }*/
}
