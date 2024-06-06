package com.example.a2020102527_main_exam_project.ui.maps

/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 02 June 2024
    Module Code: CSIP6853
*/

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a2020102527_main_exam_project.ui.theme.Color3

/*  Purpose of code
    This composable provides a search text field with a
    rounded shape and a search icon for searching.
    Users can enter text into the text field and click the
    search icon to trigger the search action.
 */

@Composable
fun SearchTextField(
    onTextChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit
) {
    var currentValue by remember {
        mutableStateOf("")
    }
    val localFocusManager = LocalFocusManager.current
    Row(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = currentValue,
            onValueChange = {
                currentValue = it
                onTextChanged(it) },
            placeholder = { Text(placeholder, color = Color3) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions {
                onSearchClicked()
                localFocusManager.clearFocus()
            },
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.weight(2f),
        )
        Spacer(modifier = Modifier.width(2.dp))
        Box(modifier = Modifier.background(Color.White),
            ){
            IconButton(onClick = { onSearchClicked() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color3,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

    }
}

@Preview
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        onTextChanged = {},
        placeholder = "Search...",
        modifier = Modifier.fillMaxWidth(),
        onSearchClicked = {}
    )
}