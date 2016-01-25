package chickennugget.spaceengineersdata.material.app;

import android.app.Application;

public class RunApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ThemeManager.init(this, 2, 0, null);
    }
}
