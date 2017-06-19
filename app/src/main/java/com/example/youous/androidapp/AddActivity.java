package com.example.youous.androidapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by youous on 2016/8/2.
 */
public class AddActivity extends AppCompatActivity implements View.OnClickListener {
//1111
    AddActivity addactivity;
    ImageButton exit;
    Button addP;
    Button isOk;
    ImageView imgp;
    EditText AddUserName;
    EditText Name;
    EditText TelNum;
    Bitmap  bitmap;
    public Boolean isPhoto;
    String SDmFilePath,PFilePath;
    String response,Qrcode, PUrl;
    boolean isTTTT; //上传中不能再点击
    boolean isb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        isTTTT =false;
        addactivity = this;
        exit= (ImageButton) findViewById(R.id.add_exit);
        exit.setOnClickListener(this);
        addP= (Button) findViewById(R.id.addp);
        addP.setOnClickListener(this);
        imgp= (ImageView) findViewById(R.id.addimg);
        isOk= (Button) findViewById(R.id.addisok);
        isOk.setOnClickListener(this);
        isPhoto = false;
        AddUserName  = (EditText) findViewById(R.id.addusername);
        Name  = (EditText) findViewById(R.id.addname);
        TelNum  = (EditText) findViewById(R.id.addtelnum);



//        Log.v("yououaaa","FEE   ");
        SDmFilePath = Environment.getExternalStorageDirectory().getPath()+ "/" + "app_photo.png";
//        Log.v("yououaaa","FEE   "+SDmFilePath);
//        File file = new File(SDmFilePath);
//        if(file.exists())
//        {
            int REQUEST_EXTERNAL_STORAGE = 1;
            String[] PERMISSIONS_STORAGE = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            int permission = ActivityCompat.checkSelfPermission(AddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        AddActivity.this,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }

            bitmap = BitmapFactory.decodeFile(SDmFilePath);
//        }
//        else{
//            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
//        }
          if (Data.isAddData==true){
              Data.isAddData = false;
             AddUserName.setText(Data.AddData[1]);
             Name.setText(Data.AddData[2]);
             TelNum.setText(Data.AddData[3]);
          }


    }

    @Override
    public void onClick(View view) {
        if(isTTTT == true){
            return;
        }
        switch (view.getId()){

            case R.id.add_exit:
                startActivity(new Intent(AddActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.addp://打开相机拍照
                /**
                 * 拍照获取图片
                 */
                takePhoto();
            break;
            case R.id.addisok:
                if(isConnect(this)==false){
                    new AlertDialog.Builder(this)
                    .setTitle("网络错误")
                            .setMessage("网络连接失败,请确认网络连接")
                            .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int arg1) {
                                    //TODO AUto_generated method stub
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(0);
                                    Toast.makeText(addactivity, "服务器异常", Toast.LENGTH_LONG).show();

                                }
                            }).show();
                }
                if(isPhoto==true&&
                        !TextUtils.isEmpty(Name.getText())&&!TextUtils.isEmpty(TelNum.getText())){
                    //用户不存在
                    if(TextUtils.isEmpty(AddUserName.getText())){
                        AddUserName.setText("nn");
                    }
                    //存在的话 生成时间 二维码(用户名加时间后4位) 上传
                    long aa = System.currentTimeMillis();//时间
                    PFilePath = ""+AddUserName.getText()+(aa%1000000)+".png";//二维码
                    Log.v("tag",""+AddUserName.getText()+(aa%1000000)+".png");
                    Qrcode = ""+AddUserName.getText()+(aa%1000000);//二维码
                    Log.v("tag",Qrcode);
                    PUrl ="http://www.sino-chance.com/ftpuser/images/apps/"+PFilePath;

                    new Thread(){
                        public void run() {
//                            Log.v("yououaaa","fffffff  "+Data.Http_ADD_Url+"?username="+AddUserName.getText()+"&name="+Name.getText()+"&telnum="+TelNum.getText()+"&qrcode="+Qrcode+"&picture="+ PUrl +"&time="+7200000);
//                            response = HttpRequest.get(Data.Http_ADDisY_Url+AddUserName.getText()).body();
                            response = "1";
                            if (response.equals("0")){
                                addactivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(addactivity, "用户不存在", Toast.LENGTH_LONG).show();
                                       return;
                                    }
                                });
                            }else if (response.equals("1")){
                                new Thread(){
                                    public void run() {
                                        isTTTT = true;
                                        long aa = System.currentTimeMillis();//时间
          //                            PFilePath = ""+AddUserName.getText()+(aa%10000)+".png";//二维码
                                        //上传图片到ftp
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                                        bitmap = Data.zoomImage(bitmap,480,320);
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                                        bitmap = Data.zoomImage(bitmap,480,320);

                                         InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                                         isb= Data.uploadFile2("192.168.10.124",21,"ftpuser","cx123","/home/ftpuser/images/apps/","",PFilePath,isBm);

                                        addactivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(isb){
                                                    new Thread(){
                                                        public void run() {
                                                            response = HttpRequest.get(Data.Http_ADD_Url + "?username=" + AddUserName.getText() + "&name=" + Name.getText() + "&telnum=" + TelNum.getText() + "&qrcode=" + Qrcode + "&picture=" + PUrl + "&time=" + 7200000).body();
                                                        }}.start();
                                                    Toast.makeText(addactivity, "添加成功", Toast.LENGTH_LONG).show();
                                                    addactivity.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            addactivity.startActivity(new Intent(addactivity,MainActivity.class));
                                                            addactivity.finish();
                                                            return;
                                                        }
                                                    });
                                                }else{
                                                    Toast.makeText(addactivity, "添加失败", Toast.LENGTH_LONG).show();
                                                }


                                                return;
                                            }
                                        });
                                    }
                                }.start();

                            }else {
                                addactivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(addactivity, "网络异常", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                });
                            }
                        }
                    }.start();




                }else {
                    Toast.makeText(this, "用户信息不完整", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        // 加载路径
        Uri uri = Uri.fromFile(new File(SDmFilePath));
        // 指定存储路径，这样就可以保存原图了
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AddActivity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Toast.makeText(this, "sd卡不可用", Toast.LENGTH_LONG).show();
                return;
            }
            String name = "拍照成功" ;
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
//            Bundle bundle = data.getExtras();
//            bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
////            bitmap = Data.zoomImage(bitmap,1080,700);
//            imgp.setImageBitmap(bitmap);
//            isPhoto =true;
//            addP.setText("重新拍照");
            File file = new File(SDmFilePath);
            if(file.exists())
            {
                bitmap=BitmapFactory.decodeFile(SDmFilePath);
                bitmap = Data.zoomImage(bitmap,1080,810);
                imgp.setImageBitmap(bitmap);
                isPhoto =true;
                addP.setText("重新拍照");
            }else{
                Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //对是否能连上服务器进行判断
    public static boolean isConnect(Context context){
        //获取手机所有链接管理对象（包括对wifi,net等链接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                //获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    //判断当前网络是否已经链接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }catch (Exception e){
            //TODO:handle exception
            Log.v("error",e.toString());
        }
        return  false;
    }



}