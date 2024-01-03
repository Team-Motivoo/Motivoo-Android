<img src="https://i.ibb.co/jygwrmV/splah-logo.png" width="20%">

# Motivoo(모티부)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-8.0-green.svg)](https://gradle.org/)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-2022.3.1%20%28Giraff%29-green)](https://developer.android.com/studio)
[![minSdkVersion](https://img.shields.io/badge/minSdkVersion-24-red)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
[![targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-34-orange)](https://developer.android.com/distribute/best-practices/develop/target-sdk)

> 부모님의 건강이 걱정되시나요?

> 모티부와 함께 걷기로 건강을 챙겨보아요! 🏃‍♂️🏃‍♀️

## Development
### Libraries
- AndroidX
    - RecyclerView
    - ViewPager
    - ViewModel
    - Fragment
    - Security
    - Core & AppCompat
    - Splash Screen
    - Navigation

- Kotlin Libraries
    - kotlinx-serialization

- Hilt 
- Coil & Glide
- Timber
- Retrofit2
- Firebase (Analytics & Crahslytics)
- Material Components


### Test & Code analysis
- Ktlint
- Junit4 & Espresso

## Architecture
- Use SingleActivity
- MVVM
- 구글 권장 아키텍처
    - Presentation / Domain(Optional) / Data

<img width="760" src="https://i.ibb.co/2hD7DX1/2024-01-03-212304.png">


## Package Structure
```
├── 📁 app
│   ├── 📁 presentation
│   ├── 📁 data
│        ├── 📁 datasource
│        ├── 📁 model
│             ├── 📁 response
│             └── 📁 request
│        ├── 📁 repository
│        └── 📁 service 
│   ├── 📁 di
│   ├── 📁 domain
│        ├── 📁 model
│        └── 📁 repository
│   └── 📁 util
│        ├── 📁 binding
│        └── 📁 extension
└──
```


## Convention
- [Git Convention](https://gayeong04.notion.site/Android-Coding-Convention-e503175f631043fda4fe63180e6e867e?pvs=4)
- [Android Coding Convention](https://gayeong04.notion.site/Git-Convention-79ba08a6f1444d4aaf9a1f53ce1eb91c?pvs=4)

## Developer
|이준희(리드)|조관희|엄현지|김준서(명예 OB)|
|:-:|:-:|:-:|:-:|
|<img src="https://avatars.githubusercontent.com/u/113578158?v=4" width=200>|<img src="https://avatars.githubusercontent.com/u/90740783?v=4" width=200>|<img src="https://avatars.githubusercontent.com/u/113780698?v=4" width=200>|<img src="https://avatars.githubusercontent.com/u/108331578?v=4" width=200>|
|[@l2zh](https://github.com/l2zh)|[@Jokwanhee](https://github.com/Jokwanhee)|[@hyunjium](https://github.com/hyunjium)|[@giovannijunseokim](https://github.com/giovannijunseokim)|
<br/>