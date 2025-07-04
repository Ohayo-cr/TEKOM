package ru.ohayo.weather_tekom.ui.screen.cityList

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import ru.ohayo.weather_tekom.ui.theme.AppColor

@Composable
fun AddCityDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onTextChange: (String) -> Unit,
    cityName: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить город") },
        text = {
            OutlinedTextField(
                value = cityName,
                onValueChange = onTextChange,
                label = { Text("Название города") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = AppColor)) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha =.5f))
            ) {
                Text("Отменить")
            }
        },
    )
}
