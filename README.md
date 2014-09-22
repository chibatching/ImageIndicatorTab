ImageIndicatorTab
===============

ImageIndicatorTab is a library to make tabs for ViewPager with indicator image you want to show.  
This library supports Android API from 9 to 19, and both support v4 and v13 ViewPager .

![Screenshot 1](https://raw.github.com/wiki/chibatching/ImgIndicatorTab/images/screenshot.png)

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

In activity or fragment, ImgIndicatorTab should be attached ViewPager.

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

## License
This library is licensed under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
