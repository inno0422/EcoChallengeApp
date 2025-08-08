package com.inno0422.myapp.ui.challenge

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 챌린지 데이터 클래스 수정: 참여 여부 상태 추가
data class Challenge(
    val id: Int, // 고유 식별자 추가
    val title: String,
    val description: String,
    val progress: Float,
    val total: Float,
    val carbonSaved: String,
    val points: Int,
    var isNew: Boolean = true, // isNew는 참여 전 상태를 나타냄
    var isParticipating: Boolean = false // 참여 중 상태를 나타내는 필드
)

class ChallengeViewModel : ViewModel() {
    private val _challenges = MutableStateFlow(
        listOf(
            Challenge(1, "일주일 대중교통 이용", "5/7일", 5f, 7f, "3.5kg CO₂", 150, isNew = false, isParticipating = true),
            Challenge(2, "텀블러 사용하기", "3/5회", 3f, 5f, "0.8kg CO₂", 80, isNew = false, isParticipating = true),
            Challenge(3, "3일 채식 도전", "식사하기", 0f, 3f, "4.2kg CO₂", 200),
            Challenge(4, "절전 마스터", "20% 줄이기", 0f, 1f, "7.0kg CO₂", 300)
        )
    )
    val challenges: StateFlow<List<Challenge>> = _challenges.asStateFlow()

    // 챌린지 참여하기 함수
    fun participateInChallenge(challengeId: Int) {
        val updatedList = _challenges.value.map { challenge ->
            if (challenge.id == challengeId) {
                // 해당 챌린지의 상태를 변경
                challenge.copy(isNew = false, isParticipating = true)
            } else {
                challenge
            }
        }
        _challenges.value = updatedList
    }
}