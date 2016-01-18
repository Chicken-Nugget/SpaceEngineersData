package chickennugget.spaceengineersdata.cards.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

public class CacheUtil {

    private static CacheUtil sInstance;
    protected LruCache<String, Bitmap> mMemoryCache;

    protected CacheUtil() {
    }

    public static CacheUtil getInstance() {
        if (sInstance != null)
            return sInstance;
        else
            return sInstance = new CacheUtil();
    }

    public static LruCache<String, Bitmap> getMemoryCache() {
        return CacheUtil.getInstance().mMemoryCache;
    }

    public static void putMemoryCache(LruCache<String, Bitmap> memoryCache) {
        CacheUtil.getInstance().mMemoryCache = memoryCache;
    }
}
