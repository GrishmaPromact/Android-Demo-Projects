/*
package com.promact.filecopy;

import android.os.Environment;

import java.io.Console;
import java.io.File;

*/
/**
 * Created by grishma on 05-07-2017.
 *//*

public class hello {

    namespace FileCopy
    {
        class Program
        {
            static void Main(string[] args)
            {
                string desktopPath = Environment.GetFolderPath(Environment.SpecialFolder.Desktop);

                Console.Write("Enter file name with extension: ");
                string fileName = Console.ReadLine();

                string filePath = desktopPath + "\\" + fileName;
                string newFileName = " - Copy";
                string newFilePath = desktopPath + "\\";

                try
                {
                    bool isFileExist = false;
                    int i = 0;

                    do
                    {
                        newFileName = ' ' + newFileName.Substring(newFileName.LastIndexOf('-'));
                        newFileName = fileName.Split('.').First() + newFileName;
                        if (i == 0)
                        {
                            newFilePath = desktopPath + "\\" + newFileName + fileName.Substring(fileName.LastIndexOf('.'));
                        }
                        else
                        {
                            newFilePath = desktopPath + "\\" + newFileName + " (" + i + ")" + fileName.Substring(fileName.LastIndexOf('.'));
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
                }

                Console.Read();
            }
        }
    }


}
*/
