package testRPC.test.server;

import testRPC.test.api.FileOperate;

import java.io.*;

public class FileOperateImpl implements FileOperate {

    synchronized public void write(String fileName,String context) throws Exception {

        File file = new File(fileName);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

        bufferedWriter.write(context);

        bufferedWriter.close();

    }

    public String readOneLine(String fileName,int index) throws Exception{


        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

        int i = 1;
        String tempStr = null;
        while((tempStr=bufferedReader.readLine())!=null&&i<index){
            i++;
        }
        return tempStr;
    }
}
