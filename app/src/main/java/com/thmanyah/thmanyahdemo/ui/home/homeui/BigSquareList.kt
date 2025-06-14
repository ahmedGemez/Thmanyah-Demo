package com.thmanyah.thmanyahdemo.ui.home.homeui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.thmanyah.thmanyahdemo.R
import com.thmanyah.thmanyahdemo.ui.models.home.ItemBigSquareData
import java.util.UUID


@Composable
fun ShowHorizontalBigSquareList(items: List<ItemBigSquareData>, header: String? = "") {
    Column {
        if (header != null) {
            HeaderUI(header = header)
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(items,key = { UUID.randomUUID() }) { item ->
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(item.imageUrl),
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.6f)
                                    ),
                                    startY = 60f
                                )
                            )
                    )

                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.BottomEnd,

                        ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                                maxLines = 2,
                                textAlign = TextAlign.Start
                            )
                            if (item.episodeCount != null)
                                Text(
                                    text = "${item.episodeCount}  ${stringResource(R.string.eposids)}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                                )

                        }
                    }

                }
            }
        }
    }


}