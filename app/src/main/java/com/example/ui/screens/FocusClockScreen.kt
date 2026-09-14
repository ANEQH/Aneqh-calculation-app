package com.example.ui.screens

import android.os.SystemClock
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppLanguage
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CalculationViewModel
import com.example.util.AmbientSoundType
import com.example.util.FocusSoundEngine
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

enum class TimerMode(val title: String, val minutes: Int) {
    POMODORO("Pomodoro 25m", 25),
    DEEP_WORK("Deep Work 50m", 50),
    SPRINT("Sprint 15m", 15),
    CUSTOM("Custom", 30),
    STOPWATCH("Open Tracker", 0)
}

enum class TimerPhase(val label: String, val hindi: String, val color: Color) {
    FOCUS("Focus Session", "एकाग्र अध्ययन सत्र", Color(0xFF6366F1)),
    SHORT_BREAK("Short Break (5m)", "लघु विश्राम (5 मि.)", Color(0xFF10B981)),
    LONG_BREAK("Long Break (15m)", "दीर्घ विश्राम (15 मि.)", Color(0xFF38BDF8))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusClockScreen(
    viewModel: CalculationViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val lang = uiState.appLanguage
    val isHindi = lang == AppLanguage.HINDI

    var selectedMode by remember { mutableStateOf(TimerMode.POMODORO) }
    var currentPhase by remember { mutableStateOf(TimerPhase.FOCUS) }
    var targetMinutes by remember { mutableIntStateOf(25) }
    var remainingSeconds by remember { mutableIntStateOf(25 * 60) }
    var totalSecondsForPhase by remember { mutableIntStateOf(25 * 60) }
    var isRunning by remember { mutableStateOf(false) }
    var completedCyclesInSession by remember { mutableIntStateOf(0) }
    var isZenFullscreen by remember { mutableStateOf(false) }
    var selectedSound by remember { mutableStateOf(AmbientSoundType.NONE) }
    var selectedSubject by remember { mutableStateOf("Quantitative Aptitude (Maths)") }
    var showCompletionDialog by remember { mutableStateOf(false) }
    var lastLoggedMinutes by remember { mutableIntStateOf(0) }

    // Audio Engine instance
    val soundEngine = remember { FocusSoundEngine() }
    DisposableEffect(Unit) {
        onDispose {
            soundEngine.release()
        }
    }

    // Change ambient sound when selection changes
    LaunchedEffect(selectedSound, isRunning) {
        if (isRunning && selectedSound != AmbientSoundType.NONE) {
            soundEngine.playSound(selectedSound)
        } else {
            soundEngine.stopSound()
        }
    }

    // Timer Loop
    LaunchedEffect(isRunning, remainingSeconds, selectedMode) {
        if (isRunning) {
            if (selectedMode == TimerMode.STOPWATCH) {
                delay(1000)
                remainingSeconds += 1
            } else {
                if (remainingSeconds > 0) {
                    delay(1000)
                    remainingSeconds -= 1
                } else {
                    // Timer Finished!
                    isRunning = false
                    soundEngine.stopSound()
                    if (currentPhase == TimerPhase.FOCUS) {
                        val sessionMin = totalSecondsForPhase / 60
                        lastLoggedMinutes = sessionMin
                        viewModel.recordFocusSession(sessionMin)
                        completedCyclesInSession += 1
                        showCompletionDialog = true

                        // Setup next phase
                        if (completedCyclesInSession % 4 == 0) {
                            currentPhase = TimerPhase.LONG_BREAK
                            totalSecondsForPhase = 15 * 60
                            remainingSeconds = 15 * 60
                        } else {
                            currentPhase = TimerPhase.SHORT_BREAK
                            totalSecondsForPhase = 5 * 60
                            remainingSeconds = 5 * 60
                        }
                    } else {
                        // Break finished -> back to focus
                        currentPhase = TimerPhase.FOCUS
                        totalSecondsForPhase = targetMinutes * 60
                        remainingSeconds = totalSecondsForPhase
                    }
                }
            }
        }
    }

    // Motivational Quote rotation
    val quotes = remember(isHindi) {
        if (isHindi) listOf(
            "“SSC CGL इंस्पेक्टर की वर्दी कड़ी मेहनत मांगती है, एकाग्र रहें!”",
            "“जो पसीना आज बहेगा, वही कल सफलता की चमक बनेगा।”",
            "“हर 25 मिनट का फोकस आपको आपकी मंजिल के और करीब ले जाता है।”",
            "“ध्यान भटकाना छोड़ें, परीक्षा में 1-1 सेकंड कीमती है।”",
            "“कैलकुलेशन स्पीड तभी तेज होती है जब दिमाग पूरी तरह शांत और केंद्रित हो।”"
        ) else listOf(
            "“Stay locked in. SSC CGL Inspector post rewards pure focus.”",
            "“Every 25 minutes of deep study beats 4 hours of distraction.”",
            "“Speed comes from clarity. Solve with calm precision.”",
            "“Your competition is practicing right now. Keep your eyes on the goal.”",
            "“Distraction is the enemy of calculation speed. Dominate the clock.”"
        )
    }
    var currentQuoteIndex by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(15000)
            currentQuoteIndex = (currentQuoteIndex + 1) % quotes.size
        }
    }

    // Pulse animation for ring
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isRunning) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Completion Dialog
    if (showCompletionDialog) {
        AlertDialog(
            onDismissRequest = { showCompletionDialog = false },
            confirmButton = {
                Button(
                    onClick = { showCompletionDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentEmerald),
                    modifier = Modifier.testTag("focus_modal_continue_button")
                ) {
                    Text(if (isHindi) "शानदार! आगे बढ़ें" else "Awesome! Continue", fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Celebration, contentDescription = null, tint = AccentAmber)
                    Text(
                        text = if (isHindi) "अध्ययन सत्र पूरा हुआ! 🔥" else "Focus Session Complete! 🔥",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = if (isHindi)
                            "बधाई हो! आपने $lastLoggedMinutes मिनट का गहन एकाग्र अध्ययन पूरा किया। यह समय आपकी कुल अध्ययन प्रोफाइल में जोड़ दिया गया है।"
                        else
                            "Great work! You completed $lastLoggedMinutes minutes of uninterrupted deep focus. Logged to your aspirant profile.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("+ $lastLoggedMinutes Min", fontWeight = FontWeight.Bold, color = AccentEmerald, fontSize = 16.sp)
                                Text(if (isHindi) "फोकस समय" else "Focus Logged", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("${uiState.userStats.completedFocusSessions + 1}", fontWeight = FontWeight.Bold, color = PrimaryIndigo, fontSize = 16.sp)
                                Text(if (isHindi) "कुल सत्र" else "Total Sessions", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        )
    }

    // ZEN FULLSCREEN MODE
    if (isZenFullscreen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF090D16))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                // Top Bar in Zen
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text(if (isHindi) currentPhase.hindi else currentPhase.label, color = currentPhase.color) },
                        leadingIcon = { Icon(Icons.Default.SelfImprovement, contentDescription = null, tint = currentPhase.color) }
                    )
                    IconButton(
                        onClick = { isZenFullscreen = false },
                        modifier = Modifier.testTag("exit_zen_button")
                    ) {
                        Icon(Icons.Default.FullscreenExit, contentDescription = "Exit Zen", tint = Color.White)
                    }
                }

                // Center Clock Big Minimalist
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val minutes = remainingSeconds / 60
                    val seconds = remainingSeconds % 60
                    val timeStr = String.format("%02d:%02d", minutes, seconds)

                    Text(
                        text = timeStr,
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = Color.White,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = selectedSubject,
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Bottom Quote & Control
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = quotes[currentQuoteIndex],
                        style = MaterialTheme.typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                        color = Color(0xFFCBD5E1),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        FilledTonalIconButton(
                            onClick = {
                                isRunning = !isRunning
                            },
                            colors = IconButtonDefaults.filledTonalIconButtonColors(
                                containerColor = if (isRunning) Color(0xFFF43F5E) else Color(0xFF10B981)
                            ),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
        return
    }

    // NORMAL FOCUS MODE SCREEN
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isHindi) "फोकस मोड क्लॉक" else "Focus Mode Clock",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isHindi) "पोमोडोरो व डीप स्टडी टाइमर" else "Pomodoro & Deep Zen Study",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.HOME) },
                        modifier = Modifier.testTag("focus_back_button")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { isZenFullscreen = true },
                        modifier = Modifier.testTag("zen_fullscreen_button")
                    ) {
                        Icon(Icons.Default.Fullscreen, contentDescription = "Zen Fullscreen", tint = PrimaryIndigo)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Mode Selector Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(TimerMode.values()) { mode ->
                    val isSelected = selectedMode == mode
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (!isRunning) {
                                selectedMode = mode
                                currentPhase = TimerPhase.FOCUS
                                if (mode == TimerMode.STOPWATCH) {
                                    remainingSeconds = 0
                                    totalSecondsForPhase = 0
                                } else if (mode == TimerMode.CUSTOM) {
                                    totalSecondsForPhase = targetMinutes * 60
                                    remainingSeconds = totalSecondsForPhase
                                } else {
                                    targetMinutes = mode.minutes
                                    totalSecondsForPhase = mode.minutes * 60
                                    remainingSeconds = totalSecondsForPhase
                                }
                            }
                        },
                        label = { Text(mode.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        leadingIcon = {
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        }
                    )
                }
            }

            // Custom Slider if Custom Mode
            if (selectedMode == TimerMode.CUSTOM && !isRunning) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isHindi) "कस्टम समय चुनें" else "Set Custom Duration",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = "$targetMinutes min",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = PrimaryIndigo)
                            )
                        }
                        Slider(
                            value = targetMinutes.toFloat(),
                            onValueChange = {
                                targetMinutes = it.toInt()
                                totalSecondsForPhase = targetMinutes * 60
                                remainingSeconds = totalSecondsForPhase
                            },
                            valueRange = 5f..120f,
                            steps = 22,
                            modifier = Modifier.fillMaxWidth().testTag("custom_timer_slider")
                        )
                    }
                }
            }

            // Subject / Topic Tag
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Bookmark, contentDescription = null, tint = AccentAmber, modifier = Modifier.size(20.dp))
                        Text(
                            text = selectedSubject,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                    Text(
                        text = if (isHindi) "सत्र: ${completedCyclesInSession + 1}/4" else "Session: ${completedCyclesInSession + 1}/4",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryIndigo
                    )
                }
            }

            // Central Circular Progress Clock Dial
            val progress = if (selectedMode == TimerMode.STOPWATCH) {
                0.5f
            } else if (totalSecondsForPhase > 0) {
                (remainingSeconds.toFloat() / totalSecondsForPhase.toFloat()).coerceIn(0f, 1f)
            } else 0f

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(270.dp)
                    .padding(12.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 14.dp.toPx()
                    val diameter = size.minDimension - strokeWidth
                    val topLeft = Offset((size.width - diameter) / 2, (size.height - diameter) / 2)
                    val arcSize = Size(diameter, diameter)

                    // Track circle background
                    drawArc(
                        color = Color(0xFFE2E8F0).copy(alpha = 0.25f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Animated Progress arc
                    val sweep = if (selectedMode == TimerMode.STOPWATCH) {
                        ((remainingSeconds % 60) / 60f) * 360f
                    } else {
                        progress * 360f
                    }

                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                currentPhase.color,
                                currentPhase.color.copy(alpha = 0.7f),
                                AccentCyan,
                                currentPhase.color
                            )
                        ),
                        startAngle = -90f,
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Clock ticks
                    val radius = diameter / 2f
                    val center = Offset(size.width / 2, size.height / 2)
                    for (i in 0 until 12) {
                        val angle = Math.toRadians((i * 30).toDouble())
                        val inner = radius - 18.dp.toPx()
                        val outer = radius - 26.dp.toPx()
                        val start = Offset(
                            x = (center.x + inner * cos(angle)).toFloat(),
                            y = (center.y + inner * sin(angle)).toFloat()
                        )
                        val end = Offset(
                            x = (center.x + outer * cos(angle)).toFloat(),
                            y = (center.y + outer * sin(angle)).toFloat()
                        )
                        drawLine(
                            color = Color.Gray.copy(alpha = 0.35f),
                            start = start,
                            end = end,
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }

                // Inner content inside dial
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(
                                text = if (isHindi) currentPhase.hindi else currentPhase.label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = currentPhase.color
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(containerColor = currentPhase.color.copy(alpha = 0.12f)),
                        modifier = Modifier.height(28.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    val minutes = remainingSeconds / 60
                    val seconds = remainingSeconds % 60
                    val displayTime = String.format("%02d:%02d", minutes, seconds)

                    Text(
                        text = displayTime,
                        fontSize = 46.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.onSurface,
                        letterSpacing = 1.5.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (isRunning) (if (isHindi) "फोकस चालू है..." else "Focus in session...") else (if (isHindi) "विश्राम / ठहराव" else "Paused"),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isRunning) AccentEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Control Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset Button
                FilledTonalIconButton(
                    onClick = {
                        isRunning = false
                        soundEngine.stopSound()
                        remainingSeconds = totalSecondsForPhase
                    },
                    modifier = Modifier.size(50.dp).testTag("timer_reset_button")
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset")
                }

                Spacer(modifier = Modifier.width(20.dp))

                // Play / Pause Main Button
                Button(
                    onClick = {
                        isRunning = !isRunning
                    },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning) AccentRose else PrimaryIndigo
                    ),
                    modifier = Modifier
                        .size(72.dp)
                        .testTag("timer_play_pause_button"),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isRunning) "Pause" else "Start",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                // Skip to next phase button
                FilledTonalIconButton(
                    onClick = {
                        isRunning = false
                        soundEngine.stopSound()
                        if (currentPhase == TimerPhase.FOCUS) {
                            currentPhase = TimerPhase.SHORT_BREAK
                            totalSecondsForPhase = 5 * 60
                            remainingSeconds = 5 * 60
                        } else {
                            currentPhase = TimerPhase.FOCUS
                            totalSecondsForPhase = targetMinutes * 60
                            remainingSeconds = totalSecondsForPhase
                        }
                    },
                    modifier = Modifier.size(50.dp).testTag("timer_skip_button")
                ) {
                    Icon(Icons.Default.SkipNext, contentDescription = "Skip Phase")
                }
            }

            // Ambient Focus Sound Selector
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.GraphicEq, contentDescription = null, tint = PrimaryIndigo)
                            Text(
                                text = if (isHindi) "एकाग्रता ऑडियो (Ambient Waves)" else "Concentration Ambient Sound",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        if (selectedSound != AmbientSoundType.NONE && isRunning) {
                            Text(
                                text = if (isHindi) "बज रहा है 🎵" else "Playing 🎵",
                                style = MaterialTheme.typography.bodySmall.copy(color = AccentEmerald, fontWeight = FontWeight.Bold)
                            )
                        }
                    }

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(AmbientSoundType.values()) { sound ->
                            val isSelected = selectedSound == sound
                            SuggestionChip(
                                onClick = { selectedSound = sound },
                                label = { Text(if (isHindi) sound.hindi else sound.label, fontSize = 12.sp) },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = if (isSelected) PrimaryIndigo.copy(alpha = 0.15f) else Color.Transparent
                                ),
                                border = SuggestionChipDefaults.suggestionChipBorder(
                                    enabled = true,
                                    borderColor = if (isSelected) PrimaryIndigo else MaterialTheme.colorScheme.outlineVariant
                                )
                            )
                        }
                    }
                }
            }

            // Daily Study Focus Statistics Card
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${uiState.userStats.todayFocusMinutes}m",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = PrimaryIndigo)
                        )
                        Text(
                            text = if (isHindi) "आज का फोकस" else "Today's Focus",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${uiState.userStats.completedFocusSessions}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = AccentEmerald)
                        )
                        Text(
                            text = if (isHindi) "सत्र पूर्ण" else "Sessions Done",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .height(40.dp)
                            .width(1.dp)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${uiState.userStats.focusStreak} 🔥",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = AccentAmber)
                        )
                        Text(
                            text = if (isHindi) "फोकस स्ट्रीक" else "Focus Streak",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Motivational Banner
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = PrimaryIndigo.copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryIndigo.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = AccentAmber)
                    Text(
                        text = quotes[currentQuoteIndex],
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Quick Link to Study Planner & Exam Countdown
            OutlinedButton(
                onClick = { viewModel.navigateTo(AppScreen.STUDY_PLANNER) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().testTag("open_study_planner_button")
            ) {
                Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isHindi) "दैनिक लक्ष्य व परीक्षा काउंटडाउन देखें" else "View Daily Study Targets & Exam Countdown",
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
