package com.dmitryb.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckTextInput(modifier: Modifier = Modifier) {
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            leadingIcon = { Text(text = "+44") },
            isError = false,
        )
        Spacer(modifier = Modifier.height(4.dp))
        PhoneNumberKeyboard({
            when (it) {
                KeyboardAction.ApproveSymbolPressed -> TODO()
                KeyboardAction.RemoveSymbolPressed -> if (text.isNotEmpty()) {
                    text = text.substring(0, text.lastIndex)
                }
                is KeyboardAction.SymbolPressed -> text += it.symbol
            }
        })
    }
}

@Composable
private fun PhoneNumberKeyboard(
    onNewAction: (KeyboardAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val onButtonPressed: (KeyboardSymbol) -> Unit = { symbol ->
        when (symbol) {
            is KeyboardSymbol.Icon -> onNewAction(KeyboardAction.RemoveSymbolPressed)
            is KeyboardSymbol.Text -> onNewAction(KeyboardAction.SymbolPressed(symbol.text))
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ButtonRow(
            buttons = { listOf("1", "2", "3").map { KeyboardSymbol.Text(it) } },
            onButtonPressed = onButtonPressed
        )
        ButtonRow(
            buttons = { listOf("4", "5", "6").map { KeyboardSymbol.Text(it) } },
            onButtonPressed = onButtonPressed
        )
        ButtonRow(
            buttons = { listOf("7", "8", "9").map { KeyboardSymbol.Text(it) } },
            onButtonPressed = onButtonPressed
        )
        ButtonRow(
            buttons = { listOf(Icons.Filled.ArrowBack, "0", Icons.Filled.Check).map {
                when (it) {
                    is ImageVector -> KeyboardSymbol.Icon(it)
                    else -> KeyboardSymbol.Text(it as String)
                }
            } },
            onButtonPressed = onButtonPressed
        )

    }
}

@Composable
private fun ButtonRow(
    buttons: () -> List<KeyboardSymbol>,
    onButtonPressed: (KeyboardSymbol) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        buttons().forEach { symbol ->
            Button(onClick = { onButtonPressed(symbol) }) {
                when (symbol) {
                    is KeyboardSymbol.Icon -> Icon(
                        imageVector = symbol.icon,
                        contentDescription = null
                    )
                    is KeyboardSymbol.Text -> Text(text = symbol.text)
                }

            }
        }
    }
}

sealed interface KeyboardSymbol {
    data class Icon(val icon: ImageVector) : KeyboardSymbol

    data class Text(val text: String): KeyboardSymbol
}

sealed interface KeyboardAction {
    data object ApproveSymbolPressed : KeyboardAction
    data object RemoveSymbolPressed : KeyboardAction
    data class SymbolPressed(val symbol: String) : KeyboardAction
}

@Preview(showBackground = true)
@Composable
fun PhoneNumberKeyboardPreview() {
    PhoneNumberKeyboard({})
}

@Preview(showBackground = true)
@Composable
fun CheckTextInputPreview() {
    CheckTextInput()
}
