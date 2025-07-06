package com.thmanyah.thmanyahdemo.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.thmanyah.thmanyahdemo.ui.models.home.ItemQueueData

@Composable
fun QueueHorizontalList(items: List<ItemQueueData>, header: String? = "") {
    Column {
        if (header != null) {
            HeaderUI(header = header)
        }
        LazyRow(state = rememberLazyListState(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items, key = { it.id}) { item ->
                Box(
                    modifier = Modifier
                        .height(140.dp)
                        .fillParentMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF23232B))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Stacked images
                        Box(
                            modifier = Modifier.width(130.dp).height(130.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(item.image),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )

                        }
                        Spacer(modifier = Modifier.width(16.dp))

                        // Episode info
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White,
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                DurationText(item.duration, Color.Red)
                                Spacer(modifier = Modifier.width(8.dp))
                                if (item.timeAgo != null)
                                    RelativeTimeText(item.timeAgo, Color.White)
                            }
                        }
                        // Play button
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Bottom) // aligns this Box to bottom of Row
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }


                    }
                }
            }
        }
    }
}