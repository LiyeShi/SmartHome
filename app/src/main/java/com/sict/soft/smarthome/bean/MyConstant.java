package com.sict.soft.smarthome.bean;

public class MyConstant {
	public static final String CONTENT_TYPE_INSERT="com.android.lelao/android.insert";
	public static final String CONTENT_TYPE_EDIT="com.android.lelao/android.edit";
	//发送意图时设定的类地址，在manifest中的intent-filter有相关设置，是一个标识
	//使用这个则不用指定具体启动哪个Activity，系统会自动寻找符合intent-filter的条件的
	//activity启动
	public static final String CONTENT_URI="content://com.android.lelao";
}
