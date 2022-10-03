package com.oys.service.impl;

import com.oys.dao.HdfsDao;
import com.oys.dao.impl.HdfsDaoImpl;
import com.oys.service.HdfsService;
import org.apache.hadoop.fs.FileStatus;
import java.io.IOException;
import java.io.InputStream;

public class HdfsServiceImpl implements HdfsService {

    private HdfsDao hdfsDao = new HdfsDaoImpl();

    @Override
    public void addDirectory(String filePath) throws IOException {
        hdfsDao.addDirectory(filePath);
    }

    @Override
    public void deleteFile(String deletePath) throws IOException {
        hdfsDao.deleteFile(deletePath);
    }

    @Override
    public FileStatus[] showFiles(String username) throws IOException {
        return hdfsDao.showFiles(username);
    }

    @Override
    public InputStream getInputStream(String cloudPath) throws IOException {
        return hdfsDao.getInputStream(cloudPath);
    }

    @Override
    public void upload(String fileName, InputStream in) throws IOException {
        hdfsDao.upload(fileName, in);
    }

    @Override
    public void reName(String path1, String path2) throws IOException {
        hdfsDao.reName(path1, path2);
    }

    @Override
    public void merge(String path1, String path2) throws IOException {
        hdfsDao.merge(path1, path2);
    }

    @Override
    public void timerUpload() throws IOException {
        hdfsDao.timerUpload();
    }
}
