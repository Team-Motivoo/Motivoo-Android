package com.android.utils

sealed class PieChartUserType
object Child : PieChartUserType()
object Parent : PieChartUserType()