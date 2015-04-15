package com.isme.gamble.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库记录 - 点数 - 等
 *      先分析-统计所有数据
 * @author and
 *
 */
public class DataBaseRecord extends SQLiteOpenHelper {

	private static final String NAME = "game.db";
	private static int VERSION = 1;
	
	private SQLiteDatabase db;
	
	public DataBaseRecord(Context context) {
		super(context, NAME, null, VERSION);
		
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String drop = "DROP TABLE IF EXISTS t_cards";
		db.execSQL(drop);
		
		StringBuffer sql = new StringBuffer();
		sql.append("CREATE TABLE IF NOT EXISTS t_cards(  			 	");
		sql.append("			id INTEGER PRIMARY KEY AUTOINCREMENT,	");
		sql.append("			user_name TEXT,							");
		sql.append("			point_a INTEGER,						");
		sql.append("			color_a INTEGER,						");
		sql.append("			point_b INTEGER,						");
		sql.append("			color_b INTEGER,						");
		sql.append("			point_c INTEGER,						");
		sql.append("			color_c INTEGER,						");
		sql.append("			max     INTEGER,						");
		sql.append("			status  INTEGER,						");
		sql.append("			win     INTEGER							");
		sql.append(")        								      		");
		
		db.execSQL(sql.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	/**
	 * 自定义 SQL 方法
	 * 
	 * 执行 SQL 语句，可执行		插入    删除    更新
	 * @param sql    SQL的执行语句(不含元素)
	 * @param bindArgs  SQL执行语句中的元素，如果SQL执行语句中含有占位符”？“, 就将该参数替换  ？
	 */
	public void executeSQL(String sql, Object... bindArgs){
		db.execSQL(sql,bindArgs);
	}
	
	/**
	 * 自定义 SQL 方法
	 * 
	 * 执行 SQL 语句        	查找
	 * @param sql		SQL的执行语句(不含元素)
	 * @param selectionArgs   SQL执行语句中的元素，如果SQL执行语句中含有占位符”？“, 就将该参数替换  ？
	 * @return    Cursor是一个接口
	 */
	public Cursor executeQuery(String sql, String... selectionArgs){
		return db.rawQuery(sql, selectionArgs);
		
	}
	
}
