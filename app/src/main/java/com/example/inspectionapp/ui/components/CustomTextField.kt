package com.example.inspectionapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.inspectionapp.R

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLined: Boolean = true,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = isSingleLined,
        placeholder = { Text(label, color = Color.Gray) },
        modifier = modifier.fillMaxWidth().padding(top = 8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        textStyle = TextStyle(color = Color.Black),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = colorResource(id = R.color.primary_color),
            focusedContainerColor = colorResource(id = R.color.light_gray),
            unfocusedContainerColor = colorResource(id = R.color.light_gray)
        )
    )
}
