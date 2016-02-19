package shellljx.gmail.com.app.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import shellljx.gmail.com.app.util.CompatibilityUtil;
import shellljx.gmail.com.app.util.Constants;
import shellljx.gmail.com.app.util.DateTimeUtil;
import shellljx.gmail.com.app.util.ExifUtil;
import shellljx.gmail.com.app.util.FileModifiedTimeComparator;
import shellljx.gmail.com.app.util.ImageUtil;
import shellljx.gmail.com.widget.Dialog.SelectPicDialog;
import shellljx.gmail.com.widget.KE.KeyboardControlEditText;
import shellljx.gmail.com.widget.R;

/**
 * Created by Administrator on 2015/6/8.
 */
public class CreatePostActivity extends BaseActivity implements DialogInterface.OnClickListener,View.OnClickListener {

    @Bind(R.id.new_content) KeyboardControlEditText newContent;
    @Bind(R.id.menu_pic) ImageButton photo_btn;
    @Bind(R.id.menu_emation) ImageButton emation_btn;
    @Bind(R.id.menu_topic) ImageButton topic_btn;
    @Bind(R.id.image_preview) ImageView imagePreview;
    @Bind(R.id.menu_send) Button send_btn;

    private SelectPicDialog selectPicDialog;
    public static final String TAG = CreatePostActivity.class.getSimpleName();
    private static final int CAMERA_RESULT = 0;
    private static final int PIC_RESULT = 2;

    private Uri imgFileUri = null;
    private String imagePath = "";
    private boolean hasImageFile;
    private int rotateDegrees;
    private Bitmap thumbnail;
    private Bitmap prePic;

    static {
        File temp = new File(Constants.DEFAULT_IMAGE_STORE_DIR);
        if(!temp.exists()){
            temp.mkdirs();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);
        ButterKnife.bind(this);
        init();
        setupView();
    }

    public void init(){
        selectPicDialog = SelectPicDialog.newInstance();
        selectPicDialog.setCancelable(true);
    }

    public void setupView(){
        photo_btn.setOnClickListener(this);
        emation_btn.setOnClickListener(this);
        topic_btn.setOnClickListener(this);
        InputMethodManager methodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromInputMethod(newContent.getWindowToken(),0);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case 0:
                jumpToTakePic();
                break;
            case 1:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(imgFileUri!=null){
            outState.putString("cameraImageUri",imgFileUri.toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey("cameraImageUri")){
            imgFileUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this,resultCode+"~",Toast.LENGTH_LONG).show();
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case CAMERA_RESULT:
                    Uri uri = null;
                    Bitmap b = null;
                    String imgPath = null;
                    if(CompatibilityUtil.hasImageCaptureBug()){
                        Bundle extras = data.getExtras();
                        b = (Bitmap)extras.get("data");

                        imgPath = getImageFilePath_CameraBug();
                        if(imgPath.equals("")){
                            uri = saveCameraImage(b);
                        }
                        updateAttachedImage(uri);
                    }else{
                        if(data==null){
                            Log.v(TAG,"Camera return data intent is null");
                        }
                        updateAttachedImage(imagePath);
                    }
                    break;
                case PIC_RESULT:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.menu_pic){
            selectPicDialog.show(getSupportFragmentManager(),"selectpicDialog");
        }
    }
    //打开相机拍照
    public void jumpToTakePic(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String name = Constants.DEFAULT_IMAGE_STORE_DIR+Constants.FILE_PREX+
                DateTimeUtil.getFormatString(new Date(), Constants.FILE_SUBFIX_FORMAT)+".jpg";
        File out = new File(name);
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        imagePath = out.getAbsolutePath();
        startActivityForResult(intent,CAMERA_RESULT);
    }

    private void updateAttachedImage(Uri uri) {
        if (uri == null) {
            updateAttachedImage(null, 0);
            return;
        }

        String path = null;
        int rotate = 0;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int rotateIndex = cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION);
            if (cursor.moveToFirst()) {
                path = cursor.getString(columnIndex); //图片文件路径
                if (rotateIndex > 0) {
                    rotate = cursor.getInt(rotateIndex);
                }
            }
            cursor.deactivate();
            cursor.close();
            updateAttachedImage(path, rotate);
        } else { //第三方文件管理器，不规范，直接返回原始路径
            path = getFilePath(uri);
            updateAttachedImage(path);
        }
    }

    public void updateAttachedImage(String path){
        if(TextUtils.isEmpty(path)){
            updateAttachedImage(null,0);
        }

        int rotation = 0;
        if(Build.VERSION.SDK_INT>android.os.Build.VERSION_CODES.DONUT){
            rotation = ExifUtil.getExifRotation(path);
        }
        updateAttachedImage(path, rotation);
    }

    public void updateAttachedImage(String path,int rotation){
        if(TextUtils.isEmpty(path)){
            hasImageFile = false;
            imagePath = null;
            rotateDegrees = 0;
            if(thumbnail!=null){
                thumbnail.recycle();
                thumbnail = null;
            }
            if(prePic!=null){
                prePic.recycle();
                prePic = null;
            }
        }else{
            File imageFile = new File(path);
            imagePath = path;
            rotateDegrees = rotation;
            hasImageFile = imageFile.exists();
            Bitmap bitmap = ImageUtil.scaleImageFile(imageFile,Constants.IMAGE_THUMBNAIL_WIDTH);
            Bitmap rotated = ImageUtil.rotate(bitmap, rotateDegrees);
            prePic = ImageUtil.scaleImageFile(imageFile, Constants.IMAGE_THUMBNAIL_WIDTH);
            imagePreview.setImageBitmap(prePic);
            thumbnail = ImageUtil.extractThumbnail(rotated, 70, 68, ImageUtil.OPTIONS_RECYCLE_INPUT);
        }
        photo_btn.setImageBitmap(thumbnail);
    }

    /*
    * 存在照相bug的版本，采用获取相机文件夹中最新图片的方法
    * */
    private String getImageFilePath_CameraBug(){
        String imgPath = "";
        File dcimFolder = new File(Constants.DCIM_PATH);
        if(!dcimFolder.exists()){
            return imgPath;
        }

        File[] cameraFolders = dcimFolder.listFiles();
        if(cameraFolders ==null|| cameraFolders.length<1){
            return imgPath;
        }
        FileModifiedTimeComparator comparator = new FileModifiedTimeComparator();
        //Arrays.sort是升序，通过obj1.compare(obj2)返回负值来实现，所以自定义comparator来使日期最大
        //排在第一个
        Arrays.sort(cameraFolders,comparator);

        File cameraFolder = cameraFolders[0];
        if(!cameraFolder.isDirectory()){
            return imgPath;
        }
        File[] imgFiles = cameraFolder.listFiles();
        if(imgFiles==null||imgFiles.length<1){
            return imgPath;
        }
        Arrays.sort(imgFiles,comparator);
        //获取最新拍照图片文件
        File imgFile = imgFiles[0];
        imgPath = imgFile.getAbsolutePath();
        return imgPath;
    }

    private Uri saveCameraImage(Bitmap bitmap){
        String name = Constants.FILE_PREX+DateTimeUtil.getShortFormat(new Date())+".jpg";
        ContentResolver resolver = this.getContentResolver();
        String uriStr = MediaStore.Images.Media.insertImage(resolver,bitmap,name,null);
        return Uri.parse(uriStr);
    }

    private String getFilePath(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.getPath();
    }
}
