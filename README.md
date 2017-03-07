
## JeroEditText

显示字数限制的EditText

### 1. 效果展示 

![Alt text](https://github.com/ijero/JeroEditText/blob/master/imgs/Demo.gif "效果展示")

### 2. 使用方法 

如同普通的EditText一样使用

xml:

	<cn.ijero.library.JeroEditText
	    android:id="@+id/jet_main_normal"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    android:layout_below="@+id/tv_main_title_normal"
	    android:layout_marginTop="10dp"
	    android:hint="@string/hint_jet_narmal"
	    android:inputType="textMultiLine"
	    android:lines="5"
	    android:padding="10dp"
	    app:jet_defTextColor="@color/colorDefaultText"
	    app:jet_defTextSize="15sp"
	    app:jet_maxLength="10"
	    app:jet_maxTextColor="@color/colorFillText" />


附加属性：

	app:jet_defTextSize - 默认字体大小
	app:jet_defTextColor - 默认显示颜色
	app:jet_maxLength - 最大字符串限制
	app:jet_maxTextColor - 最大字符串限制的颜色
	app:jet_showMaxTip - 是否显示提示文字，为false时跟普通的EditText效果一样
