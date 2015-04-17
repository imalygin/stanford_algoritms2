package org.imalygin.util;

import org.imalygin.clustering.KClustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static BufferedReader createReaderForFile(String fileName){
        InputStream inputStream = KClustering.class.getClassLoader().getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
