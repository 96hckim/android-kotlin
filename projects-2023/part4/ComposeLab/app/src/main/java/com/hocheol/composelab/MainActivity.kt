package com.hocheol.composelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hocheol.composelab.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                RowExample()
            }
        }
    }
}

@Composable
fun TextExample(name: String, modifier: Modifier = Modifier) {
    Text(
        color = Color(0xFFFF9944), // Color.Red,
        text = "Hello $name!",
        modifier = modifier,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Cursive,
        letterSpacing = 2.sp, // 자간
        maxLines = 2,
        textDecoration = TextDecoration.Underline,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ButtonExample(onButtonClicked: () -> Unit) {
    Button(
        onClick = onButtonClicked,
        enabled = true,
        border = BorderStroke(10.dp, Color.Magenta),
        shape = CircleShape,
        contentPadding = PaddingValues(20.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Send,
            contentDescription = "Send"
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = "Send")
    }
}

@Composable
fun ModifierExample() {
    // fillMaxSize()
    // height(dp)
    // width(dp)
    // size(dp, dp)
    // background(color)
    // colors = ButtonDefaults.buttonColors(containerColor, contentColor)
    // padding(dp)
    // enabled, clickable
    // offset(dp, dp)
    Button(
        onClick = {},
        modifier = Modifier
            .size(200.dp)
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Magenta,
            contentColor = Color.Cyan
        )
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier.background(Color.Blue)
        )
        Spacer(
            modifier = Modifier
                .size(ButtonDefaults.IconSpacing)
                .background(Color.Blue)
        )
        Text(
            "Search",
            modifier = Modifier
                .offset(x = 10.dp, y = (-10).dp)
                .background(Color.Blue)
        )
    }
}

@Composable
fun SurfaceExample() {
    // Surface 아래 View 에는 터치 전달 안된다.
    // margin 대신 윗 View 에서 padding 으로 설정
    Surface(
        modifier = Modifier.padding(5.dp),
        shadowElevation = 10.dp,
        border = BorderStroke(
            width = 2.dp,
            color = Color.Magenta
        ),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.error
    ) {
        Text(
            text = "Hello World!",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun BoxExample() {
    // Box 를 만들거나 FrameLayout 과 같이 중첩시키는 용도로 사용된다.
    // Modifier.align(Alignment)
    // matchParentSize()
    // BoxScope 에서 사용 가능 align, matchParentSize
    // fillMaxSize()
    Box {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Cyan)
                .align(Alignment.TopStart)
        )
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(Color.Yellow)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun RowExample() {
    // Row 는 가로 배치이므로 alignment 는 수직적으로 행해진다.
    // Modifier.align(Alignment) 개별 위치 조정
    // verticalAlignment(Alignment) 세로 위치 조정
    // horizontalArrangement 가로 위치 조정
    // weight
    // textAlign(TextAlign)
    Row(
        modifier = Modifier.size(200.dp, 40.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "첫 번째!",
            textAlign = TextAlign.End,
            modifier = Modifier
                .align(Alignment.Top)
                .weight(3f)
                .background(Color.Magenta)
        )
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "추가",
            modifier = Modifier
                .weight(1f)
                .background(Color.Cyan)
        )
        Text(
            text = "세 번째!",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(3f)
                .background(Color.Blue)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLabTheme {
        RowExample()
    }
}
