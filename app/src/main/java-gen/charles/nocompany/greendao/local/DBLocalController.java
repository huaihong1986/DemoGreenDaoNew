package charles.nocompany.greendao.local;

import android.content.Context;

import moxia.com.demogreendao.MainApplication;

/**
 * 本地数据库控制类
 */
public class DBLocalController
{
    private static DaoMaster daoMasterEcmc;
    
    //默认DB
    private static DaoSession daoSessionEcmc;
    
    /**
     * 
     * 数据库名称:localdata
     */
    public static final String DATABASE_NAME = "history.db";
    
    private static DaoMaster obtainMaster(Context context, String dbName)
    {
        return new DaoMaster(new DaoMaster.DevOpenHelper(context, dbName, null).getWritableDatabase());
//        return new DaoMaster(new MySQLiteOpenHelper(context, dbName, null).getWritableDatabase());
    }
    
    private static DaoMaster getDaoMaster(Context context, String dbName)
    {
        if (dbName == null)
            return null;
        if (daoMasterEcmc == null)
        {
            daoMasterEcmc = obtainMaster(context, dbName);
        }
        return daoMasterEcmc;
    }
    
    /**
     * 取得DaoSession
     *
     * @return
     */
    public static DaoSession getDaoSession(String dbName)
    {
        
        if (daoSessionEcmc == null)
        {
            daoSessionEcmc = getDaoMaster(MainApplication.getIns(), dbName).newSession();
        }
        return daoSessionEcmc;
    }
    
    /**
     * 默认操作localdata数据库
     */
    public static DaoSession getDaoSession()
    {
        
        if (daoSessionEcmc == null)
        {
            daoSessionEcmc = getDaoMaster(MainApplication.getIns(), DATABASE_NAME).newSession();
        }
        return daoSessionEcmc;
    }
}
