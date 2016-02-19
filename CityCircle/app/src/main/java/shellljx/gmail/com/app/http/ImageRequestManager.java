package shellljx.gmail.com.app.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2015/5/20.
 */
public class ImageRequestManager {

    private static ImageRequestManager manager;

    private RequestQueue mQueue;
    private ImageLoader mImageLoader;
    private BitmapCache bitmapCache;
    private HttpRequestManager mHttpRequestManager;


    public ImageRequestManager(Context context){
        mQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mQueue,new BitmapCache());
        mHttpRequestManager = HttpRequestManager.getInstance(context);
        bitmapCache = new BitmapCache();
    }

    public static ImageRequestManager getInstance(Context context){
        if(manager==null){
            manager = new ImageRequestManager(context.getApplicationContext());
        }
        return manager;
    }

    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }


    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }
}
