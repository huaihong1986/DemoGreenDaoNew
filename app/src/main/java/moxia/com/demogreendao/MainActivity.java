package moxia.com.demogreendao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import charles.nocompany.greendao.DBController;
import charles.nocompany.greendao.DaoMaster;
import charles.nocompany.greendao.DaoSession;
import charles.nocompany.greendao.Student;
import charles.nocompany.greendao.StudentDao;
import charles.nocompany.greendao.TestTable;
import charles.nocompany.greendao.TestTableDao;
import charles.nocompany.greendao.local.DBLocalController;
import charles.nocompany.greendao.local.History;
import charles.nocompany.greendao.local.HistoryDao;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import moxia.com.demogreendao.utils.DBUtils;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.inputEditText)
    EditText inputEditText;
    @Bind(R.id.showlv)
    ListView showlv;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ArrayAdapter<String> arrayAdapter;    //定义一个数组适配器对象
    private ArrayList<String> datastr = new ArrayList();
    private StringBuilder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setupDatabase();
//        listData();


        //创建默认的数据,并插入一条数据
        HistoryDao historyDao = DBLocalController.getDaoSession().getHistoryDao();
        History entity = new History();
        entity.setName("科罗拉多");
        entity.setImageUrl("http://www.baidu.com");
        historyDao.insert(entity);
        //拷贝外部DB文件到指定目录
        if(copyRawDB())
        //通过greendao查询外部db文件数据
            selDBData();
    }
    private void setupDatabase() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "testTable_db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public void add(View view){

        String str=inputEditText.getText().toString().trim();
        String comment="comment";
        TestTable table1= new TestTable(null,str,comment,new Date());
        daoSession.getTestTableDao().insert(table1);
        listData();
    }

    public void del(View view){
        String str=inputEditText.getText().toString().trim();
        DeleteQuery deleteQuery = daoSession.getTestTableDao().queryBuilder()
                .where(TestTableDao.Properties.Text.eq(str))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        listData();

    }

    public void update(View view){
        String comment="comment2";
        String str=inputEditText.getText().toString().trim();
        Query query = daoSession.getTestTableDao().queryBuilder()
                .where(TestTableDao.Properties.Text.eq(str))
                .orderAsc(TestTableDao.Properties.Date)
                .build();
        List<TestTable> list = query.list();
        if(list.size()>0) {
            TestTable table1 = list.get(0);
            table1.setComment(comment);
            daoSession.getTestTableDao().update(table1);
            listData();
        }

    }

    public void search(View view){
        String str=inputEditText.getText().toString().trim();
        Query query = daoSession.getTestTableDao().queryBuilder()
                .where(TestTableDao.Properties.Text.eq(str))
                .orderAsc(TestTableDao.Properties.Date)
                .build();
        List<TestTable> list = query.list();
        datastr.clear();
        for(int i=0;i<list.size();i++){
            TestTable table1 = list.get(i);
            datastr.add(table1.getText()+"&"+table1.getComment());
        }
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, datastr);
        showlv.setAdapter(arrayAdapter);
    }

    public void listData(){
        Query query = daoSession.getTestTableDao().queryBuilder()
                .orderAsc(TestTableDao.Properties.Date)
                .build();
        List<TestTable> list = query.list();
        datastr.clear();
        for(int i=0;i<list.size();i++){
            TestTable table1 = list.get(i);
            datastr.add(table1.getText()+"&"+table1.getComment());
        }
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, datastr);
        showlv.setAdapter(arrayAdapter);
    }


    private void selDBData() {
        StudentDao student = DBController.getDaoSession(DBController.DATABASE_SCHOOL_NAME).getStudentDao();
        List<Student> students = student.queryBuilder().list();
        builder = new StringBuilder();
        for (int i = 0; i < students.size(); i++)
        {
            builder.append(students.get(i).getName() + "---");
        }
        Toast.makeText(MainActivity.this, builder.toString(), Toast.LENGTH_SHORT).show();
    }

    private Boolean copyRawDB() {
        try
        {
            // 拷贝res/raw/xxxxdb.zip 到
            // /data/data/com.xinhang.mobileclient/databases/ 目录下面
            Toast.makeText(this,DBUtils.APK_DB_PATH,Toast.LENGTH_LONG).show();
            return DBUtils.copyRawDBToApkDb(MainActivity.this, R.raw.schooldb, DBUtils.APK_DB_PATH, DBUtils.ECMC_DB_NAME, true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }



}
