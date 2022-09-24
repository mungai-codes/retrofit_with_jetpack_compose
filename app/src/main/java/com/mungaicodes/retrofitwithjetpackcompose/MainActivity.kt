package com.mungaicodes.retrofitwithjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.mungaicodes.retrofitwithjetpackcompose.model.Movie
import com.mungaicodes.retrofitwithjetpackcompose.ui.theme.MainViewModel
import com.mungaicodes.retrofitwithjetpackcompose.ui.theme.RetrofitWithJetpackComposeTheme

class MainActivity : ComponentActivity() {

    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitWithJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MovieList(movieList = mainViewModel.movieListResponse)
                    mainViewModel.getMovieList()
                }
            }
        }
    }
}

@Composable
fun MovieList(
    movieList: List<Movie>,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember {
        mutableStateOf(-1)
    }
    LazyColumn {
        itemsIndexed(movieList) { index, movie ->
            MovieItem(movie = movie, index, selectedIndex) { i ->
                selectedIndex = i
            }
        }
    }

}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieItem(
    movie: Movie,
    index: Int,
    selectedIndex: Int,
    onClick: (Int) -> Unit,

    ) {

    val backgroundColor =
        if (index == selectedIndex) MaterialTheme.colors.primary else MaterialTheme.colors.background

    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp)
            .clickable {
                onClick(index)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        border = BorderStroke(4.dp, Color.LightGray)
    ) {
        Surface(color = backgroundColor) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = movie.imageUrl, builder = {
                            scale(Scale.FILL)
                            placeholder(R.drawable.loading_animation)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = movie.desc,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.2f)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    Text(
                        text = movie.name,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movie.category,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .background(Color.LightGray)
                            .padding(4.dp)
                    )
                    Text(
                        text = movie.desc,
                        style = MaterialTheme.typography.body1,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

    }

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    RetrofitWithJetpackComposeTheme {
//        MovieItem(movie = Movie("", "", "", ""))
//    }
//}