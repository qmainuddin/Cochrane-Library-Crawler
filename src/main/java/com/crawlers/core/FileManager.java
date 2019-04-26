package com.crawlers.core;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class FileManager<T> {

    public void writeToFile(Path filePath, Collection<? extends T> contents) throws IOException {
        String outputString = "";
        for (T item : contents){
            outputString += item.toString() + "\n";
        }
        Files.write(filePath, outputString.getBytes());
    }
}
