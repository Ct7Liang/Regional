package com.ct7liang.regional;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class AddressDao {

    private AddressHelper helper;
    private AddressDao(Context ctx){
        helper = new AddressHelper(ctx);
    }
    private static AddressDao hd;
    public synchronized static AddressDao getDao(Context ctx) {
        if (hd == null) {
            return new AddressDao(ctx);
        }
        return hd;
    }

    /**
     * 查询省一级
     */
    public ArrayList<Address> queryProvince(){
        ArrayList<Address> list = new ArrayList<>();
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("address_dict", new String[]{"name", "code", "id"}, "parentId = ?", new String[]{"0"}, null, null, null);
            while(cursor.moveToNext()) {
                list.add(new Address(cursor.getString(1), cursor.getString(0), cursor.getString(2)));
            }
        }catch (Exception e){
            Log.e(Regional.TAG, "查询省失败");
        }
        return list;
    }

    /**
     * 根据parentId查询子城市
     * @param id
     */
    public ArrayList<Address> queryCity(String id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("address_dict", new String[]{"name", "code", "id"}, "parentId = ?", new String[]{id}, null, null, null);
        ArrayList<Address> list = new ArrayList<>();
        while(cursor.moveToNext()) {
            list.add(new Address(cursor.getString(1), cursor.getString(0), cursor.getString(2)));
        }
        return list;
    }
}