# Fancy TabLayout

[ ![Download](https://api.bintray.com/packages/mrezanasirloo/maven/fancytablayout/images/download.svg) ](https://bintray.com/mrezanasirloo/maven/fancytablayout/_latestVersion)

![Preview gif](https://media.giphy.com/media/3NvQrT1H08ySikNpAl/giphy.gif)

How to use:
```java
FancyTabLayout fancyTabLayout =  findViewById(R.id.tabs);

fancyTabLayout.addTab(fancyTabLayout.newTab().setText("ONE").setIcon(R.drawable.ic_room_24dp));
fancyTabLayout.addTab(fancyTabLayout.newTab().setText("TWO").setIcon(R.drawable.ic_backup_24dp));
fancyTabLayout.addTab(fancyTabLayout.newTab().setText("THREE").setIcon(R.drawable.ic_brightness_2_24dp));
fancyTabLayout.addTab(fancyTabLayout.newTab().setText("FOUR").setIcon(R.drawable.ic_shopping_cart_24dp));

ViewPager viewPager = findViewById(R.id.vp);
PagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
viewPager.setAdapter(myPagerAdapter);
fancyTabLayout.setViewPager(viewPager);
```

Download
```groovy
implementation 'com.mrezanasirloo:fancytablayout:0.9.2'
```