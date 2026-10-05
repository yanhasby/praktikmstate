package com.example.praktikum_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktikum_4.ui.theme.Praktikum_4Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Praktikum_4Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    TicketPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TicketPage(
    modifier: Modifier = Modifier
) {

    // STATE DIKELOLA OLEH PARENT
    var hargaTiket by remember { mutableStateOf(50000) }
    var jumlahTiket by remember { mutableStateOf(1) }
    var namaPembeli by remember { mutableStateOf("") }

    // State untuk menjalankan proses pemesanan
    var orderRequest by remember { mutableStateOf(0) }

    // State status
    var status by remember {
        mutableStateOf("Silakan pesan tiket")
    }

    // LAUNCHED EFFECT
    LaunchedEffect(orderRequest) {

        // Jika belum ada proses pemesanan
        if (orderRequest == 0) {
            return@LaunchedEffect
        }

        // Validasi nama
        if (namaPembeli.isBlank()) {
            status = "Nama masih kosong"
            return@LaunchedEffect
        }

        // Proses pemesanan
        status = "Memproses pesanan........."

        // Tunggu 5 detik
        delay(5000)

        // Pesanan berhasil
        status = "Tiket telah dipesan"
    }

    TicketScreen(
        modifier = modifier,
        hargaTiket = hargaTiket,
        jumlahTiket = jumlahTiket,
        namaPembeli = namaPembeli,
        status = status,

        onNamaChange = {
            namaPembeli = it
        },

        onTambahTiket = {
            jumlahTiket++
        },

        onKurangiTiket = {
            if (jumlahTiket > 1) {
                jumlahTiket--
            }
        },

        onPesanTiket = {
            orderRequest++
        }
    )
}


@Composable
fun TicketScreen(
    modifier: Modifier = Modifier,
    hargaTiket: Int,
    jumlahTiket: Int,
    namaPembeli: String,
    status: String,
    onNamaChange: (String) -> Unit,
    onTambahTiket: () -> Unit,
    onKurangiTiket: () -> Unit,
    onPesanTiket: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Pemesanan Tiket",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // NAMA PEMBELI
        Text(
            text = "Nama",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = namaPembeli,
            onValueChange = onNamaChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Masukkan nama Anda")
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        // HARGA TIKET
        Text(
            text = "Harga Tiket",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Rp $hargaTiket",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // JUMLAH TIKET
        Text(
            text = "Jumlah Tiket",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = onKurangiTiket
            ) {
                Text("-")
            }

            Text(
                text = "$jumlahTiket",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onTambahTiket
            ) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TOTAL HARGA
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Total: Rp ${hargaTiket * jumlahTiket}",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BUTTON PESAN
        Button(
            onClick = onPesanTiket,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pesan Tiket")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // STATUS
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Status: $status",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TicketPreview() {
    Praktikum_4Theme {
        TicketPage()
    }
}
