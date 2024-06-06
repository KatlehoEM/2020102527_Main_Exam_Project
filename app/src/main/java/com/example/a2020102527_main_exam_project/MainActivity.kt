package com.example.a2020102527_main_exam_project
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a2020102527_main_exam_project.ui.canvas.CanvasScreen
import com.example.a2020102527_main_exam_project.ui.home.Home
import com.example.a2020102527_main_exam_project.ui.Routes
import com.example.a2020102527_main_exam_project.ui.detail.DetailsScreen
import com.example.a2020102527_main_exam_project.ui.list.ListScreen
import com.example.a2020102527_main_exam_project.ui.maps.ShowMap
import com.example.a2020102527_main_exam_project.ui.theme.Color3
import com.example.a2020102527_main_exam_project.ui.theme._2020102527_Main_Exam_ProjectTheme
import kotlinx.coroutines.launch

/*
This code sets up a Compose UI with a navigation drawer,
top app bar, and bottom app bar, using ModalNavigationDrawer, TopAppBar, BottomAppBar, and NavHost.
 It handles navigation between different screens (Home, List, Detail, Canvas, Maps) and
 manages the state of the selected icon in the bottom app bar.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _2020102527_Main_Exam_ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavDrawer()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(){
    val navigationController = rememberNavController()
    val coroutineScore = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current.applicationContext
    val selected = remember{
        mutableStateOf(Icons.Default.Home)
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled =  true,
        drawerContent = {
            ModalDrawerSheet{
                Box(modifier = Modifier
                    .background(Color3)
                    .fillMaxWidth()
                    .height(150.dp)) {
                    Image(
                        modifier = Modifier.size(450.dp),
                        painter = painterResource(id = R.drawable.logos),
                        contentDescription = "Logo Image"
                    )
                }
                Divider()
                NavigationDrawerItem(
                    label = {Text(text = "Home", color = Color3)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = Color3) },
                    onClick = {
                        coroutineScore.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Routes.Home.name){
                            popUpTo(0)
                        }
                    }
                )

                NavigationDrawerItem(
                    label = {Text(text = "Shopping List", color = Color3)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.List, contentDescription = "Shopping List", tint = Color3) },
                    onClick = {
                        coroutineScore.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Routes.List.name){
                            popUpTo(0)
                        }
                    }
                )

                NavigationDrawerItem(
                    label = {Text(text = "Draw on Canvas", color = Color3)},
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Create, contentDescription = "Draw on Canvas", tint = Color3) },
                    onClick = {
                        coroutineScore.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Routes.Canvas.name){
                            popUpTo(0)
                        }
                    }
                )

                NavigationDrawerItem(
                    label = {Text(text = "Show Shops on Map", color = Color3)},
                    selected = false,
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.pin),
                            contentDescription = "Show Shops on Map",
                            tint = Color3,
                            modifier = Modifier.size(28.dp)) },
                    onClick = {
                        coroutineScore.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(Routes.Maps.name){
                            popUpTo(0)
                        }
                    }
                )

            }
        }
    ) {
        Scaffold(
            topBar = {
                val coroutineScope = rememberCoroutineScope()
                TopAppBar(title = { Text(text = "Shopping List App") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color3,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScore.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Rounded.Menu, contentDescription = null
                            )
                        }
                    }
                )

            },
            bottomBar = {
                BottomAppBar(containerColor = Color3) {
                    IconButton(onClick = {
                        selected.value = Icons.Default.Home
                        navigationController.navigate(Routes.Home.name){
                            popUpTo(0)
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if(selected.value == Icons.Default.Home) Color.White else Color.DarkGray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.List
                        navigationController.navigate(Routes.List.name){
                            popUpTo(0)
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.List,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if(selected.value == Icons.Default.List) Color.White else Color.DarkGray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.Create
                        navigationController.navigate(Routes.Canvas.name){
                            popUpTo(0)
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Create,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp),
                            tint = if(selected.value == Icons.Default.Create) Color.White else Color.DarkGray
                        )
                    }

                    IconButton(onClick = {
                        selected.value = Icons.Default.Email
                        navigationController.navigate(Routes.Maps.name){
                            popUpTo(0)
                        }
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            painter = painterResource(id = R.drawable.pin),
                            contentDescription = "Pin Icon",
                            tint = if (selected.value == Icons.Default.Email) Color.White else Color.DarkGray,
                            modifier = Modifier.size(30.dp)
                        )

                    }
                }
            }
        ) {
            NavHost(navController = navigationController,
                startDestination = Routes.Home.name ){
                composable(route = Routes.Home.name){ Home(navigationController) }
                composable(route = Routes.List.name){
                    ListScreen(onNavigate = {id ->
                        navigationController.navigate(route = "${Routes.Detail.name}?id=$id")
                    })
                }
                composable(
                    route = "${Routes.Detail.name}?id={id}",
                    arguments = listOf(navArgument("id"){type = NavType.IntType})

                ){
                    val id = it.arguments?.getInt("id") ?: -1
                    DetailsScreen(id = id) {
                        navigationController.navigateUp()
                    }
                }
                composable(Routes.Canvas.name){ CanvasScreen() }
                composable(Routes.Maps.name){ ShowMap(context) }

            }

        }

    }
}