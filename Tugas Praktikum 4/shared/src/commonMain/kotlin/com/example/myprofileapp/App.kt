package com.example.myprofileapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.painterResource

import myprofileapp.shared.generated.resources.Res
import myprofileapp.shared.generated.resources.bronyaback
import myprofileapp.shared.generated.resources.bronya
import myprofileapp.shared.generated.resources.bronyadark

class ProfileViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun darkMode(isDark: Boolean){
        _uiState.update { it.copy(isDarkMode = isDark)}
    }
    fun edit(isEdited: Boolean) {
        _uiState.update {
            if (isEdited) {
                it.copy(isEdited = true, editName = it.name, editBio = it.bio)
            } else {
                it.copy(isEdited = false)
            }
        }
    }

    fun editName(name: String) {
        _uiState.update { it.copy(editName = name) }
    }

    fun editBio(bio: String) {
        _uiState.update { it.copy(editBio = bio) }
    }

    fun save() {
        _uiState.update {
            it.copy(
                name = it.editName,
                bio = it.editBio,
                isEdited = false
            )
        }
    }
}

data class ProfileUiState(
    val name: String = "R. Askarrofi Prabularizda Anggoro",
    val bio: String = "Mahasiswa Teknik Informatika",
    val isDarkMode: Boolean = false,
    val isEdited: Boolean = false,
    val editName: String = "",
    val editBio: String = "",
)

@Composable
@Preview
fun App(viewModel: ProfileViewModel = viewModel()) {


    val uiState by viewModel.uiState.collectAsState()


    val colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        var showContent by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxSize()){
            if (uiState.isDarkMode == true){
                Image(
                    painter = painterResource(Res.drawable.bronyadark),
                    contentDescription = "Background Profile",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }else{
                Image(
                    painter = painterResource(Res.drawable.bronyaback),
                    contentDescription = "Background Profile",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray.copy(alpha = 0.5f)),
        ) {
            Switch(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 40.dp, end = 20.dp),
                checked = uiState.isDarkMode,
                onCheckedChange = { viewModel.darkMode(it) }
            )
            AnimatedVisibility(
                visible = showContent,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    ProfileCard(uiState = uiState, viewModel = viewModel)
                }
            }
            Button(onClick = { showContent = !showContent },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurfaceVariant))
            {
                Text(if (showContent) "Close" else "Open")
            }
        }
        }
    }
}

@Composable
fun ProfileHeader(uiState: ProfileUiState, viewModel: ProfileViewModel) {
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
                text = uiState.name,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = uiState.bio,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProfileCard(uiState: ProfileUiState, viewModel: ProfileViewModel) {
    val uriHandler = LocalUriHandler.current

    val cardColor = if (uiState.isDarkMode){
        MaterialTheme.colorScheme.surface.copy(0.85f)
    }else{
        Color.White.copy(0.85f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {


            ProfileHeader(uiState = uiState, viewModel = viewModel)

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isEdited){
                Form(
                    label = "Nama",
                    value = uiState.editName,
                    onValueChange = {viewModel.editName(it)}
                )
                Form(
                    label = "Bio",
                    value = uiState.editBio,
                    onValueChange = {viewModel.editBio(it)}
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { viewModel.edit(false) },
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurfaceVariant)
                    ) {
                        Text("Batal")
                    }
                    Button(
                        onClick = { viewModel.save() },
                        modifier = Modifier.weight(1f).padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurfaceVariant)
                    ) {
                        Text("Simpan")
                    }
                }
            }else{
                Button(
                    onClick = {
                        viewModel.edit(true)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Text("Edit", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

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
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onSurfaceVariant)
            ) {
                Text("Kunjungi GitHub", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun Form(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        singleLine = true
    )
}