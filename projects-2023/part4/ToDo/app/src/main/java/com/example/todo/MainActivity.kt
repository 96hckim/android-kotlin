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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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

class ToDoViewModel : ViewModel() {
    private val _text = MutableLiveData("")
    val text: LiveData<String> = _text
    val setText: (String) -> Unit = {
        _text.value = it
    }

    // mutableStateListOf - 추가, 삭제, 대입 -> UI 가 갱신이 됩니다. 각 항목의 필드가 바뀌었을 때 -> 갱신이 안되는 문제.
    // LiveData<List<T>>.observeAsState() - List 가 통채로 다른 List 로 바뀌었을 때만 State 가 갱신된다.
    private val _rawToDoList = mutableListOf<ToDoData>()
    private val _toDoList = MutableLiveData<List<ToDoData>>(_rawToDoList)
    val toDoList: LiveData<List<ToDoData>> = _toDoList

    val onSubmit: (String) -> Unit = {
        val key = (_rawToDoList.lastOrNull()?.key ?: 0) + 1
        _rawToDoList.add(ToDoData(key, it))
        _toDoList.value = ArrayList(_rawToDoList)
        _text.value = ""
    }

    val onEdit: (Int, String) -> Unit = { key, newText ->
        val i = _rawToDoList.indexOfFirst { it.key == key }
        if (i != -1) {
            _rawToDoList[i] = _rawToDoList[i].copy(text = newText)
            _toDoList.value = ArrayList(_rawToDoList)
        }
    }

    val onToggle: (key: Int, checked: Boolean) -> Unit = { key, checked ->
        val i = _rawToDoList.indexOfFirst { it.key == key }
        if (i != -1) {
            _rawToDoList[i] = _rawToDoList[i].copy(done = checked)
            _toDoList.value = ArrayList(_rawToDoList)
        }
    }

    val onDelete: (Int) -> Unit = { key ->
        val i = _rawToDoList.indexOfFirst { it.key == key }
        if (i != -1) {
            _rawToDoList.removeAt(i)
            _toDoList.value = ArrayList(_rawToDoList)
        }
    }
}

@Composable
fun TopLevel(viewModel: ToDoViewModel = viewModel()) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            ToDoInput(
                text = viewModel.text.observeAsState("").value,
                onTextChange = viewModel.setText,
                onSubmit = viewModel.onSubmit
            )

            val items = viewModel.toDoList.observeAsState(emptyList()).value
            LazyColumn {
                items(
                    items = items,
                    key = { it.key }
                ) { toDoData ->
                    ToDo(
                        toDoData = toDoData,
                        onEdit = viewModel.onEdit,
                        onToggle = viewModel.onToggle,
                        onDelete = viewModel.onDelete
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
