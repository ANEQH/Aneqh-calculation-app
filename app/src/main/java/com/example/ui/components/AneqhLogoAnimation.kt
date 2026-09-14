package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.*
import kotlin.random.Random

/**
 * Animated ANEQH Insignia Component matching the Inspector Chalisa video branding:
 * - Outer Rainbow circular spectrum ring with continuous subtle rotation and glow.
 * - Ashoka Chakra / Emblem radiant crown at top.
 * - Scholar Graduation Mortarboard cap with hanging golden tassel.
 * - Dynamic open book layers in emerald, cyan, and purple.
 * - Golden open Chalisa base wings.
 * - "ANEQH" bold branding badge.
 * - Orbiting sparkle and magic math particles.
 */
@Composable
fun AneqhAnimatedLogo(
    modifier: Modifier = Modifier,
    size: Dp = 160.dp,
    showParticles: Boolean = true,
    interactive: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "aneqh_logo_anim")

    // Continuous smooth rotation for the rainbow ring
    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring_rotation"
    )

    // Gentle float/pulse for the emblem
    val emblemFloat by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emblem_float"
    )

    // Shimmer glow wave
    val shimmerPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    // Tap pulse animation
    var tapTrigger by remember { mutableStateOf(0) }
    val tapScale by animateFloatAsState(
        targetValue = if (tapTrigger % 2 == 1) 1.08f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        finishedListener = {
            if (tapTrigger % 2 == 1) tapTrigger++
        },
        label = "tap_scale"
    )

    Box(
        modifier = modifier
            .size(size)
            .scale(tapScale)
            .then(
                if (interactive) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        tapTrigger++
                    }
                } else Modifier
            )
            .testTag("aneqh_animated_logo"),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.toPx() / 2f, size.toPx() / 2f)
            val radius = size.toPx() * 0.44f

            // 1. Outer Swirling / Orbiting Particles
            if (showParticles) {
                drawSwirlParticles(center, radius, ringRotation, shimmerPhase)
            }

            // 2. Soft background circular glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFEF08A).copy(alpha = 0.12f),
                        Color(0xFF06B6D4).copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius * 1.3f
                ),
                radius = radius * 1.25f,
                center = center
            )

            // 3. Circular Rainbow Spectrum Ring
            rotate(degrees = ringRotation, pivot = center) {
                drawRainbowRing(center, radius)
            }

            // 4. White / Clean Base Inner Disk
            drawCircle(
                color = Color(0xFFFAFBFD),
                radius = radius * 0.94f,
                center = center
            )

            // Inner gold hairline border
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFFEAB308),
                        Color(0xFFFDE047),
                        Color(0xFFF59E0B),
                        Color(0xFFEAB308)
                    ),
                    center = center
                ),
                radius = radius * 0.92f,
                center = center,
                style = Stroke(width = size.toPx() * 0.012f)
            )

            // 5. Draw Emblem Content (Ashoka chakra, Mortarboard, Pages, Wings, ANEQH text)
            val emblemCenter = center + Offset(0f, emblemFloat)
            drawAneqhInsignia(emblemCenter, size.toPx(), shimmerPhase)
        }
    }
}

private fun DrawScope.drawRainbowRing(center: Offset, radius: Float) {
    val strokeWidth = radius * 0.10f
    val rainbowColors = listOf(
        Color(0xFFEF4444), // Red
        Color(0xFFF97316), // Orange
        Color(0xFFFACC15), // Yellow
        Color(0xFF10B981), // Green
        Color(0xFF06B6D4), // Cyan
        Color(0xFF3B82F6), // Blue
        Color(0xFF8B5CF6), // Purple
        Color(0xFFEC4899), // Pink
        Color(0xFFEF4444)  // Loop
    )

    drawCircle(
        brush = Brush.sweepGradient(
            colors = rainbowColors,
            center = center
        ),
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidth)
    )

    // Soft outer glow rim
    drawCircle(
        color = Color.White.copy(alpha = 0.35f),
        radius = radius + strokeWidth * 0.45f,
        center = center,
        style = Stroke(width = strokeWidth * 0.15f)
    )
}

