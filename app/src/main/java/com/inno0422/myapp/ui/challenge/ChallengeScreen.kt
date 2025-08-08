package com.inno0422.myapp.ui.challenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.inno0422.myapp.ui.theme.LightGreen
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChallengeScreen(viewModel: ChallengeViewModel = viewModel()) {
    val challenges by viewModel.challenges.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("탄소 감축 챌린지", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        val participatingChallenges = challenges.filter { it.isParticipating }
        if (participatingChallenges.isNotEmpty()) {
            item {
                Text("진행 중인 챌린지", style = MaterialTheme.typography.titleLarge)
            }
            items(participatingChallenges) { challenge ->
                ChallengeCard(challenge, onParticipateClick = {})
            }
        }

        val newChallenges = challenges.filter { it.isNew }
        if (newChallenges.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("새로운 챌린지", style = MaterialTheme.typography.titleLarge)
            }
            items(newChallenges) { challenge ->
                ChallengeCard(challenge, onParticipateClick = { viewModel.participateInChallenge(challenge.id) })
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("획득한 배지", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun ChallengeCard(challenge: Challenge, onParticipateClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(challenge.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            if (challenge.isParticipating) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LinearProgressIndicator(
                        progress = { challenge.progress / challenge.total },
                        modifier = Modifier.weight(1f).height(10.dp).clip(RoundedCornerShape(5.dp)),
                        color = LightGreen
                    )
                    Text(
                        text = challenge.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                "예상 절약: ${challenge.carbonSaved} | +${challenge.points} 포인트",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            if (challenge.isNew) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onParticipateClick,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("참여하기")
                }
            } else if (challenge.isParticipating) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* TODO: 챌린지 진행 중 */ },
                    modifier = Modifier.align(Alignment.End),
                    enabled = false, // 버튼 비활성화
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text("진행 중")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChallengeScreenPreview() {
    EcoChallengeTheme {
        ChallengeScreen()
    }
}