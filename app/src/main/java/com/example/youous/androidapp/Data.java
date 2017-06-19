package com.example.youous.androidapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Vector;


/**
 * Created by youous on 2016/8/2.
 */
public class Data {
    //用户信息
    public static Vector<User> vUser = new Vector<User>();
    public static int GetUserId =0;
    public static String Ip ="159";
    public static String Http_Delete_Url = "http://192.168.10."+Ip+":8080/info/delete";
    public static String Http_Login_Url = "http://192.168.10."+Ip+":8080/staff/login";
    public static String Http_GetUser_Url = "http://192.168.10."+Ip+":8080/infos?getuser";
    public static String Http_ADD_Url = "http://192.168.10."+Ip+":8080/info/addinfo";
    public static String Http_ADDisY_Url = "http://192.168.10."+Ip+":8080//customer/username/";
    public static String[] AddData;//新增用户数据
    public static boolean isAddData;//是否自动


    //获取用户信息
    public static void GetUser(){
        //发送 GET 请求
        new Thread(){
            public void run() {
                String response = HttpRequest.get(Http_GetUser_Url).body();
                Log.v("yououaaa","用户信息11："+response);
                JSONArray jsonArray = null;
                vUser.removeAllElements();//清空用户信息
                try {
                    jsonArray = new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        User user = new User(jsonObject);
                        vUser.add(user);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static String getTimeHH(long time){
        if(time<0){
            return "00";
        }else{
//            time+=1000*3600*8;
            long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            if(hours<10){
                return  "0"+hours;
            }else{
                return  ""+hours;
            }
        }
    }

    public static String getTimeMM(long time){
        if(time<0){
            return "00";
        }else{
            long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
            if(minutes<10){
                return  "0"+minutes;
            }else{
                return  ""+minutes;
            }
        }
    }

    public static String getTimeSS(long time){
        if(time<0){
            return "00";
        }else{
            long seconds = (time % (1000 * 60)) / 1000;
            if(seconds<10){
                return  "0"+seconds;
            }else{
                return  ""+seconds;
            }
        }
    }

    public static long getTimeHHl(long time){
        if(time<0){
            return 0;
        }else{
//            time+=1000*3600*8;
            long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);

                return  hours;

        }
    }

    public static long getTimeMMl(long time){
        if(time<0){
            return 0;
        }else{
            long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);

                return  minutes;

        }
    }

    public static long getTimeSSl(long time){
        if(time<0){
            return 0;
        }else{
            long seconds = (time % (1000 * 60)) / 1000;

                return  seconds;

        }
    }

    //图片缩放
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static String[] getStringArray(String s, char ch) {
        if (s.length() == 0) {
            return null;
        }
        int startIndex = 0;
        int endIndex = 0;
        Vector vS = new Vector();
        while (true) {
            endIndex = s.indexOf(ch, startIndex);
            if (endIndex == -1) {
                vS.addElement(s.substring(startIndex));
                break;
            } else {
                vS.addElement(s.substring(startIndex, endIndex));
                startIndex = endIndex + 1;
            }
        }
        String[] strs = new String[vS.size()];
        vS.copyInto(strs);
        return strs;
    }

    public static void savePNG_After(Bitmap bitmap, String name) {
        File file = new File(name);
        try {
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveBitmap(Bitmap bm,String st) {
        File f = new File("/sdcard/namecard/", st);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

//    public static int GetSearchId(String st){
//
//    }
//    public static String formatDuring(long mss) {
//        long days = mss / (1000 * 60 * 60 * 24);
//        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)+8;//移动8个小时时差
//        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
//        long seconds = (mss % (1000 * 60)) / 1000;
//        return days + " 天 " + hours + " 小时 " + minutes + " 分钟 "
//                + seconds + " 秒 ";
//    }

//    long aa = System.currentTimeMillis();
//    SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//    String retStrFormatNowDate = sdFormatter.format(new Date(System.currentTimeMillis()));
//    Log.v("yououaaa","RR  "+retStrFormatNowDate);
//    Toast.makeText(this, ""+aa, Toast.LENGTH_LONG).show();


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param basePath
     *            FTP服务器保存目录，是linux下的目录形式,如/photo/
     * @param filename
     *            上传到FTP服务器上的文件名,是自己定义的名字，
     * @param input
     *            输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile2(String host, int port, String username, String password, String basePath,
                                     String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath+filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }

//    #FTP相关配置
//    #FTP的ip地址
//            FTP_ADDRESS=192.168.10.105
//    FTP_PORT=21
//    FTP_USERNAME=ftpuser
//            FTP_PASSWORD=cx123
//    FTP_BASE_PATH=/home/ftpuser/images
//    #图片服务器的相关配置
//    #图片服务器的基础url
//    IMAGE_BASE_URL=http\://192.168.10.105/images

    //搜索
    public static int SearchUserId(String str){
        boolean isb =false;
        for (int i=0;i<vUser.size();i++){
            if (vUser.get(i).TelNum.equals(str)){
                return i;
            }
        }
        return  -1;
    }

    //搜索
    public static int SearchQUserId(String str){
        boolean isb =false;
        for (int i=0;i<vUser.size();i++){
            if (vUser.get(i).Qrcode.equals(str)){
                return i;
            }
        }
        return  -1;
    }

    //获取日期
    public static String GetStringData(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String  mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String  mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
//        return mYear + "年" + mMonth + "月" + mDay+"日"+"/星期"+mWay;
        return mMonth + "月" + mDay+"日"+"/星期"+mWay;
    }


}
