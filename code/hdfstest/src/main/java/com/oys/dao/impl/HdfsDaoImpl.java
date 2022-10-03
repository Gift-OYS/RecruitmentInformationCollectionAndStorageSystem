package com.oys.dao.impl;

import com.oys.dao.HdfsDao;
import com.oys.util.UploadFile;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Timer;

public class HdfsDaoImpl implements HdfsDao {

    static Configuration conf = new Configuration();
    static String hdfsPath = "hdfs://hadoop102:8020/user";

    public static void init() {
        try {
            conf.set("fs.defaultFS", "hdfs://hadoop102:8020/user");
            conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
            System.setProperty("HADOOP_USER_NAME", "gift_oys");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDirectory(String filePath) throws IOException {
        init();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.mkdirs(new Path("/user/" + filePath));
        fs.close();
    }

    @Override
    public void deleteFile(String deletePath) throws IOException {
        init();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.deleteOnExit(new Path("/user/" + deletePath));
        fs.close();
    }

    @Override
    public FileStatus[] showFiles(String username) throws IOException {
        init();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FileStatus[] list = fs.listStatus(new Path("/user/" + username));
        fs.close();
        return list;
    }

    @Override
    public InputStream getInputStream(String cloudPath) throws IOException {
        init();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FSDataInputStream in = fs.open(new Path("/user/" + cloudPath + "/"));
        return in;
    }

    @Override
    public void upload(String fileName, InputStream in) throws IOException {
        init();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FSDataOutputStream out = fs.create(new Path("/user/" + fileName + "/"));
        IOUtils.copyBytes(in, out, 10240, false);
        fs.close();
    }

    @Override
    public void reName(String path1, String path2) throws IOException {
        init();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.rename(new Path("/user/" + path1 + "/"), new Path("/user/" + path2 + "/"));
        fs.close();
    }

    @Override
    public void merge(String path1, String path2) throws IOException {
        init();
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FSDataOutputStream outputStream = fs.create(new Path("/user/" + path2 + "/"), true);
        FileStatus[] list = fs.listStatus(new Path("/user/" + path1 + "/"));
        for(FileStatus fileStatus : list){
            Boolean isDir = fileStatus.isDirectory();
            if (!isDir){
                FSDataInputStream inputStream = fs.open(fileStatus.getPath());
                IOUtils.copyBytes(inputStream, outputStream, 10240, false);
                IOUtils.closeStream(inputStream);
            }
        }
        IOUtils.closeStream(outputStream);
        fs.close();
    }

    @Override
    public void timerUpload() throws IOException {
        Timer timer = new Timer();
        UploadFile uploadFile = new UploadFile();
        timer.schedule(uploadFile, 0, 24*60*60*1000);
    }

}
