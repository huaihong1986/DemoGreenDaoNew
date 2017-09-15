package moxia.com.demogreendao;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class MainApplication extends Application
{
    /**
     * 单例对象
     */
    private static MainApplication instance;

    private static PackageInfo packInfo;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    public static MainApplication getIns()
    {
        return instance;
    }

    /**
     * 获取apk包名路径
     */
    @SuppressLint("Override")
    public String getDataDirLocal()
    {
        if (packInfo == null)
            getAppInfo();
        return packInfo != null && packInfo.applicationInfo != null ? packInfo.applicationInfo.dataDir : "";
    }

    private void getAppInfo()
    {
        // 获取packageManager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try
        {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}

