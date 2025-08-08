package com.inno0422.myapp.ui.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.inno0422.myapp.R
import com.inno0422.myapp.ui.theme.PrimaryGreen
import com.inno0422.myapp.ui.theme.Yellow
import com.inno0422.myapp.ui.theme.Orange
import com.inno0422.myapp.ui.theme.Red

enum class EarthStatus(
    val statusName: String,
    val color: androidx.compose.ui.graphics.Color,
    val imageResId: Int
) {
    Healthy("건강", PrimaryGreen, R.drawable.earth_character_healthy),
    Worried("걱정", Yellow, R.drawable.earth_character_worried),
    Tired("힘듦", Orange, R.drawable.earth_character_tired),
    Sick("위험", Red, R.drawable.earth_character_sick)
}

class DashboardViewModel : ViewModel() {
    private val _carbonFootprint = MutableStateFlow(0.0f)
    val carbonFootprint: StateFlow<Float> = _carbonFootprint.asStateFlow()

    private val _weeklyProgress = MutableStateFlow(
        mapOf("월" to 2.0f, "화" to 2.3f, "수" to 3.2f)
    )
    val weeklyProgress: StateFlow<Map<String, Float>> = _weeklyProgress.asStateFlow()

    private val _points = MutableStateFlow(1250)
    val points: StateFlow<Int> = _points.asStateFlow()

    fun addCarbonFootprint(
        transportCarbon: Float,
        mealCarbon: Float,
        powerCarbon: Float
    ) {
        val newCarbonValue = _carbonFootprint.value + transportCarbon + mealCarbon + powerCarbon
        _carbonFootprint.value = newCarbonValue

        val addedPoints = (transportCarbon + mealCarbon + powerCarbon) * 10
        _points.value += addedPoints.toInt()
    }

    fun getEarthStatus(carbon: Float): EarthStatus {
        return when {
            carbon <= 20.0f -> EarthStatus.Healthy
            carbon <= 30.0f -> EarthStatus.Worried
            carbon <= 40.0f -> EarthStatus.Tired
            else -> EarthStatus.Sick
        }
    }
}