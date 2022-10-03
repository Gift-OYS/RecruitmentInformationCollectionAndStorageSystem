package com.oys.dao;

import java.io.IOException;
import java.io.InputStream;
import org.apache.hadoop.fs.FileStatus;

public interface HdfsDao {

	void addDirectory(String filePath) throws IOException;

	void deleteFile(String deletePath) throws IOException;

	FileStatus[] showFiles(String username) throws IOException;

	InputStream getInputStream(String cloudPath) throws IOException;

	void upload(String fileName,InputStream in) throws IOException;

	void reName(String path1,String path2) throws IOException;

	void merge(String path1,String path2) throws IOException;

	void timerUpload() throws IOException;
}
