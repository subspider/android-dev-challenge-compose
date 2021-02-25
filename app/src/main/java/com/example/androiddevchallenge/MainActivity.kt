/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.models.Puppy
import com.example.androiddevchallenge.ui.theme.MyTheme

private val allPuppies = listOf(
    Puppy(
        1,
        "Bob",
        9,
        R.drawable.dog1,
        "I'm a gorgeous dog with 9 Year and I do think I fit your family."
    ),
    Puppy(
        2,
        "Jake",
        2,
        R.drawable.dog2,
        "I'm a gorgeous dog with 2 Year and I do think I fit your family."
    ),
    Puppy(
        3,
        "Star",
        1,
        R.drawable.dog3,
        "I'm a gorgeous dog with 1 Year and I do think I fit your family."
    ),
    Puppy(
        4,
        "Moon",
        3,
        R.drawable.dog4,
        "I'm a gorgeous dog with 3 Year and I do think I fit your family."
    )
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Cyan,
                elevation = 4.dp,
                title = { Text(text = "OUR PUPPIES") },
            )
        },
        content = {
            NavHost(navController = navController, startDestination = "list") {
                composable("list") {
                    PuppyListContent(
                        modifier = Modifier,
                        puppies = allPuppies,
                        onClick = {
                            navController.navigate("details/${it.id}")
                        }
                    )
                }
                composable("details/{id}") { navBackStackEntry ->
                    val selectedPuppyId = navBackStackEntry.arguments?.getString("id")
                    allPuppies.find {
                        it.id == selectedPuppyId?.toInt()
                    }?.let {
                        DetailPuppy(it)
                    }
                }
            }
        }
    )
}

@Composable
fun PuppyListContent(
    modifier: Modifier,
    puppies: List<Puppy>,
    onClick: (Puppy) -> Unit,
) {
    val listState = rememberLazyListState()
    Surface(color = MaterialTheme.colors.background) {
        LazyColumn(state = listState, modifier = modifier) {
            items(puppies) { item: Puppy ->
                Card(
                    modifier = modifier
                        .padding(PaddingValues(16.dp))
                        .clickable {
                            onClick(item)
                        }
                ) {
                    Column {
                        Image(
                            painterResource(id = item.dogImage),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Text(text = item.name, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        PuppyListContent(modifier = Modifier, puppies = allPuppies, onClick = { })
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        PuppyListContent(modifier = Modifier, puppies = allPuppies, onClick = { })
    }
}

/* DETAILS */

@Composable
fun DetailPuppy(puppy: Puppy) {
    Box {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id = puppy.dogImage),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Row(modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp)) {
                    Text(text = "Name:", style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(text = puppy.name, modifier = Modifier.padding(10.dp, 0.dp))
                }
                Row(modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp)) {
                    Text(text = "Age:", style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(text = "${puppy.age}", modifier = Modifier.padding(10.dp, 0.dp))
                }
                Row(modifier = Modifier.padding(16.dp)) {
                    Text(text = puppy.description)
                }
            }
        }
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreviewDogDetails() {
    DetailPuppy(puppy = allPuppies[1])
}
