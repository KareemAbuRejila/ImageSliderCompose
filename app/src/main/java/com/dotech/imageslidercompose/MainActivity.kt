@file:OptIn(ExperimentalFoundationApi::class)

package com.dotech.imageslidercompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dotech.imageslidercompose.ui.theme.ImageSliderComposeTheme

class MainActivity : ComponentActivity() {
    private val images = mapOf(
        "To Gaza Sign" to R.drawable.togaza,
        "Al Baqaa Cafe" to R.drawable.baqqa,
        "Camp In Rafah" to R.drawable.camp,
        "Katayef" to R.drawable.des,
        "Destroyed Build in Gaza" to R.drawable.destroyed_house,
        "Bread In Gaza" to R.drawable.bread,

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val pagerState = rememberPagerState { images.size }
            ImageSliderComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            text = images.keys.toList()[pagerState.currentPage],
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.DarkGray
                        )
                    }
                ) { innerPadding ->
                    Content(
                        images = images,
                        modifier = Modifier.padding(innerPadding),
                        pagerState = pagerState
                    )
                }
            }
        }
    }
}

@Composable
fun Content(images: Map<String, Int>, modifier: Modifier = Modifier, pagerState: PagerState) {
    Box(modifier) {
        HorizontalPager(
            state = pagerState,
            key = { images.keys.toList()[it] },
            pageSize = PageSize.Fill
        ) { index ->
            images[images.keys.toList()[index]]?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { current ->
                val color =
                    if (current == pagerState.currentPage) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Log.d("Page change", "Page changed to $page")
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImageSliderComposeTheme {
        Content(images = emptyMap(), pagerState = rememberPagerState { 0 })
    }
}