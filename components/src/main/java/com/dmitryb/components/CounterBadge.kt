package com.dmitryb.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            Row(
                modifier = Modifier
                    .background(Color.Cyan, shape = CircleShape)
                    .clip(CircleShape),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$counter",
                    modifier = Modifier.padding(2.dp),
                    fontSize = 4.sp,
                    lineHeight = 4.sp,
                    textAlign = TextAlign.Center
                )
            }

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

@Composable
fun CounterWithStandardBadge(modifier: Modifier = Modifier) {
    var counter by rememberSaveable {
        mutableIntStateOf(0)
    }
    BadgedContent(
        modifier = modifier,
        badge = {
            Badge {
                Text(text = "$counter")
            }
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

@Preview
@Composable
private fun CounterWithStandardBadgePreview() {
    MaterialTheme {
        CounterWithStandardBadge()
    }
}

/**
 * Example from official documentation.
 * In example: text is in the middle of background.
 */
@Composable
private fun BadgeInteractiveExample() {
    var itemCount by remember { mutableIntStateOf(0) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BadgedBox(
            badge = {
                if (itemCount > 0) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text("$itemCount")
                    }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Shopping cart",
            )
        }
        Button(onClick = { itemCount++ }) {
            Text("Add item")
        }
    }
}

@Preview
@Composable
private fun BadgeInteractiveExamplePreview() {
    MaterialTheme {
        BadgeInteractiveExample()
    }
}
