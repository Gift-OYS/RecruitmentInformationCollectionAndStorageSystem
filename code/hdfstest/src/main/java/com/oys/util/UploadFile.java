package com.oys.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class UploadFile extends TimerTask {

    @Override
    public void run() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf1.format(new Date());
        String fileName = "data." + date + ".csv";

        File files = new File("D:\\Java\\datas\\" + fileName);

        if (!files.exists()){
            System.out.println("未找到今日的文件！");
        }else {
            try {
                upload(files, date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void upload(File file, String date) throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI("hdfs://hadoop102:8020");
        org.apache.hadoop.conf.Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(uri, configuration, "gift_oys");
        fs.mkdirs(new Path("/user/oys/data"));
        Path newPath = new Path("/user/oys/data/data." + date + ".csv");
        fs.copyFromLocalFile(new Path(file.getAbsolutePath()), newPath);
        fs.close();
    }
}
