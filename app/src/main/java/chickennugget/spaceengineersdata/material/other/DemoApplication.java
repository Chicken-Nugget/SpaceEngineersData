package chickennugget.spaceengineersdata.material.other;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import chickennugget.spaceengineersdata.BuildConfig;
import chickennugget.spaceengineersdata.material.app.ThemeManager;

public class DemoApplication extends Application {

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        DemoApplication application = (DemoApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG)
            refWatcher = LeakCanary.install(this);
        ThemeManager.init(this, 2, 0, null);
    }
}
