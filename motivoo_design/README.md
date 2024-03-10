# Motivoo Design Component 🎨
- [Circle ProgressBar](#circle-progressbar)
    - [MotivooPieChart](#motivoopiechart)
    - [MotivooOtherPieChart](#motivoootherpiechart)

## Circle ProgressBar
<img width=250px height=400px src="images/motivoo_circle_progressbar.gif">

### MotivooPieChart

- XML Code
```kotlin
<com.android.motivoo_design.MotivooPieChart
    android:id="@+id/motivoo_my_pie_chart"
    stepCount="@{vm.stepCount}"
    stepCountGoal="@{vm.stepCountGoal}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:chartUserType="@{vm.userType}"
    iconEnabled="@{vm.isHighFive}"
    app:layout_constraintEnd_toEndOf="@id/motivoo_other_pie_chart"
    app:layout_constraintStart_toStartOf="@id/motivoo_other_pie_chart"
    app:layout_constraintTop_toTopOf="@id/motivoo_other_pie_chart"
    app:progressBackgroundColor="@color/blue_400_65DBFF" />
```

`stepCount` : 걸음 수      
`stepCountGoal` : 목표 걸음 수   
: 걸음 수 / 목표 걸음 수 = 퍼센트(%), 왼쪽 프로그래스 바 동적 움직임 판정    
`iconEnabled` : 아이콘 활성유무        
`app:chartUserType` : 아이콘 자녀/부모   
```
<attr name="chartUserType" format="enum">
    <enum name="Child" value="1"/>
    <enum name="Parent" value="2"/>
</attr>
````      
`app:progressBackgroundColor` : 왼쪽에 위치한 원형 프로그래스 바 백그라운드 색상
```
<attr name="progressBackgroundColor" format="reference|color"/>
```

- Programmatically Code

```kotlin
binding.customView.userType = Parent // or Child
```
<img width=100px height=200px src="images/motivoo_user_image.png">

`Parent` : 유저 타입이 부모   
`Child` : 유저 타입이 자식


### MotivooOtherPieChart

- XML Code
```kotlin
<com.android.motivoo_design.MotivooOtherPieChart
    android:id="@+id/motivoo_other_pie_chart"
    stepCount="@{vm.otherStepCount}"
    stepCountGoal="@{vm.otherStepCountGoal}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="24dp"
    app:centerCircleColor="@color/white_FFFFFF"
    app:chartUserType="@{vm.anotherUserType}"
    iconEnabled="@{vm.isHighFive}"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/tv_selected_mission_home_today_exercise_mission"
    app:progressBackgroundColor="@color/gray_600_707276"
    app:progressInnerColor="@color/gray_100_F4F5F9" />
```
`stepCount` : 걸음 수      
`stepCountGoal` : 목표 걸음 수   
: 걸음 수 / 목표 걸음 수 = 퍼센트(%), 왼쪽 프로그래스 바 동적 움직임 판정    
`iconEnabled` : 아이콘 활성유무        
`app:chartUserType` : 아이콘 자녀/부모   
```
<attr name="chartUserType" format="enum">
    <enum name="Child" value="1"/>
    <enum name="Parent" value="2"/>
</attr>
````   
`app:progressInnerColor` : 전체 원형 프로그래스 바 백그라운드 색상

`app:progressBackgroundColor` : 오른쪽에서 올라오는 원형 프로그래스 바 백그라운드 색상

`app:centerCircleColor` : 원형 프로그래스 바 가운데 위치한 원 색상



- Programmatically Code

```kotlin
binding.customViewOther.userType = Child // or Parent
```
<img width=100px height=200px src="images/motivoo_other_image.png">

`Parent` : 유저 타입이 부모   
`Child` : 유저 타입이 자식