private fun DrawScope.drawSwirlParticles(
    center: Offset,
    radius: Float,
    rotation: Float,
    shimmer: Float
) {
    val particleCount = 18
    val particleColors = listOf(
        Color(0xFFFACC15),
        Color(0xFF38BDF8),
        Color(0xFF34D399),
        Color(0xFFF472B6),
        Color(0xFFA78BFA),
        Color(0xFFFDE047)
    )

    for (i in 0 until particleCount) {
        val angleDeg = (i * (360f / particleCount) + rotation * 1.4f + i * 15f) % 360f
        val angleRad = Math.toRadians(angleDeg.toDouble())
        val distVariation = (sin(angleRad * 3 + shimmer * 6.28) * 12).toFloat()
        val orbitDist = radius * 1.14f + distVariation
        val px = center.x + (orbitDist * cos(angleRad)).toFloat()
        val py = center.y + (orbitDist * sin(angleRad)).toFloat()
        val pSize = (radius * 0.032f) * (0.6f + 0.4f * sin((i + shimmer * 4).toDouble()).toFloat())

        drawCircle(
            color = particleColors[i % particleColors.size].copy(alpha = 0.75f),
            radius = pSize,
            center = Offset(px, py)
        )
    }
}

private fun DrawScope.drawAneqhInsignia(center: Offset, canvasSize: Float, shimmer: Float) {
    val unit = canvasSize / 100f

    // A. Top Ashoka Emblem & Chakra
    val emblemY = center.y - unit * 27f
    val emblemX = center.x

    // 1. Navy Blue Ashoka Chakra Circle with 12 Spokes
    val chakraRadius = unit * 3.5f
    val navyColor = Color(0xFF1E3A8A)
    val goldColor = Color(0xFFEAB308)
    val darkGold = Color(0xFFCA8A04)

    drawCircle(
        color = navyColor,
        radius = chakraRadius,
        center = Offset(emblemX, emblemY),
        style = Stroke(width = unit * 0.8f)
    )
    drawCircle(
        color = navyColor,
        radius = unit * 0.8f,
        center = Offset(emblemX, emblemY)
    )
    for (i in 0 until 12) {
        val spokeAngle = Math.toRadians((i * 30).toDouble())
        val sx = emblemX + (chakraRadius * cos(spokeAngle)).toFloat()
        val sy = emblemY + (chakraRadius * sin(spokeAngle)).toFloat()
        drawLine(
            color = navyColor,
            start = Offset(emblemX, emblemY),
            end = Offset(sx, sy),
            strokeWidth = unit * 0.45f
        )
    }

    // Ashoka Capital Base / Lion Crest in Gold
    val crestPath = Path().apply {
        moveTo(emblemX - unit * 4f, emblemY - unit * 2.5f)
        lineTo(emblemX - unit * 2.5f, emblemY - unit * 5.5f)
        lineTo(emblemX, emblemY - unit * 6.5f)
        lineTo(emblemX + unit * 2.5f, emblemY - unit * 5.5f)
        lineTo(emblemX + unit * 4f, emblemY - unit * 2.5f)
        lineTo(emblemX + unit * 3f, emblemY - unit * 1.5f)
        lineTo(emblemX - unit * 3f, emblemY - unit * 1.5f)
        close()
    }
    drawPath(
        path = crestPath,
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFFFDE047), goldColor, darkGold),
            start = Offset(emblemX - unit * 4f, emblemY - unit * 6f),
            end = Offset(emblemX + unit * 4f, emblemY)
        )
    )

    // B. Graduation Cap (Scholar Mortarboard)
    val capY = center.y - unit * 14f
    val capDiamond = Path().apply {
        moveTo(emblemX, capY - unit * 7f)             // Top vertex
        lineTo(emblemX + unit * 17f, capY - unit * 2.5f) // Right vertex
        lineTo(emblemX, capY + unit * 2f)              // Bottom vertex
        lineTo(emblemX - unit * 17f, capY - unit * 2.5f) // Left vertex
        close()
    }

    // Fill Diamond with Emerald & Teal gradient
    drawPath(
        path = capDiamond,
        brush = Brush.linearGradient(
            colors = listOf(Color(0xFF0F766E), Color(0xFF0D9488), Color(0xFF14B8A6)),
            start = Offset(emblemX - unit * 15f, capY - unit * 6f),
            end = Offset(emblemX + unit * 15f, capY + unit * 2f)
        )
    )
    // Diamond gold border
    drawPath(
        path = capDiamond,
        color = Color(0xFFFDE047),
        style = Stroke(width = unit * 0.9f, join = StrokeJoin.Round)
    )

    // Cap Skull Cap Underneath
    val skullCap = Path().apply {
        moveTo(emblemX - unit * 8f, capY - unit * 0.5f)
        quadraticTo(emblemX, capY + unit * 4f, emblemX + unit * 8f, capY - unit * 0.5f)
        lineTo(emblemX + unit * 7f, capY + unit * 1.5f)
        quadraticTo(emblemX, capY + unit * 5.5f, emblemX - unit * 7f, capY + unit * 1.5f)
        close()
    }
    drawPath(path = skullCap, color = Color(0xFF115E59))

    // Golden Tassel & Ribbon
    val tasselPath = Path().apply {
        moveTo(emblemX, capY - unit * 2.5f)
        quadraticTo(emblemX + unit * 12f, capY - unit * 1.5f, emblemX + unit * 14f, capY + unit * 6f)
    }
    drawPath(
        path = tasselPath,
        color = Color(0xFFF59E0B),
        style = Stroke(width = unit * 0.8f, cap = StrokeCap.Round)
    )
    // Tassel drop bulb
    drawCircle(
        color = Color(0xFFFBBF24),
        radius = unit * 1.2f,
        center = Offset(emblemX + unit * 14f, capY + unit * 6.5f)
    )

    // C. Open Layered Book Wings (Middle Green & Purple Layers)
    val bookY = center.y + unit * 1f

    // Layer 1: Royal Purple Back Pages
    val purplePageLeft = Path().apply {
        moveTo(emblemX - unit * 1f, bookY - unit * 5f)
        lineTo(emblemX - unit * 14f, bookY - unit * 1f)
        lineTo(emblemX - unit * 14f, bookY + unit * 7f)
        lineTo(emblemX - unit * 1f, bookY + unit * 4f)
        close()
    }
    val purplePageRight = Path().apply {
        moveTo(emblemX + unit * 1f, bookY - unit * 5f)
        lineTo(emblemX + unit * 14f, bookY - unit * 1f)
        lineTo(emblemX + unit * 14f, bookY + unit * 7f)
        lineTo(emblemX + unit * 1f, bookY + unit * 4f)
        close()
    }
    val purpleBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF7E22CE), Color(0xFF9333EA)),
        start = Offset(emblemX - unit * 14f, bookY),
        end = Offset(emblemX + unit * 14f, bookY)
    )
    drawPath(path = purplePageLeft, brush = purpleBrush)
    drawPath(path = purplePageRight, brush = purpleBrush)

    // Layer 2: Emerald Green Front Pages
    val greenPageLeft = Path().apply {
        moveTo(emblemX - unit * 1f, bookY - unit * 3f)
        lineTo(emblemX - unit * 11f, bookY + unit * 0.5f)
        lineTo(emblemX - unit * 11f, bookY + unit * 7.5f)
        lineTo(emblemX - unit * 1f, bookY + unit * 5f)
        close()
    }
    val greenPageRight = Path().apply {
        moveTo(emblemX + unit * 1f, bookY - unit * 3f)
        lineTo(emblemX + unit * 11f, bookY + unit * 0.5f)
        lineTo(emblemX + unit * 11f, bookY + unit * 7.5f)
        lineTo(emblemX + unit * 1f, bookY + unit * 5f)
        close()
    }
    val emeraldBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF059669), Color(0xFF10B981)),
        start = Offset(emblemX - unit * 11f, bookY),
        end = Offset(emblemX + unit * 11f, bookY)
    )
    drawPath(path = greenPageLeft, brush = emeraldBrush)
    drawPath(path = greenPageRight, brush = emeraldBrush)

    // Inner White page lines
    drawLine(
        color = Color.White.copy(alpha = 0.85f),
        start = Offset(emblemX - unit * 3f, bookY + unit * 0.5f),
        end = Offset(emblemX - unit * 9f, bookY + unit * 3f),
        strokeWidth = unit * 0.6f
    )
    drawLine(
        color = Color.White.copy(alpha = 0.85f),
        start = Offset(emblemX + unit * 3f, bookY + unit * 0.5f),
        end = Offset(emblemX + unit * 9f, bookY + unit * 3f),
        strokeWidth = unit * 0.6f
    )

    // D. Base Golden Chalisa Open Book Wings
    val baseBookY = center.y + unit * 11f
    val goldenWingLeft = Path().apply {
        moveTo(emblemX, baseBookY - unit * 1f)
        quadraticTo(emblemX - unit * 12f, baseBookY - unit * 6f, emblemX - unit * 22f, baseBookY - unit * 2f)
        quadraticTo(emblemX - unit * 14f, baseBookY + unit * 3f, emblemX, baseBookY + unit * 3.5f)
        close()
    }
    val goldenWingRight = Path().apply {
        moveTo(emblemX, baseBookY - unit * 1f)
        quadraticTo(emblemX + unit * 12f, baseBookY - unit * 6f, emblemX + unit * 22f, baseBookY - unit * 2f)
        quadraticTo(emblemX + unit * 14f, baseBookY + unit * 3f, emblemX, baseBookY + unit * 3.5f)
        close()
    }

    val goldWingBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFB45309),
            Color(0xFFF59E0B),
            Color(0xFFFDE047),
            Color(0xFFF59E0B),
            Color(0xFFB45309)
        ),
        startX = emblemX - unit * 22f,
        endX = emblemX + unit * 22f
    )
    drawPath(path = goldenWingLeft, brush = goldWingBrush)
    drawPath(path = goldenWingRight, brush = goldWingBrush)

    // Wing Spine & Central Dot
    drawCircle(
        color = Color(0xFFD97706),
        radius = unit * 1.3f,
        center = Offset(emblemX, baseBookY + unit * 2f)
    )

    // E. "ANEQH" Branding Graphic at Bottom
    drawAneqhTextShape(
        centerX = emblemX,
        centerY = center.y + unit * 21f,
        unit = unit,
        shimmer = shimmer
    )
}

