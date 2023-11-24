package ru.dancat.metasearcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.dancat.metasearcher.ui.view.Searching
import ru.dancat.metasearcher.ui.view.Studio

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Searching()
//            Studio()
//            MetasearcherApplicationTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MetasearcherApplicationTheme {
//        Greeting("Android")
//    }
//}