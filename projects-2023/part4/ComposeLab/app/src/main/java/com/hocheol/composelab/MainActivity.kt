package com.hocheol.composelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.hocheol.composelab.ui.theme.ComposeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DropDownMenuExample()
                }
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

@Composable
fun ColumnExample() {
    Column(
        modifier = Modifier.size(100.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "첫 번째", modifier = Modifier.align(Alignment.Start))
        Text(text = "두 번째")
        Text(text = "세 번째", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun Outer() {
    // 외부에서 size 지정 시 내부에서 높게 지정하더라도 외부 size 따라감.
    Column(Modifier.width(150.dp)) {
        Inner(
            Modifier
                .width(200.dp)
                .height(160.dp)
        )
        Inner(
            Modifier
                .widthIn(min = 200.dp)
                .heightIn(min = 50.dp, max = 100.dp)
        )
    }
}

@Composable
fun Inner(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        if (maxHeight > 150.dp) {
            Text(
                text = "여기 꽤 길군요!",
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
        Text(text = "maxW:$maxWidth maxH:$maxHeight minW:$minWidth minH:$minHeight")
    }
}

@Composable
fun ImageExample() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.wall),
            contentDescription = "엔텔로프 캐년"
        )
        Image(
            imageVector = Icons.Filled.Settings,
            contentDescription = "세팅"
        )
    }
}

@Composable
fun CoilExample() {
    Column {
        val imageUrl = "https://picsum.photos/1920/1080"
        // rememberImagePainter [Deprecated]
        val painter = rememberImagePainter(data = imageUrl)
        Image(
            painter = painter,
            contentDescription = "이미지"
        )
        // Coil Image [Recommended]
        AsyncImage(
            model = imageUrl,
            contentDescription = "이미지"
        )
    }
}

@Composable
fun CheckBoxExample() {
    // 상태가 바뀔 경우 Recomposition(redraw) 이 발동된다.
    Row(verticalAlignment = Alignment.CenterVertically) {
        // by : 프로퍼티인 것 처럼 사용 가능 (delegated properties)
//        var checked by remember { mutableStateOf(false) }
//        Checkbox(
//            checked = checked,
//            onCheckedChange = {
//                checked = checked.not()
//            }
//        )

        // 비구조화 (destruction)
//        val (checked, setChecked) = remember { mutableStateOf(false) }
//        Checkbox(
//            checked = checked,
//            onCheckedChange = {
//                setChecked(checked.not())
//            }
//        )

        val (getter, setter) = remember { mutableStateOf(false) }
        Checkbox(
            checked = getter,
            onCheckedChange = setter
        )

        Text(
            text = "프로그래머입니까?",
            modifier = Modifier.clickable {
                setter(getter.not())
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldExample() {
    var name by remember { mutableStateOf("Tom") }
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text("이름")
            }
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "Hello $name")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarExample() {
    Column {
        TopAppBar(
            title = { Text("TopAppBar") },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "업 네비게이션"
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "검색"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "설정"
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.AccountBox,
                        contentDescription = "계정"
                    )
                }
            }
        )
        Text(text = "Hello Android")
    }
}

@Composable
fun CheckBoxWithSlot(
    checked: Boolean,
    onCheckedChanged: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    // 위임할 수 있는 것들은 가능한 밖에서 위임하는 형태로 만들자
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onCheckedChanged()
        }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChanged() }
        )
        content()
    }
}

@Composable
fun SlotExample() {
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(false) }

    Column {
        CheckBoxWithSlot(
            checked = checked1,
            onCheckedChanged = {
                checked1 = checked1.not()
            }
        ) {
            Text(text = "텍스트 1")
        }
        CheckBoxWithSlot(
            checked = checked2,
            onCheckedChanged = {
                checked2 = checked2.not()
            }
        ) {
            Text(text = "텍스트 2")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldExample() {
    var checked by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "뒤로가기"
                        )
                    }
                },
                title = {
                    Text(text = "Scaffold App")
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            CheckBoxWithSlot(
                checked = checked,
                onCheckedChanged = { checked = checked.not() },
            ) {
                Text(text = "Hello World!")
            }
        }
    }
}

@Composable
fun ConstraintLayoutExample() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (redBox, magentaBox, greenBox, yellowBox) = createRefs()

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .constrainAs(redBox) {
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    end.linkTo(parent.end, margin = 4.dp)
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Magenta)
                .constrainAs(magentaBox) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Green)
                .constrainAs(greenBox) {
                    centerTo(parent)
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Yellow)
                .constrainAs(yellowBox) {
                    start.linkTo(magentaBox.end)
                    top.linkTo(magentaBox.bottom)
                }
        )
    }
}