/**
 * Draws the bold, metallic stylized "ANEQH" letters geometrically
 */
private fun DrawScope.drawAneqhTextShape(
    centerX: Float,
    centerY: Float,
    unit: Float,
    shimmer: Float
) {
    val navyColor = Color(0xFF0F172A)
    val goldShine = Color(0xFFD97706)
    val strokeWidth = unit * 1.2f

    // Total width for ANEK is roughly 27 units
    val startX = centerX - unit * 13.5f

    // 1. 'A'
    val pathA = Path().apply {
        moveTo(startX + unit * 0.5f, centerY + unit * 3f)
        lineTo(startX + unit * 3f, centerY - unit * 3f)
        lineTo(startX + unit * 5.5f, centerY + unit * 3f)
        moveTo(startX + unit * 1.5f, centerY + unit * 0.5f)
        lineTo(startX + unit * 4.5f, centerY + unit * 0.5f)
    }
    drawPath(path = pathA, color = navyColor, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // 2. 'N'
    val nX = startX + unit * 7.5f
    val pathN = Path().apply {
        moveTo(nX, centerY + unit * 3f)
        lineTo(nX, centerY - unit * 3f)
        lineTo(nX + unit * 5f, centerY + unit * 3f)
        lineTo(nX + unit * 5f, centerY - unit * 3f)
    }
    drawPath(path = pathN, color = navyColor, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // 3. 'E'
    val eX = startX + unit * 14.5f
    val pathE = Path().apply {
        moveTo(eX + unit * 4.5f, centerY - unit * 3f)
        lineTo(eX, centerY - unit * 3f)
        lineTo(eX, centerY + unit * 3f)
        lineTo(eX + unit * 4.5f, centerY + unit * 3f)
        moveTo(eX, centerY)
        lineTo(eX + unit * 3.5f, centerY)
    }
    drawPath(path = pathE, color = navyColor, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // 4. 'K'
    val kX = startX + unit * 22f
    val pathK = Path().apply {
        moveTo(kX, centerY - unit * 3f)
        lineTo(kX, centerY + unit * 3f)
        moveTo(kX + unit * 4.5f, centerY - unit * 3f)
        lineTo(kX + unit * 1f, centerY)
        lineTo(kX + unit * 4.5f, centerY + unit * 3f)
    }
    drawPath(path = pathK, color = navyColor, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))

    // Subtle gold underline below ANEK
    val underlineY = centerY + unit * 5.2f
    drawLine(
        brush = Brush.horizontalGradient(
            colors = listOf(Color.Transparent, goldShine, Color(0xFFFDE047), goldShine, Color.Transparent),
            startX = centerX - unit * 12f,
            endX = centerX + unit * 12f
        ),
        start = Offset(centerX - unit * 12f, underlineY),
        end = Offset(centerX + unit * 12f, underlineY),
        strokeWidth = unit * 0.75f,
        cap = StrokeCap.Round
    )
}

/**
 * Full-screen splash / animated intro dialog or banner for ANEQH with particle reveal
 */
@Composable
fun AneqhIntroBanner(
    modifier: Modifier = Modifier,
    onExploreClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp)),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            Brush.sweepGradient(
                listOf(
                    Color(0xFFEF4444),
                    Color(0xFFF59E0B),
                    Color(0xFF10B981),
                    Color(0xFF06B6D4),
                    Color(0xFF8B5CF6),
                    Color(0xFFEF4444)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFEF3C7),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Official Emblem",
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "OFFICIAL CHALISA SYSTEM",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = Color(0xFF92400E)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Animated interactive logo
            AneqhAnimatedLogo(
                size = 170.dp,
                showParticles = true,
                interactive = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "ANEQH",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Inspector Chalisa Speed Math Mastery",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Vedic Tricks • 12 Chalisa Chapters • 10x Daily Drills",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
