package org.generadorfc.project

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun App() {
    MaterialTheme {
        var nombre by remember { mutableStateOf("") }
        var apellidoPaterno by remember { mutableStateOf("") }
        var apellidoMaterno by remember { mutableStateOf("") }
        var fechaNacimiento by remember { mutableStateOf("") }

        val rfcGenerado = calcularRFC(nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento)

        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(
                text = "RFC Generado: $rfcGenerado",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre(s)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = apellidoPaterno,
                onValueChange = { apellidoPaterno = it },
                label = { Text("Apellido Paterno") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = apellidoMaterno,
                onValueChange = { apellidoMaterno = it },
                label = { Text("Apellido Materno (Dejar en blanco si no tiene)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = { fechaNacimiento = it },
                label = { Text("Fecha de Nacimiento (AAMMDD)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}

fun calcularRFC(nombre: String, paterno: String, materno: String, fecha: String): String {
    val nom = nombre.trim().uppercase()
    val pat = paterno.trim().uppercase()
    val mat = materno.trim().uppercase()
    val fec = fecha.trim()

    val primeraLetraPat = pat.firstOrNull() ?: 'X'

    val vocalInternaPat = pat.drop(1).firstOrNull {
        it == 'A' || it == 'E' || it == 'I' || it == 'O' || it == 'U'
    } ?: 'X'

    val primeraLetraMat = mat.firstOrNull() ?: 'X'

    val nombres = nom.split(" ").filter { it.isNotEmpty() }
    val nombreValido = if (nombres.size > 1 && (nombres[0] == "JOSE" || nombres[0] == "MARIA")) {
        nombres[1]
    } else {
        nombres.firstOrNull() ?: ""
    }
    val primeraLetraNom = nombreValido.firstOrNull() ?: 'X'

    var letrasRfc = "$primeraLetraPat$vocalInternaPat$primeraLetraMat$primeraLetraNom"

    val palabrasInconvenientes = listOf(
        "BUEI", "BUEY", "CACA", "CACO", "CAGA", "CAGO", "CAKA", "CAKO",
        "COGE", "COJA", "COJE", "COJI", "COJO", "CULO", "FETO", "GUEY",
        "JOTO", "KACA", "KACO", "KAGA", "KAGO", "KAKA", "KOGE", "KOJO",
        "KULO", "LOCA", "LOCO", "MAME", "MAMO", "MEAR", "MEAS", "MEON",
        "MION", "MOCO", "MULA", "PEDA", "PEDO", "PENE", "PUTA", "PUTO",
        "QULO", "RATA", "RUIN"
    )

    if (letrasRfc in palabrasInconvenientes) {
        letrasRfc = letrasRfc.substring(0, 3) + "X"
    }

    val fechaFormateada = fec.padEnd(6, 'X').substring(0, 6)

    return letrasRfc + fechaFormateada
}