@Composable
fun ConstraintSetExample() {
    val constraintSet = ConstraintSet {
        val redBox = createRefFor("redBox")
        val magentaBox = createRefFor("magentaBox")
        val greenBox = createRefFor("greenBox")
        val yellowBox = createRefFor("yellowBox")

        constrain(redBox) {
            bottom.linkTo(parent.bottom, 10.dp)
            end.linkTo(parent.end, 30.dp)
        }
        constrain(magentaBox) {
            start.linkTo(parent.start, 10.dp)
            end.linkTo(parent.end, 30.dp)
        }
        constrain(greenBox) {
            centerTo(parent)
        }
        constrain(yellowBox) {
            start.linkTo(greenBox.end)
            top.linkTo(greenBox.bottom)
        }
    }

    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .layoutId("redBox")
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Magenta)
                .layoutId("magentaBox")
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Green)
                .layoutId("greenBox")
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Yellow)
                .layoutId("yellowBox")
        )
    }
}

@Composable
fun ConstraintChainBarrierExample() {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (redBox, yellowBox, magentaBox, text) = createRefs()

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Red)
                .constrainAs(redBox) {
                    top.linkTo(parent.top, margin = 18.dp)
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Yellow)
                .constrainAs(yellowBox) {
                    top.linkTo(parent.top, margin = 32.dp)
                }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Magenta)
                .constrainAs(magentaBox) {
                    top.linkTo(parent.top, margin = 20.dp)
                }
        )

//        createVerticalChain(redBox, yellowBox, magentaBox)
        createHorizontalChain(redBox, yellowBox, magentaBox, chainStyle = ChainStyle.SpreadInside)
        val barrier = createBottomBarrier(redBox, yellowBox, magentaBox)

        Text(
            text = "Hello World! Hello World! Hello World! Hello World! Hello World! ",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(barrier)
            }
        )
    }
}

@Composable
fun CanvasExample() {
    Canvas(modifier = Modifier.size(20.dp)) {
        drawLine(Color.Red, Offset(30f, 10f), Offset(50f, 40f))
        drawCircle(Color.Yellow, 10f, Offset(15f, 40f))
        drawRect(Color.Magenta, Offset(30f, 30f), Size(10f, 10f))

        // Search Icon
//        moveTo(2.01f, 21.0f)
//        lineTo(23.0f, 12.0f)
//        lineTo(2.01f, 3.0f)
//        lineTo(2.0f, 10.0f)
//        lineToRelative(15.0f, 2.0f)
//        lineToRelative(-15.0f, 2.0f)
//        close()
        drawLine(Color.Green, Offset(2.01f, 21.0f), Offset(23.0f, 12.0f))
        drawLine(Color.Green, Offset(23.0f, 12.0f), Offset(2.01f, 3.0f))
        drawLine(Color.Green, Offset(2.01f, 3.0f), Offset(2.0f, 10.0f))
        drawLine(Color.Green, Offset(2.0f, 10.0f), Offset(17.0f, 12.0f))
        drawLine(Color.Green, Offset(17.0f, 12.0f), Offset(2.0f, 14.0f))
        drawLine(Color.Green, Offset(2.0f, 14.0f), Offset(2.01f, 21.0f))
    }
}

@Composable
fun DialogExample() {
    var openDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }

    Column {
        Button(onClick = { openDialog = true }) {
            Text("다이얼로그 열기")
        }
        Text("카운터: $counter")
    }

    if (openDialog) {
        AlertDialog(onDismissRequest = {
            openDialog = false
        }, confirmButton = {
            Button(onClick = {
                counter++
                openDialog = false
            }) {
                Text(text = "더하기")
            }
        }, dismissButton = {
            Button(onClick = {
                openDialog = false
            }) {
                Text(text = "취소")
            }
        }, title = {
            Text(text = "더하기")
        }, text = {
            Text(text = "더하기 버튼을 누르면 카운터를 증가합니다.\n버튼을 눌러주세요.")
        })
    }
}

@Composable
fun CustomDialogExample() {
    var openDialog by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }

    Column {
        Button(onClick = {
            openDialog = true
        }) {
            Text("다이얼로그 열기")
        }
        Text("카운터: $counter")
    }

    if (openDialog) {
        Dialog(onDismissRequest = {
            openDialog = false
        }) {
            Surface {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "버튼을 클릭해주세요.\n * +1을 누르면 값이 증가합니다.\n * -1을 누르면 값이 감소합니다.")
                    Row(modifier = Modifier.align(Alignment.End)) {
                        Button(onClick = {
                            openDialog = false
                        }) {
                            Text(text = "취소")
                        }
                        Button(onClick = {
                            counter++
                            openDialog = false
                        }) {
                            Text(text = "+1")
                        }
                        Button(onClick = {
                            counter--
                            openDialog = false
                        }) {
                            Text(text = "-1")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropDownMenuExample() {
    var expandDropDownMenu by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }

    Column {
        Button(onClick = { expandDropDownMenu = true }) {
            Text("드롭다운 메뉴 열기")
        }
        Text("카운터: $counter")
    }

    DropdownMenu(
        expanded = expandDropDownMenu,
        onDismissRequest = {
            expandDropDownMenu = false
        }
    ) {
        DropdownMenuItem(
            text = {
                Text(text = "증가")
            },
            onClick = {
                counter++
            }
        )
        DropdownMenuItem(
            text = {
                Text(text = "감소")
            },
            onClick = {
                counter--
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeLabPreview() {
    ComposeLabTheme {
        DropDownMenuExample()
    }
}
