package org.jeecgframework.web.hrm.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author Wang Yu
 * 文件工具类
 * Warning : 仅用于图片资源
 */
public class FileUtils {

    public static final String SEPARATOR = File.separator;

    private FileUtils(){}

    /**
     * 获取服务器IP地址
     * @return
     */
    public static InetAddress getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni =  networkInterfaces.nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while (nias.hasMoreElements()) {
                    InetAddress ia = nias.nextElement();
                    if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) {
        }
        return null;
    }

    /**
     * 获取默认的上传目录(绝对路径)
     * @return
     */
    public static String mkFilePath(HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("");
        int ch= path.lastIndexOf(SEPARATOR);
        String webapp = path.substring(0,ch);
        File file = new File(webapp + SEPARATOR + "update" + SEPARATOR + "image");
        if (!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 文件上传，生成随机文件名，命名规则为：时间戳yyyyMMddhhmmss + 四个随机数字
     * @return 简单名称
     */
    public static String mkUploadFileName(){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String formatDate = format.format(date);
        int random = (int)((Math.random() * 9 + 1) * 1000);
        return formatDate + random;
    }

    /**
     * 获取文件后缀名
     * @param file
     * @return 后缀名 无 .
     */
    public static String getSuffixName(File file){
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getSuffixName(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取上传文件名 (简单名称)
     * @param file
     * @return
     */
    public static String getUploadFileName(File file){
        return mkUploadFileName() + "." + getSuffixName(file);
    }

    public static String getUploadFileName(String fileName){
        return mkUploadFileName() + "." + getSuffixName(fileName);
    }

    /**
     * 获取上传文件绝对地址
     * @param uploadFileName 简单名称
     * @return
     */
    public static String getUploadFilePath(String uploadFileName, HttpServletRequest request){
        return mkFilePath(request) + SEPARATOR + getUploadFileName(uploadFileName);
    }

    /**
     * 获得上传地址文件对象
     * @param uploadFileName 文件名(为了获取后缀名)
     * @return
     */
    public static File getUploadFile(String uploadFileName, HttpServletRequest request){
        return new File(getUploadFilePath(uploadFileName, request));
    }

}
