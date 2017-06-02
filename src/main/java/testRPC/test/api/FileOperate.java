package testRPC.test.api;

public interface FileOperate {

    /**
     * 写文件
     *
     * @param fileName
     */
    void write(String fileName, String context) throws Exception;

    /**
     * 读文件
     *
     * @param fileName
     */
    String readOneLine(String fileName,int index) throws Exception;
}
