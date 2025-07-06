package com.thmanyah.thmanyahdemo.ui.common

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.thmanyah.thmanyahdemo.R
import com.thmanyah.thmanyahdemo.ui.models.home.ItemSquareData


@Composable
fun HorizontalSquareList(items: List<ItemSquareData>, header: String? = "") {
    Column {
        if (header != null) {
            HeaderUI(header = header)
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items, key = { it.id }) { item ->
                SquareItem(item)
            }
        }
    }
}

@Composable
fun SquareItem(item: ItemSquareData) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(120.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(item.imageUrl),
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Row for button and text beside it
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth() // Make row fill the width of the image
        ) {
            RoundedDurationButton(item.duration)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "أمس", // Example text beside the button
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun RoundedDurationButton(duration: Int) {
    Button(
        onClick = { /* TODO: Handle click */ },
        shape = RoundedCornerShape(50), // Fully rounded
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
        modifier = Modifier.height(32.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF20222f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Play",
                tint = Color.White, // or any color you want
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            DurationText(duration, Color.White)
        }
    }
}


fun formatDurationText(context: Context, durationSeconds: Int?): String {
    if (durationSeconds == null) return ""
    val hours = durationSeconds / 3600
    val minutes = (durationSeconds % 3600) / 60
    val seconds = durationSeconds

    return when {
        hours > 0 && minutes > 0 -> context.getString(R.string.duration_hour_minute, hours, minutes)
        hours > 0 -> context.getString(R.string.duration_hour, hours)
        minutes > 0 -> context.getString(R.string.duration_minute, minutes)
        else -> context.getString(R.string.duration_second, seconds)
    }
}

@Composable
fun DurationText(durationSeconds: Int?, color: Color) {
    val context = LocalContext.current
    val text = formatDurationText(context, durationSeconds)
    Text(text, style = MaterialTheme.typography.bodySmall, color = color)
}
