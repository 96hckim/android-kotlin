package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TopLevel()
                }
            }
        }
    }
}

@Composable
fun TopLevel() {
    val (text, setText) = remember { mutableStateOf("") }
    val toDoList = remember { mutableStateListOf<ToDoData>() }

    val onSubmit: (String) -> Unit = {
        val key = (toDoList.lastOrNull()?.key ?: 0) + 1
        toDoList.add(ToDoData(key, it))
        setText("")
    }

    val onEdit: (Int, String) -> Unit = { key, newText ->
        val i = toDoList.indexOfFirst { it.key == key }
        if (i != -1) {
            toDoList[i] = toDoList[i].copy(text = newText)
        }
    }

    val onToggle: (key: Int, checked: Boolean) -> Unit = { key, checked ->
        val i = toDoList.indexOfFirst { it.key == key }
        if (i != -1) {
            toDoList[i] = toDoList[i].copy(done = checked)
        }
    }

    val onDelete: (Int) -> Unit = { key ->
        val i = toDoList.indexOfFirst { it.key == key }
        if (i != -1) {
            toDoList.removeAt(i)
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            ToDoInput(
                text = text,
                onTextChange = setText,
                onSubmit = onSubmit
            )

            LazyColumn {
                items(
                    items = toDoList,
                    key = { it.key }
                ) { toDoData ->
                    ToDo(
                        toDoData = toDoData,
                        onEdit = onEdit,
                        onToggle = onToggle,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoTheme {
        TopLevel()
    }
}

@Composable
fun ToDoInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = {
            onSubmit(text)
        }) {
            Text("입력")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoInputPreview() {
    ToDoTheme {
        ToDoInput("테스트", {}, {})
    }
}

@Composable
fun ToDo(
    toDoData: ToDoData,
    onEdit: (key: Int, text: String) -> Unit = { _, _ -> },
    onToggle: (key: Int, checked: Boolean) -> Unit = { _, _ -> },
    onDelete: (key: Int) -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp
    ) {
        Crossfade(targetState = isEditing, modifier = Modifier.padding(8.dp)) { state ->
            if (state) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val (newText, setNewText) = remember { mutableStateOf(toDoData.text) }

                    OutlinedTextField(
                        value = newText,
                        onValueChange = setNewText,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Button(
                        onClick = {
                            onEdit(toDoData.key, newText)
                            isEditing = false
                        }
                    ) {
                        Text(text = "완료")
                    }
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = toDoData.text,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "완료"
                    )
                    Checkbox(
                        checked = toDoData.done,
                        onCheckedChange = { checked ->
                            onToggle(toDoData.key, checked)
                        }
                    )
                    Button(
                        onClick = {
                            isEditing = true
                        }
                    ) {
                        Text(text = "수정")
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Button(
                        onClick = {
                            onDelete(toDoData.key)
                        }
                    ) {
                        Text(text = "삭제")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoPreview() {
    ToDoTheme {
        ToDo(ToDoData(1, "nice", true))
    }
}

data class ToDoData(
    val key: Int,
    val text: String,
    val done: Boolean = false
)
