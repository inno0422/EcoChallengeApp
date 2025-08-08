package com.inno0422.myapp.ui.community

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 피드 게시글 데이터 클래스 수정: 고유 ID와 좋아요 상태 추가
data class FeedPost(
    val id: Int, // 고유 식별자 추가
    val author: String,
    val content: String,
    val time: String,
    val comments: Int,
    var likes: Int,
    var isLiked: Boolean = false // 좋아요 상태를 나타내는 필드
)

// 리더보드 데이터 클래스는 수정할 필요가 없습니다.
data class LeaderboardUser(val rank: Int, val name: String, val carbonSaved: String)

class CommunityViewModel : ViewModel() {
    // 리더보드 데이터
    private val _leaderboard = MutableStateFlow(
        listOf(
            LeaderboardUser(1, "김환경", "12.5kg CO₂ 절약"),
            LeaderboardUser(2, "박지구", "10.2kg CO₂ 절약"),
            LeaderboardUser(3, "이친환", "8.7kg CO₂ 절약")
        )
    )
    val leaderboard: StateFlow<List<LeaderboardUser>> = _leaderboard.asStateFlow()

    // 피드 게시글 데이터
    private val _feed = MutableStateFlow(
        listOf(
            FeedPost(1, "김환경", "오늘 자전거로 출근했어요! 날씨도 좋고 운동도 되고 일석이조 🚲", "2시간 전", 3, 12),
            FeedPost(2, "박지구", "일주일 채식 챌린지 완료! 생각보다 맛있는 요리들이 많네요 🥗", "5시간 전", 5, 8)
        )
    )
    val feed: StateFlow<List<FeedPost>> = _feed.asStateFlow()

    // 좋아요 토글 함수
    fun toggleLike(postId: Int) {
        val updatedFeed = _feed.value.map { post ->
            if (post.id == postId) {
                // 좋아요 상태에 따라 좋아요 수와 isLiked 상태를 변경
                if (post.isLiked) {
                    post.copy(likes = post.likes - 1, isLiked = false)
                } else {
                    post.copy(likes = post.likes + 1, isLiked = true)
                }
            } else {
                post
            }
        }
        _feed.value = updatedFeed
    }
}