package com.dmitryb.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CounterBadge(modifier: Modifier = Modifier) {
    var counter by rememberSaveable {
        mutableIntStateOf(0)
    }
    BadgedContent(
        modifier = modifier,
        badge = {
            // TODO: How to avoid different spacing top/bottom in text?
            Text(
                text = "$counter",
                modifier = Modifier
                    .background(Color.Cyan, shape = RoundedCornerShape(4.dp))
                    .padding(2.dp),
                fontSize = 4.sp,
                letterSpacing = 1.sp,
                lineHeight = 2.sp,
                textAlign = TextAlign.Center
            )
        }
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clipWithAnchor(CircleShape, AnchorPosition.BOTTOM_END)
                .background(Color.LightGray)
                .clickable {
                    counter++
                }
        )
    }
}

@Preview
@Composable
private fun CounterBadgePreview() {
    MaterialTheme {
        CounterBadge()
    }
}
