package com.chenna.lloydsamplepoject.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Chenna Rao on 27/12/24.
 * <p>
 * Frost Interactive
 */
@Composable
fun StyledOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDoneAction: (String) -> Unit,
    onCloseAction: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.Gray,
                fontFamily = FontFamily.Default,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontFamily = FontFamily.Default,
            fontSize = 16.sp,
            letterSpacing = TextUnit(0.8F, TextUnitType.Sp),
            fontWeight = FontWeight.W400
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.LightGray,
            cursorColor = Color.Gray,
            backgroundColor = Color.White,
            textColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onDoneAction(value) // Invoke callback with the current text
            }
        ),
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = {
                    onCloseAction("")
                    onValueChange("")
                }) {
                    Icon(
                        imageVector = Icons.Default.Close, // Built-in close icon
                        contentDescription = "Clear text",
                        tint = Color.Gray
                    )
                }
            }
        },
        shape = RoundedCornerShape(50.dp),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun StyledOutlinedTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    StyledOutlinedTextField(
        value = text,
        onValueChange = { text = it },
        onDoneAction = {},
        onCloseAction = {},
        placeholder = "Enter text",
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}