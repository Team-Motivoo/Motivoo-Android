package sopt.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

@RunWith(AndroidJUnit4::class)
class MotivooTest {
    private lateinit var server: MockWebServer
    private lateinit var motivooApi: MotivooApi
    private lateinit var retrofit: Retrofit

    val homeResponse = """
        {
            "code": 200,
            "message": "미션 걸음 수 달성 현황 정보 조회에 성공했습니다.",
        		"success": true
        		"data": {
        				"user_type": "자녀", 
        				"user_id": 5,    
        				"user_goal_step_count": 100,
        				"opponent_user_id": 4,
        				"opponent_user_goal_step_count": 100,  

        				"is_step_count_completed": true,  
        				"is_opponent_user_withdraw": false,
        				"is_mission_img_completed": true  
        		}
        }
    """.trimIndent()


    @Before
    fun `서버_초기화`() {
        server = MockWebServer()
        server.start()

        retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        motivooApi = retrofit.create(MotivooApi::class.java)
    }

    @After
    fun `서버_다운`() {
        server.shutdown()
    }

    @Test
    fun `오늘의_미션_가져오기`() = runTest {
        val response = """
            {
                "code": 201,
                "message": "오늘의 미션을 조회하는 데 성공했습니다.",
                "success": true,
                "data": {

                    "is_choice_finished": false,

                    "mission_choice_list": [
                        {
                            "mission_id": 60,
                            "mission_content": "11000걸음 걷고\n힙브릿지 30회 하기",
                            "mission_icon_url": "url"
                        },
                        {
                            "mission_id": 211,
                            "mission_content": "7000걸음 걷고\n벽스쿼트 30초 하기",
                            "mission_icon_url": "url"
                        }
                    ],
                    "today_mission": null,
                    "date": "2024년 1월 18일 목요일"
                }
            }
        """.trimIndent()

        server.enqueue(MockResponse().setBody(response))

        val mApi = motivooApi.getCurrentMissionToday()

        assertNotNull(mApi)
        assertEquals(mApi.data.date, "2024년 1월 18일 목요일")
    }
}

interface MotivooApi {
    @GET("currentMissionToday?serviceKey=abcsd")
    suspend fun getCurrentMissionToday(): MissionTodayDTO
}

data class MissionTodayDTO(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: MissionTodayDataDTO,
)

data class MissionTodayDataDTO(
    @SerializedName("is_choice_finished")
    val is_choice_finished: Boolean,
    @SerializedName("mission_choice_list")
    val mission_choice_list: List<MissionDataDTO>,
    @SerializedName("today_mission")
    val today_mission: TodayMissionDTO?,
    @SerializedName("date")
    val date: String,
)

data class MissionDataDTO(
    @SerializedName("mission_id")
    val mission_id: Int,
    @SerializedName("mission_content")
    val mission_content: String,
    @SerializedName("mission_icon_url")
    val mission_icon_url: String,
)

data class TodayMissionDTO(
    @SerializedName("mission_content")
    val mission_content: String,
    @SerializedName("mission_description")
    val mission_description: String,
    @SerializedName("mission_step_count")
    val mission_step_count: Int,
    @SerializedName("mission_quest")
    val mission_quest: String,
)