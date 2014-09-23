ImageIndicatorTab
===============

## Introduction

ImageIndicatorTab is a widget to make tabs for ViewPager with indicator image you want to show.  
This library supports Android API from 9 to 19, and both support v4 and v13 ViewPager .

![Screenshot 1](https://raw.github.com/wiki/chibatching/ImgIndicatorTab/images/screenshot.png)

You can add the tab in the middle or bottom of your layout as you wish.

![Screenshot 3](https://raw.github.com/wiki/chibatching/ImgIndicatorTab/images/screenshot3.png)
![Screenshot 2](https://raw.github.com/wiki/chibatching/ImgIndicatorTab/images/screenshot2.png)



## Usage

In layout xml.

```XML
<com.chibatching.imgindicatortab.ImgIndicatorTab
    xmlns:imgtab="http://schemas.android.com/apk/res-auto"
    android:id="@+id/indicatorTab"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    android:background="#ffffff"
    imgtab:selectedTextColor="#3333aa"
    imgtab:deselectedTextColor="#888888"
    imgtab:indicatorDrawable="@drawable/rect"
    imgtab:fitIndicatorWithTabWidth="true" />
```

In activity's onCreate (or fragment's onCreateView), ImgIndicatorTab should be attached ViewPager.

```Java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sample_tab);

    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.pager);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    // Set up the ImgIndicatorTab with ViewPager
    ImgIndicatorTab imgIndicatorTab = (ImgIndicatorTab) findViewById(R.id.indicatorTab);
    imgIndicatorTab.setViewPager(mViewPager);
}
```

## XML parameters
In layout xml, you can set some parameters.

|param|value|
|:--:|:--:|
|selectedTextColor|Text color when tab selected|
|deselectedTextColor|Text color when tab **not** selected|
|indicatorDrawable|Indicator image drawable|
|fitIndicatorWithTabWidth|Whether scale indicator image to fit tab width<br/>(but not exceed tab height)|

## License
This library is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
