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

| <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/f9c14055-586b-4727-997e-e3f5cfefdc9b"/> | <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/b0a6aed7-af3f-4a40-8d55-c65232e56a75"/> | <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/bcd63b80-d818-48dd-9cca-d0ddbcf83871"/> | <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/ad3049cc-9d22-40f1-8d93-f99f5952e335"/> | <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/93e8e3bb-bf79-4d71-a18a-e0341ccaa54d"/> |
| :---: | :---: | :---: |:------------------------------------------------------------------------------------------------------------------------------:| :---:|
|`로그인`|`회원가입` | `대분류 생성`|`투두 생성`|                                                        `투두 체크`                                                        |
| <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/39f689ee-d399-4c44-9253-ff377c877e0d"/> | <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/b9d30cf9-5c2b-444a-8cea-61cc8e0fb39e"/> | <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/7da6d736-2470-41eb-892a-2363f7ca86fe"/> | <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/f46d7ca4-25a0-425f-a700-d57c1b6a4c27"> | <img width="200" src="https://github.com/DGU-SE-HOW-TODO/HowDroid/assets/113578158/7ce71eed-57a5-447b-ac1b-934b0ae49161">
|`나만의 실패태그 만들기`|`실태패그 달기`|`미루기`|`투두 고정`|`통계/피드백`| 



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
