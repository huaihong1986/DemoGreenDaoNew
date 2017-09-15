package charles.nocompany.greendao;

import android.content.Context;

import moxia.com.demogreendao.MainApplication;


/**
 * 外部数据库控制类
 */
public class DBController
{
    private static DaoMaster daoMasterEcmc;
    
    private static DaoMaster daoMasterSchool;
    
    // 默认DB
    private static DaoSession daoSessionDefault;
    
    // 拷贝的db
    private static DaoSession daoSchoolSession;
    
    /**
     * 默认数据库名称:localdata
     */
    public static final String DATABASE_NAME = "localdata.db";
    
    /**
     * 拷贝数据库名称:school
     */
    public static final String DATABASE_SCHOOL_NAME = "school.db";
    
    private static DaoMaster obtainMaster(Context context, String dbName)
    {
        return new DaoMaster(new DaoMaster.DevOpenHelper(context, dbName, null).getWritableDatabase());
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
    
    private static DaoMaster getSchoolDaoMaster(Context context, String dbName)
    {
        if (dbName == null)
            return null;
        if (daoMasterSchool == null)
        {
            daoMasterSchool = obtainMaster(context, dbName);
        }
        return daoMasterSchool;
    }
    
    /**
     * 取得DaoSession
     *
     * @return
     */
    public static DaoSession getDaoSession(String dbName)
    {
        
        if (daoSchoolSession == null)
        {
            daoSchoolSession = getSchoolDaoMaster(MainApplication.getIns(), dbName).newSession();
        }
        return daoSchoolSession;
    }
    
    /**
     * 默认操作localdata数据库
     */
    public static DaoSession getDaoSession()
    {
        
        if (daoSessionDefault == null)
        {
            daoSessionDefault = getDaoMaster(MainApplication.getIns(), DATABASE_NAME).newSession();
        }
        return daoSessionDefault;
    }
}
