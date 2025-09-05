package com.anas.a30days


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anas.a30days.data.Days
import com.anas.a30days.data.days
import com.anas.a30days.ui.theme._30DaysTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _30DaysTheme {
                Surface(
                    modifier = Modifier
                ) {
                    DayApp()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DayApp() {
    Scaffold(
        topBar = { TopBar() }
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                DayList(dayList = days)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.topicon),
                    contentDescription = "TopIcon",
                    modifier = Modifier
                        .padding(16.dp)
                )
                Text(
                    text = stringResource(R.string.top_bar),
                    fontSize = 24.sp,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}


@Composable
fun DayList(
    dayList: List<Days>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf<Days?>(null) }
    var previousSelectedDay by remember { mutableStateOf<Days?>(null) }
    Column {
        dayList.chunked(5).forEach { rowItems ->
            Row {
                rowItems.forEach { day ->
                    DayButton(
                        days = day,
                        onClick = {
                            previousSelectedDay = selectedDay
                            selectedDay = day
                            if (selectedDay != previousSelectedDay){
                                expanded = true
                            }else{
                                expanded = false
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
        if (expanded) {
            selectedDay?.let { day ->
                DayCard(
                    day.dayResourceId,
                    day.tittle,
                    day.imageResourceId,
                    day.desc
                )
            }
        }
    }
}

@Composable
fun DayButton(
    days: Days,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    //var expanded by remember { mutableStateOf(false) }
    //Column (
    //  modifier = modifier

    //){
    Button(
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        //onClick = { expanded = !expanded },
        onClick = onClick,
        border = BorderStroke(
            0.5.dp,
            MaterialTheme.colorScheme.inverseSurface
        ),
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Text(
            text = stringResource(days.dayResourceId),
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun DayCard(
    day: Int,
    tittle: Int,
    imageResourceId: Int,
    desc: Int,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column {
            Text(
                text = stringResource(day),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = stringResource(tittle),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,

                )
            Image(
                painter = painterResource(imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(desc),
                modifier = Modifier
                    .padding(8.dp),
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    DayApp()
}