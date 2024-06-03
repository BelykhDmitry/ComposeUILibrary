package com.dmitryb.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableCard(
    mainContent: @Composable RowScope.() -> Unit,
    expandableContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    expandedByDefault: Boolean = false
) {
    // TODO(): add animation, think about Transition for this
    var expandedState by rememberSaveable {
        mutableStateOf(expandedByDefault)
    }
    // NOTE(): Make Container as a parameter for better flexibility?
    Card(
        modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier.padding(8.dp)
        ) {
            mainContent()
            IconButton(
                onClick = { expandedState = !expandedState },
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Top)
                    .padding(start = 4.dp)
                    .rotate(if (expandedState) 180f else 0f)
            ) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }
        AnimatedVisibility(visible = expandedState) {
            expandableContent()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ExpandableCardPreview() {
    MaterialTheme {
        ExpandableCard(
            mainContent = {
                Text(
                    text = "Some main content, text for example. Very large title, Some main content, text for example. Very large title, Some main content, text for example. Very large title",
                    modifier = Modifier.weight(1f, true)
                )
            },
            expandableContent = {
                Text(
                    text = "Some extra content, text for example. Or there could be images",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                )
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun ExpandedExpandableCardPreview() {
    MaterialTheme {
        ExpandableCard(
            mainContent = {
                Text(
                    text = "Some main content, text for example. Very large title, Some main content, text for example. Very large title, Some main content, text for example. Very large title",
                    modifier = Modifier.weight(1f, true)
                )
            },
            expandableContent = {
                Text(
                    text = "Some extra content, text for example. Or there could be images",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                )
            },
            expandedByDefault = true
        )
    }
}
