package com.oys.service;

import org.apache.hadoop.fs.FileStatus;

import java.io.IOException;
import java.io.InputStream;

public interface HdfsService {

    void addDirectory(String filePath) throws IOException;

    void deleteFile(String deletePath) throws IOException;

    FileStatus[] showFiles(String username) throws IOException;

    InputStream getInputStream(String path) throws IOException;

    void upload(String fileName, InputStream in) throws IOException;

    void reName(String path1, String path2) throws IOException;

    void merge(String path1, String path2) throws IOException;

    void timerUpload() throws IOException;
}
