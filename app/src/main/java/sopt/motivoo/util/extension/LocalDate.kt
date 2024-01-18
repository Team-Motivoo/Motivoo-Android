package sopt.motivoo.util.extension

import java.time.LocalDate

/** Convert LocalDate to Pretty String **/
fun LocalDate.toPrettyString(): String = "${year}년 ${monthValue}월 ${dayOfMonth}일"
val LocalDate.prettyString get() = "${year}년 ${monthValue}월 ${dayOfMonth}일"
