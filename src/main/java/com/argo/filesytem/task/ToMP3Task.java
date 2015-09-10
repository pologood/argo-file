package com.argo.filesytem.task;

import com.argo.filesytem.FileTaskInfo;
import com.argo.filesytem.FilesConfig;
import com.google.common.io.Closeables;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Created by yamingd on 9/10/15.
 */
public class ToMP3Task extends AbstractFileTask {

    public static final String tag = "mp3";

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public boolean execute(FileTaskInfo fileTaskInfo) throws Exception {

        File file = new File(FilesConfig.instance.getFolder(), fileTaskInfo.getFilePath());
        if (!file.exists()){
            return false;
        }

        String[] cmd2 = new String[4];
        cmd2[0] = FilesConfig.instance.getFfmpeg();
        cmd2[1] = "-i";
        cmd2[2] = file.getAbsolutePath();
        cmd2[3] = file.getAbsolutePath() + ".mp3";

        String toFile = file.getAbsolutePath() + ".mp3";
        File toF = new File(toFile);
        if (toF.exists()) {
            toF.delete();
        }

        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象

        try {

            logger.debug("CmdExecutor. toMp3. 准备执行命令 cmd={}", StringUtils.join(cmd2, " "));
            Process p = run.exec(cmd2);// 启动另一个进程来执行命令

            String inputLog = file.getAbsolutePath() + ".mp3input.log";
            String errorLog = file.getAbsolutePath() + ".mp3error.log";

            new CommandStreamHandlerThread("toMp3 INPUT", inputLog, p.getInputStream()).start();
            new CommandStreamHandlerThread("toMp3 ERROR", errorLog, p.getErrorStream()).start();

            //检查命令是否执行失败。
            if (0 == p.waitFor() && 0 == p.exitValue()) {
                logger.debug("命令执行成功!, cmd={}", StringUtils.join(cmd2, " "));
                return true;
            } else{
                logger.error("命令执行失败!, cmd={}", StringUtils.join(cmd2, " "));
                return false;
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    class CommandStreamHandlerThread extends Thread {

        private String type;
        private String logFile;
        private InputStream is;

        CommandStreamHandlerThread() {

        }

        CommandStreamHandlerThread(String type, String logFile, InputStream is) {
            this.type = type;
            this.logFile = logFile;
            this.is = is;
        }

        @Override
        public void run() {
            if (null == is) {
                return;
            }
            BufferedInputStream bis = null;
            BufferedReader br = null;

            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;

            try {

                fos = new FileOutputStream(new File(logFile));
                osw = new OutputStreamWriter(fos);
                bw = new BufferedWriter(osw);


                bis = new BufferedInputStream(is);
                br = new BufferedReader(new InputStreamReader(bis));
                String lineStr;
                while (null != (lineStr = br.readLine())) {
                    //获得命令执行后在控制台的输出信息
                    //System.out.println(type + " --> " + lineStr);// 打印输出信息
                    bw.write(lineStr);
                    bw.newLine();
                }
                bw.flush();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {

                Closeables.closeQuietly(is);

                Closeables.closeQuietly(is);
                Closeables.closeQuietly(bis);
                Closeables.closeQuietly(br);

            }
        }
    }
}
