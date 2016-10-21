package live.xhf.asus.clivetelecast.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import live.xhf.asus.clivetelecast.bean.Student;

/**
 * Created by asus on 2016/10/19.
 */
public class DbManager {

    private MySqlDataBaseHelper helper;
    private SQLiteDatabase database;

    public DbManager(Context context) {
        helper = new MySqlDataBaseHelper(context);

        database = helper.getWritableDatabase();
    }

    //添加数据
    public void insert(Student stu){

        try{
            database.beginTransaction();
                ContentValues values = new ContentValues();
                values.put("midheadimg",stu.midheadimg);
                values.put("name",stu.name);
                values.put("place",stu.place);
                values.put("video",stu.video);
                database.insert("student",null,values);
                //关闭
                database.close();
            //设置事务成功
            database.setTransactionSuccessful();
        }catch (Exception e){

        }finally {
            database.endTransaction();
        }
    }

    //查询数据
    public  List<Student> select(){

        List<Student> list = new ArrayList<Student>();

        Cursor cursor = database.query("student",new String[]{"id","midheadimg","name","place","video"},null,null,null,null,null);
        while(cursor.moveToNext()){
            Student student = new Student();
            student.id = cursor.getInt(cursor.getColumnIndex("id"));
            student.midheadimg = cursor.getString(cursor.getColumnIndex("midheadimg"));
            student.name = cursor.getString(cursor.getColumnIndex("name"));
            student.place = cursor.getString(cursor.getColumnIndex("place"));
            student.video = cursor.getString(cursor.getColumnIndex("video"));
            list.add(student);
        }
        cursor.close();
        return list;
    }

    //删除
    public void stuDelete(int id){
        database.delete("student","_id = ?",new String[]{String.valueOf(id)});
    }

}
