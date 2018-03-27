# Regional

allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}

compile 'com.github.Ct7Liang:Regional:1.0.1'

Regional.init(
                this,
                "项目包名",
                "选中的字体颜色",
                "未选中的字体颜色",
                "标题文字颜色",
                "标题背景颜色",
                "地区列表背景颜色",
                "日志打印标签"
              );

              颜色参数可选为null 代表使用默认颜色
              日志标签参数可选为null 代表使用默认标签

使用:
AddressUtils
    .getInstance()
    .showSelectWindow(
        "在指定的view下方显示",
        context,
        new AddressUtils.OnAddressSelected() {
            @Override
            public void onSelected(String code, String name) {
                Toast.makeText(context, code + " -- " + name, Toast.LENGTH_SHORT).show();
            }
        }
    );