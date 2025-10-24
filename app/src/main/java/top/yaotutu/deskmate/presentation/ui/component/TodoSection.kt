package top.yaotutu.deskmate.presentation.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import top.yaotutu.deskmate.data.model.TodoItem

@Composable
fun TodoSection(
    todoItems: List<TodoItem>,
    onTodoToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "今日待办",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        todoItems.forEach { todoItem ->
            TodoItemRow(
                todoItem = todoItem,
                onToggle = { onTodoToggle(todoItem.id) }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

@Composable
private fun TodoItemRow(
    todoItem: TodoItem,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Checkbox
        Surface(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 2.dp,
                    color = if (todoItem.isCompleted) Color(0xFF2196F3) else Color(0xFFCCCCCC),
                    shape = CircleShape
                ),
            shape = CircleShape,
            color = if (todoItem.isCompleted) Color(0xFF2196F3) else Color.Transparent
        ) {
            if (todoItem.isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "已完成",
                    tint = Color.White,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }

        // Title
        Text(
            text = todoItem.title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier.padding(start = 14.dp)
        )
    }
}
