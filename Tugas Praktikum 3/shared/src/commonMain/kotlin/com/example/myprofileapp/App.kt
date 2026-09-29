package com.example.myprofileapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

import myprofileapp.shared.generated.resources.Res
import myprofileapp.shared.generated.resources.bronyaback
import myprofileapp.shared.generated.resources.bronya

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()){
            Image(

                painter = painterResource(Res.drawable.bronyaback),
                contentDescription = "Background Profile",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            ProfileCard()
        }
        }
    }
}

@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.bronya),
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, Color.DarkGray, CircleShape),

        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = "R. Askarrofi Prabularizda Anggoro",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Mahasiswa Teknik Informatika",
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProfileCard() {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {

            ProfileHeader()

            Spacer(modifier = Modifier.height(24.dp))
            Spacer(modifier = Modifier.height(16.dp))

            InfoItem(label = "Email", value = "raskarrafi.124140070@student.itera.ac.id")
            InfoItem(label = "Phone", value = "0895-7001-951750")
            InfoItem(label = "Location", value = "Bandar Lampung, Indonesia")

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    uriHandler.openUri("https://github.com/Askarrofi")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Kunjungi GitHub", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}