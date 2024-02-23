<img src="https://i.ibb.co/jygwrmV/splah-logo.png" width="20%">

# Motivoo(모티부)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-8.0-green.svg)](https://gradle.org/)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-2022.3.1%20%28Giraff%29-green)](https://developer.android.com/studio)
[![minSdkVersion](https://img.shields.io/badge/minSdkVersion-24-red)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
[![targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-34-orange)](https://developer.android.com/distribute/best-practices/develop/target-sdk)

> 부모님의 건강이 걱정되시나요? 부모님의 운동에 부담감을 느끼는 자녀들이 있을까? 어떤 방법으로 운동을 돕고 있을까? 에 대한 궁금증을 가지고 모티부의 서비스가 시작되었습니다. 모티부는 부모와 자녀에게 매일 맞춤형 미션을 부여하여, 부모의 운동 전 과정을 도와줍니다! 

> 모티부와 함께 걷기로 건강을 챙겨보아요! 🏃‍♂️🏃‍♀️

### Motivoo 핵심 가치
> 🧡 <strong>신뢰</strong> : 부모님의 운동은 믿고 맡길 수 있도록<br>
💛 <strong>동기 부여</strong> : 부모가 운동을 더이상 숙제처럼 느끼지 않도록<br>
💙 <strong>연결</strong> : 부모와 자녀가 서로 함께하고 있다고 느낄 수 있도록


## 📱*****ScreenShot*****

| <img width="200" src="https://github.com/Team-Motivoo/Motivoo-Android/assets/90740783/1154159a-cc71-4d15-996b-185190d722e0"/> | <img width="200" src="https://github.com/Team-Motivoo/Motivoo-Android/assets/90740783/03e88b0a-33a4-45e9-918b-8c7dc66cc55b"/> | 
<img width="200" src="https://github.com/Team-Motivoo/Motivoo-Android/assets/90740783/86d398b3-fb85-43ee-959a-0bdda7a1d57a"/> | 
<img width="200" src="https://github.com/Team-Motivoo/Motivoo-Android/assets/90740783/09a982d4-be0d-4ae5-bd98-48906c5e05a2"/> | <img width="200" src="https://github.com/Team-Motivoo/Motivoo-Android/assets/90740783/b4bbd953-c8c9-4d27-a68b-8694ec0993f0"/> 

## How to use Deisgn Component?
 [learn from README 👀](/motivoo_design/README.md)


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
- Firebase
    - Analytics & Crahslytics
    - Realtime Database
- Material Components
- OkHttp3
- Kakao login
- Lottie
- Aws s3
- Java 8+ API desugaring support
- LeakCanary

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
- [Git Convention](https://gayeong04.notion.site/Git-Convention-79ba08a6f1444d4aaf9a1f53ce1eb91c?pvs=4)
- [Android Coding Convention](https://gayeong04.notion.site/Android-Coding-Convention-e503175f631043fda4fe63180e6e867e?pvs=4)

## Projects Progress Board
- 1️⃣ [Phase1](https://github.com/orgs/Team-Motivoo/projects/1/views/1)
    - 합숙 전 : 프로젝트 기초세팅, 뷰 관련 작업
- 2️⃣ [Phase2](https://github.com/orgs/Team-Motivoo/projects/3)
    - 합숙 1주차 : 뷰 관련 작업 + 서버 연결
- 3️⃣ [Phase3](https://github.com/orgs/Team-Motivoo/projects/4)
    - 합숙 2주차 : 서버 연결 마무리 + QA + 1차 릴리즈

## Developer
|이준희(리드)|조관희|엄현지|김준서(명예 OB)|
|:-:|:-:|:-:|:-:|
|<img src="https://avatars.githubusercontent.com/u/113578158?v=4" width=200>|<img src="https://avatars.githubusercontent.com/u/90740783?v=4" width=200>|<img src="https://avatars.githubusercontent.com/u/113780698?v=4" width=200>|<img src="https://avatars.githubusercontent.com/u/108331578?v=4" width=200>|
|[@l2zh](https://github.com/l2zh)|[@Jokwanhee](https://github.com/Jokwanhee)|[@hyunjium](https://github.com/hyunjium)|[@giovannijunseokim](https://github.com/giovannijunseokim)|
<br/>
