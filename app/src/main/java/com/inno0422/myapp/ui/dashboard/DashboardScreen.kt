package com.inno0422.myapp.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inno0422.myapp.R
import com.inno0422.myapp.ui.theme.EcoChallengeTheme

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val carbonFootprint by viewModel.carbonFootprint.collectAsState()
    val weeklyProgress by viewModel.weeklyProgress.collectAsState()
    val points by viewModel.points.collectAsState()
    val earthStatus = viewModel.getEarthStatus(carbonFootprint)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "포인트: $points P",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("지구의 상태", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = earthStatus.imageResId),
                    contentDescription = "지구 캐릭터 상태: ${earthStatus.statusName}",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(earthStatus.color)
                )
                Text(
                    earthStatus.statusName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        CarbonFootprintCard(carbonFootprint, earthStatus)

        Spacer(modifier = Modifier.height(24.dp))

        WeeklyProgressCard(weeklyProgress)

        Spacer(modifier = Modifier.height(24.dp))

        QuickActionButtons()
    }
}

@Composable
fun CarbonFootprintCard(carbon: Float, status: EarthStatus) {
    val target = 20.0f
    val progress = carbon / (target * 2)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("오늘의 탄소 발자국", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(150.dp)) {
                CircularProgressIndicator(
                    progress = { progress.coerceIn(0f, 1f) },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 12.dp,
                    color = status.color,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$carbon",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = status.color
                    )
                    Text(
                        text = "kg CO₂",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (carbon > target) "목표 초과 ${"%.1f".format(carbon - target)}kg" else "목표: ${target}kg CO₂",
                color = if (carbon > target) status.color else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun WeeklyProgressCard(progress: Map<String, Float>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("주간 진행상황", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            progress.forEach { (day, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(day, modifier = Modifier.width(40.dp))
                    LinearProgressIndicator(
                        progress = { value / 5f },
                        modifier = Modifier.weight(1f).height(12.dp).clip(RoundedCornerShape(6.dp))
                    )
                    Text("${value}kg", modifier = Modifier.width(50.dp), textAlign = TextAlign.End)
                }
            }
        }
    }
}

@Composable
fun QuickActionButtons() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { /* TODO: 대중교통 이용 로직 */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("대중교통 이용")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /* TODO: 채식 식단 로직 */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text("채식 식단")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    EcoChallengeTheme {
        DashboardScreen()
    }
}