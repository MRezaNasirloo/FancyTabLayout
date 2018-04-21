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

## License
```
MIT License

Copyright (c) 2018 M. Reza Nasirloo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```