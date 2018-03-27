package com.ct7liang.regional;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AddressUtils {

    private AddressUtils (){}
    private static AddressUtils utils;
    public static AddressUtils getInstance() {
        if (utils == null) {
            utils = new AddressUtils();
        }
        return utils;
    }

    private PopupWindow mPWindow;

    private WeakReference<Activity> mReference;

    private ArrayList<Address> provinces;
    private ArrayList<Address> cities;
    private ArrayList<Address> counties;

    private AddressAdapter pAdapter;
    private CityAdapter cAdapter;
    private DistractAdapter dAdapter;

    private int pLastPosition;
    private int cLastPosition;
    private int dLastPosition;

    private StringBuffer p;
    private StringBuffer c;
    private StringBuffer d;

    public void showSelectWindow(View asView, Activity act, final OnAddressSelected listener){
        mReference = new WeakReference<>(act);
        final AddressDao dao = AddressDao.getDao(act);
        if (mPWindow!=null && mPWindow.isShowing()){
            mPWindow.dismiss();
        }else{
            View view = View.inflate(act, R.layout.test_popup_window, null);

            final TextView pTv = (TextView) view.findViewById(R.id.p);
            final TextView cTv = (TextView) view.findViewById(R.id.c);
            final TextView dTv = (TextView) view.findViewById(R.id.d);

            final ListView province = (ListView) view.findViewById(R.id.province);
            final ListView city = (ListView) view.findViewById(R.id.city);
            final ListView county = (ListView) view.findViewById(R.id.distract);
            provinces = AddressDao.getDao(act).queryProvince();
            pAdapter = new AddressAdapter(provinces);
            province.setAdapter(pAdapter);
            province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    provinces.get(pLastPosition).setIsSelect(false);
                    provinces.get(position).setIsSelect(true);
                    pLastPosition = position;
                    pAdapter.notifyDataSetChanged();
                    cities = dao.queryCity(provinces.get(position).getaId());
                    cAdapter = new CityAdapter(cities);
                    city.setAdapter(cAdapter);
                    county.setAdapter(new CityAdapter(null));

                    p = new StringBuffer();
                    p.append(provinces.get(position).getName());

                    pTv.setText(p.toString());
                    cTv.setText("");
                    dTv.setText("");
                }
            });
            city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        cities.get(cLastPosition).setIsSelect(false);
                    }catch (IndexOutOfBoundsException e){

                    }
                    cities.get(position).setIsSelect(true);
                    cLastPosition = position;
                    cAdapter.notifyDataSetChanged();
                    counties = dao.queryCity(cities.get(position).getaId());
                    dAdapter = new DistractAdapter(counties);
                    county.setAdapter(dAdapter);

                    c = new StringBuffer();
                    if (!cities.get(position).getName().equals("市辖区")&&!cities.get(position).getName().equals("县")){
                        c.append(cities.get(position).getName());
                    }
                    pTv.setText("");
                    cTv.setText(p.toString()+c.toString());
                    dTv.setText("");
                }
            });
            county.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        counties.get(dLastPosition).setIsSelect(false);
                    }catch (IndexOutOfBoundsException e){

                    }
                    counties.get(position).setIsSelect(true);
                    dLastPosition = position;
                    dAdapter.notifyDataSetChanged();

                    d = new StringBuffer();
                    if (!counties.get(position).getName().equals("不限")){
                        d.append(counties.get(position).getName());
                    }

                    pTv.setText("");
                    cTv.setText("");
                    dTv.setText(p.toString()+c.toString()+d.toString());

                    if (listener != null){
                        listener.onSelected(counties.get(position).getCode(), p.toString()+c.toString()+d.toString());
                    }
                }
            });
            mPWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mPWindow.setFocusable(true);
            mPWindow.setBackgroundDrawable(new BitmapDrawable());
            mPWindow.setOutsideTouchable(true);

            mPWindow.setAnimationStyle(R.style.designer_location_select_popup_window_anim);
            mPWindow.showAsDropDown(asView);

        }
    }

    /**
     * 省适配器
     */
    private class AddressAdapter extends BaseAdapter {
        private ArrayList<Address> list;
        public AddressAdapter(ArrayList<Address> list) {
            this.list = list;
        }
        @Override
        public int getCount() {
            if (list == null){
                return 0;
            }
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null){
                view = View.inflate(mReference.get(), R.layout.item_designer_location_select, null);
            }else {
                view = convertView;
            }
            TextView name = (TextView) view.findViewById(R.id.text1);
            name.setText(list.get(position).getName());
            if (list.get(position).getIsSelect()){
                name.setTextColor(Color.parseColor(Regional.SelectColor));
            }else{
                name.setTextColor(Color.parseColor(Regional.NormalColor));
            }
            return view;
        }
    }

    /**
     * 市适配器
     */
    private class CityAdapter extends BaseAdapter {
        private ArrayList<Address> list;
        public CityAdapter(ArrayList<Address> list) {
            this.list = list;
        }
        @Override
        public int getCount() {
            if (list == null){
                return 0;
            }
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null){
                view = View.inflate(mReference.get(), R.layout.item_designer_location_select, null);
            }else {
                view = convertView;
            }
            TextView name = (TextView) view.findViewById(R.id.text1);
            name.setText(list.get(position).getName());
            if (list.get(position).getIsSelect()){
                name.setTextColor(Color.parseColor(Regional.SelectColor));
            }else{
                name.setTextColor(Color.parseColor(Regional.NormalColor));
            }
            return view;
        }
    }

    /**
     * 县/区适配器
     */
    private class DistractAdapter extends BaseAdapter {
        private ArrayList<Address> list;
        public DistractAdapter(ArrayList<Address> list) {
            this.list = list;
        }
        @Override
        public int getCount() {
            if (list == null){
                return 0;
            }
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null){
                view = View.inflate(mReference.get(), R.layout.item_designer_location_select, null);
            }else {
                view = convertView;
            }
            TextView name = (TextView) view.findViewById(R.id.text1);
            name.setText(list.get(position).getName());
            if (list.get(position).getIsSelect()){
                name.setTextColor(Color.parseColor(Regional.SelectColor));
            }else{
                name.setTextColor(Color.parseColor(Regional.NormalColor));
            }
            return view;
        }
    }

    /**
     * 接口
     */
    public interface OnAddressSelected{
        void onSelected(String code, String name);
    }

}