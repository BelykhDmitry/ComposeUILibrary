package com.dmitryb.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun BadgedContent(
    modifier: Modifier = Modifier,
    badge: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Layout(
        content = {
            content()
            badge?.invoke()
        },
        modifier = modifier.wrapContentSize(),
        measurePolicy = remember {
            BadgedContentMeasurePolicy()
        }
    )
}

enum class AnchorPosition {
    BOTTOM_END,
    TOP_START,
    ;
}

fun Modifier.clipWithAnchor(shape: Shape, anchorType: AnchorPosition) = clip(shape)
    .layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val alignmentMap = when (shape) {
            is RoundedCornerShape -> {
                calculateRoundedCornerAlignment(
                    anchorType,
                    shape,
                    placeable.width,
                    placeable.height
                )
            }
            // TODO(): add more shapes
            else -> mapOf()
        }
        layout(placeable.width, placeable.height, alignmentMap) {
            placeable.place(0, 0)
        }
    }

private val sin45 = sin(Math.PI / 4)

private fun MeasureScope.calculateRoundedCornerAlignment(
    anchorType: AnchorPosition,
    shape: RoundedCornerShape,
    width: Int,
    height: Int
): Map<AlignmentLine, Int> {
    val widthAlignment: Int
    val heightAlignment: Int
    when (anchorType) {
        AnchorPosition.BOTTOM_END -> {
            val roundCornerDiff = shape.bottomEnd.toPx(
                Size(width.toFloat(), height.toFloat()),
                this
            ) * (1 - sin45)
            widthAlignment = width - roundCornerDiff.roundToInt()
            heightAlignment = height - roundCornerDiff.roundToInt()
        }

        AnchorPosition.TOP_START -> {
            val roundCornerDiff = shape.topStart.toPx(
                Size(width.toFloat(), height.toFloat()),
                this
            ) * (1 - sin45)
            widthAlignment = roundCornerDiff.roundToInt()
            heightAlignment = roundCornerDiff.roundToInt()
        }
    }
    return mapOf(
        ContentAnchorX to widthAlignment,
        ContentAnchorY to heightAlignment
    )
}

private class BadgedContentMeasurePolicy : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints
    ): MeasureResult {
        val placeables =
            measurables.map { it.measure(constraints.copy(minWidth = 0, minHeight = 0)) }
        val contentPlaceable = placeables.first()
        val badgePlaceable = placeables.getOrNull(1)
        val contentWidth: Int
        val contentHeight: Int
        val badgeOffsetX: Int
        val badgeOffsetY: Int
        if (badgePlaceable != null) {
            val anchorX =
                contentPlaceable[ContentAnchorX].takeIf { it != AlignmentLine.Unspecified }
                    ?: contentPlaceable.width
            val anchorY =
                contentPlaceable[ContentAnchorY].takeIf { it != AlignmentLine.Unspecified }
                    ?: contentPlaceable.height
            val badgeAnchorX = badgePlaceable.width / 2
            val badgeAnchorY = badgePlaceable.height / 2
            badgeOffsetX = anchorX - badgeAnchorX
            badgeOffsetY = anchorY - badgeAnchorY
            contentWidth = maxOf(
                anchorX + badgeAnchorX,
                contentPlaceable.width
            ) - minOf(anchorX - badgeAnchorX, 0)
            contentHeight = maxOf(
                anchorY + badgeAnchorY,
                contentPlaceable.height
            ) - minOf(anchorY - badgeAnchorY, 0)
        } else {
            contentWidth = contentPlaceable.width
            contentHeight = contentPlaceable.height
            badgeOffsetX = 0
            badgeOffsetY = 0
        }

        return layout(contentWidth, contentHeight) {
            contentPlaceable.placeRelative(x = 0, y = 0)

            badgePlaceable?.placeRelative(
                x = badgeOffsetX,
                y = badgeOffsetY
            )
        }
    }

}

private val ContentAnchorX = VerticalAlignmentLine(::maxOf)
private val ContentAnchorY = HorizontalAlignmentLine(::maxOf)

@Preview
@Composable
private fun BadgedContentRoundedBottomEndPreview() {
    MaterialTheme {
        BadgedContent(
            badge = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(8.dp)
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clipWithAnchor(RoundedCornerShape(12.dp), AnchorPosition.BOTTOM_END)
                    .background(Color.LightGray)
            )
        }
    }
}

@Preview
@Composable
private fun BadgedContentWithCircleBottomEndPreview() {
    MaterialTheme {
        BadgedContent(
            modifier = Modifier.wrapContentSize(),
            badge = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(8.dp)
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clipWithAnchor(CircleShape, AnchorPosition.BOTTOM_END)
                    .background(Color.LightGray)
            )
        }
    }
}

@Preview
@Composable
private fun BadgedContentWithCircleTopStartPreview() {
    MaterialTheme {
        BadgedContent(
            badge = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_check_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(8.dp)
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clipWithAnchor(CircleShape, AnchorPosition.TOP_START)
                    .background(Color.LightGray)
            )
        }
    }
}

@Preview
@Composable
private fun CardPreview() {
    MaterialTheme {
        Card {
            Row(modifier = Modifier
                .wrapContentSize()
                .padding(4.dp)) {
                BadgedContent(
                    badge = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_check_circle_24),
                            contentDescription = null,
                            modifier = Modifier
                                .size(8.dp)
                        )
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clipWithAnchor(CircleShape, AnchorPosition.TOP_START)
                            .background(Color.LightGray)
                    )
                }
                Text(text = "Bla bla bla")
            }
        }
    }
}
