package com.inno0422.myapp.ui.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inno0422.myapp.ui.theme.EcoChallengeTheme
import com.inno0422.myapp.ui.theme.Yellow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable

@Composable
fun CommunityScreen(viewModel: CommunityViewModel = viewModel()) {
    val leaderboard by viewModel.leaderboard.collectAsState()
    val feed by viewModel.feed.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("커뮤니티", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            LeaderboardCard(leaderboard)
        }

        item {
            Text("커뮤니티 피드", style = MaterialTheme.typography.titleLarge)
        }

        items(feed) { post ->
            FeedPostCard(post = post, onLikeClick = { viewModel.toggleLike(post.id) })
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: 성과 공유하기 */ },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("내 성과 공유하기")
            }
        }
    }
}

@Composable
fun LeaderboardCard(leaderboard: List<LeaderboardUser>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("이번 주 리더보드", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            leaderboard.forEach { user ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${user.rank}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (user.rank <= 3) Yellow else Color.Unspecified,
                        modifier = Modifier.width(30.dp)
                    )
                    Text(user.name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    Text(user.carbonSaved, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun FeedPostCard(post: FeedPost, onLikeClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.LightGray))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(post.author, fontWeight = FontWeight.Bold)
                    Text(post.time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(post.content, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 좋아요 버튼
                IconButton(onClick = onLikeClick) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Likes",
                        tint = if (post.isLiked) Color.Red else Color.Unspecified
                    )
                }
                Text("${post.likes}", modifier = Modifier.padding(start = 4.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Icon(Icons.Default.ModeComment, contentDescription = "Comments")
                Text("${post.comments}", modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    EcoChallengeTheme {
        CommunityScreen()
    }
}