package com.example.a2020102527_main_exam_project.ui.home
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a2020102527_main_exam_project.R
import com.example.a2020102527_main_exam_project.ui.Routes
import com.example.a2020102527_main_exam_project.ui.theme.Color3

/*
This code defines a composable function Home() using Jetpack Compose to create the UI for
the home screen of an Android app. It includes header text, a featured image, a welcome message,
 action buttons, and a footer message, all styled using Material Design components and themes.
 */
@Composable
fun Home(navigationController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Header
        Text(
            text = "Hey, Welcome",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF1C1C1E)
            ),
            modifier = Modifier.padding(top = 84.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Featured Image
        Image(
            painter = painterResource(id = R.drawable.featured_image),
            contentDescription = "Featured Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color(0xFFb8bfba))
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Welcome Message
        Text(
            text = "Discover amazing features and enjoy a seamless experience.",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF3A3A3C)
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    navigationController.navigate(Routes.List.name)
                          },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color3)
            ) {
                Text(text = "View List", color = Color.White)
            }

            OutlinedButton(
                onClick = {
                          navigationController.navigate(Routes.Canvas.name)
                          },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Draw", color = Color3)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Footer Message
        Text(
            text = "Thank you for choosing MyApp!",
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color(0xFF3A3A3C)
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}