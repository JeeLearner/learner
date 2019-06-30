package com.jee.poi.excel.utils;

import ch.ethz.ssh2.*;

import java.io.*;

/**
 * Scp工具类
 *
 * @author jeeLearner
 * @date 2019/6/30
 */
public class ScpUtil {

    private String ip;
    private int port;
    private String user;
    private String password;

    private static ScpUtil instance;
    /**
     * 单例模式
     * 懒汉式
     * 线程安全
     * @return
     */
    public static ScpUtil getInstance(){
        if (null == instance){
            synchronized (ScpUtil.class) {
                if (null == instance){
                    instance = new ScpUtil();
                }
            }
        }
        return instance;
    }

    public static ScpUtil getInstance(String ip, int port, String name, String password){
        if(null == instance){
            synchronized (ScpUtil.class){
                if(null == instance){
                    instance = new ScpUtil(ip,port,name,password);
                }
            }
        }
        return instance;
    }

    /**
     * 私有化默认构造函数
     * 实例化对象只能通过getInstance
     */
    private ScpUtil(){
    }

    /**
     * 私有化有参构造函数
     * @param ip 服务器ip
     * @param port 服务器端口 22
     * @param user 登录名
     * @param password 登录密码
     */
    private ScpUtil(String ip, int port, String user, String password){
        this.ip = ip ;
        this.port = port;
        this.user = user;
        this.password = password;
    }




    /**
     * 下载文件
     *
     * @param remoteFile 服务器上的文件名
     * @param remoteTargetDirectory 服务器上文件的所在路径
     * @param newPath 下载文件的路径
     */
    public void getFile(String remoteFile, String remoteTargetDirectory,String newPath){
        Connection conn = new Connection(ip, port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(user, password);
            if (!isAuthenticated){
                System.out.println("建立连接失败");
                return ;
            } else {
                SCPClient scpClient = conn.createSCPClient();
                //SCPInputStream sis = scpClient.get(remoteTargetDirectory + File.separator + remoteFile);
                SCPInputStream sis = scpClient.get(remoteTargetDirectory + "/" + remoteFile);
                File f = new File(newPath);
                if (!f.exists()){
                    f.mkdirs();
                }
                File newFile = new File(newPath + remoteFile);
                OutputStream fos = new FileOutputStream(newFile);

                byte[] b = new byte[4096];
                int length;
                while ((length = sis.read(b)) != -1){
                    fos.write(b, 0, length);
                }
                fos.flush();
                fos.close();
                sis.close();
                conn.close();
                System.out.println("下载成功");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 上传文件到服务器
     *
     * @param f 文件对象
     * @param remoteTargetDirectory 上传路径
     * @param mode 默认为null
     */
    public void putFile(File f, String remoteTargetDirectory, String mode) {
        Connection connection = new Connection(ip,port);
        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(user, password);
            if(!isAuthenticated){
                System.out.println("连接建立失败");
                return ;
            }
            SCPClient scpClient = new SCPClient(connection);
            SCPOutputStream os = scpClient.put(f.getName(),f.length(),remoteTargetDirectory,mode);

            byte[] b = new byte[4096];
            FileInputStream fis = new FileInputStream(f);
            int length;
            while ((length = fis.read(b)) != -1) {
                os.write(b, 0, length);
            }
            os.flush();
            fis.close();
            os.close();
            connection.close();
            System.out.println("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  获取服务器上相应文件的流
     *
     * @param remoteFile 文件名
     * @param remoteTargetDirectory 文件路径
     * @return
     * @throws IOException
     */
    public SCPInputStream getStream(String remoteFile, String remoteTargetDirectory) throws IOException {
        Connection connection = new Connection(ip,port);
        connection.connect();
        boolean isAuthenticated = connection.authenticateWithPassword(user,password);
        if(!isAuthenticated){
            System.out.println("连接建立失败");
            return null;
        }
        SCPClient scpClient = connection.createSCPClient();
        return scpClient.get(remoteTargetDirectory + "/" + remoteFile);
    }

    /**
     * 删除远程服务器指定目录下的文件
     * @param remoteFile
     * @param remoteTargetDirectory
     */
    public void removeFile(String remoteFile, String remoteTargetDirectory){
        Connection connection = new Connection(ip,port);
        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(user, password);
            if(!isAuthenticated){
                System.out.println("连接建立失败");
                return ;
            }
            SFTPv3Client sftpClient = new SFTPv3Client(connection);
            sftpClient.rm(remoteTargetDirectory + remoteFile);
            sftpClient.close();
            connection.close();
            System.out.println("删除成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  static  void  main(String[] args){

        File  f1 = new File("D://test.xlsx");

        /*ScpUtil.getInstance("140.143.45.241",22, "jee","jee123")
                .getFile("sakila-db.tar.gz", "/home/jee/", "D://upload2/");*/
        /*ScpUtil.getInstance("140.143.45.241",22, "jee","jee123")
                .putFile(f1, "/home/jee/", null);*/
        ScpUtil.getInstance("140.143.45.241",22, "jee","jee123")
                .removeFile("test.xlsx", "/home/jee/");

    }
}

