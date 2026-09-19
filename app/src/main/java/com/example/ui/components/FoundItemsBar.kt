package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SceneTarget

@Composable
fun FoundItemsBar(
    targets: List<SceneTarget>,
    modifier: Modifier = Modifier
) {
    var selectedClueTarget by remember { mutableStateOf<SceneTarget?>(null) }
    val foundCount = targets.count { it.isDiscovered }

    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("found_items_bar")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Header: Objects to Find: X/5
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Hidden Objects",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = "$foundCount / ${targets.size} Found",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (foundCount == targets.size) Color(0xFF27AE60) else MaterialTheme.colorScheme.primary
                )
            }

            // Horizontal row of 5 objects
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                items(targets, key = { it.id }) { target ->
                    TargetChip(
                        target = target,
                        onClick = {
                            selectedClueTarget = if (selectedClueTarget?.id == target.id) null else target
                        }
                    )
                }
            }

            // Clue banner when an item is tapped
            selectedClueTarget?.let { target ->
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Clue: ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = target.clue,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TargetChip(
    target: SceneTarget,
    onClick: () -> Unit
) {
    val isDiscovered = target.isDiscovered
    val backgroundColor by animateColorAsState(
        targetValue = if (isDiscovered) Color(0xFFE8F8F0) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        label = "chip_bg"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isDiscovered) Color(0xFF27AE60) else MaterialTheme.colorScheme.onSurface,
        label = "chip_content"
    )

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(14.dp),
        tonalElevation = if (isDiscovered) 0.dp else 2.dp,
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag("target_chip_${target.id}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        if (isDiscovered) Color(0xFF27AE60)
                        else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
            ) {
                if (isDiscovered) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Found",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = target.name,
                fontSize = 12.sp,
                fontWeight = if (isDiscovered) FontWeight.Normal else FontWeight.SemiBold,
                color = contentColor,
                textDecoration = if (isDiscovered) TextDecoration.LineThrough else TextDecoration.None
            )
        }
    }
}
