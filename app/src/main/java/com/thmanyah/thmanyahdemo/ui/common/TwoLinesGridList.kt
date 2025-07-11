package com.thmanyah.thmanyahdemo.ui.common

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.thmanyah.thmanyahdemo.R
import com.thmanyah.thmanyahdemo.ui.models.home.ItemTwoLinesGridData

@Composable
fun HorizontalTwoLinesGridList(items: List<ItemTwoLinesGridData>, header: String? = "") {
    val groupedEpisodes = items.chunked(2)

    Column {
        if (header != null) {
            HeaderUI(header = header)
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(groupedEpisodes, key = { it.first().id }) { pair ->
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    pair.forEach { item ->
                        EpisodeCard(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeCard(item: ItemTwoLinesGridData) {
    Row(
        modifier = Modifier
            .width(300.dp)
            .height(IntrinsicSize.Min)
            .background(Color(0xFF121212), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image
        Image(
            painter = rememberAsyncImagePainter(item.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Texts
        Column(modifier = Modifier.weight(1f)) {
            RelativeTimeText(
                diff = item.releaseDate ,
                color = Color.Gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.title ?: "",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedDurationButton(item.duration)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Row(
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Play",
                            tint = Color.White, // or any color you want
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Play",
                            tint = Color.White, // or any color you want
                            modifier = Modifier.size(18.dp)
                        )

                    }
                }

            }
        }
    }
}

fun getRelativeTimeFromNow(context: Context, diff: List<Long>?): String {
    if (diff == null) return ""

    val (minutes, hours, days, months, years) = diff

    return when {
        minutes < 1 -> context.getString(R.string.relative_time_now)
        minutes == 1L -> context.getString(R.string.relative_time_minute_ago)
        minutes < 60 -> context.getString(R.string.relative_time_minutes_ago, minutes)
        hours == 1L -> context.getString(R.string.relative_time_hour_ago)
        hours < 24 -> context.getString(R.string.relative_time_hours_ago, hours)
        days == 1L -> context.getString(R.string.relative_time_day_ago)
        days < 30 -> context.getString(R.string.relative_time_days_ago, days)
        months == 1L -> context.getString(R.string.relative_time_month_ago)
        months < 12 -> context.getString(R.string.relative_time_months_ago, months)
        years == 1L -> context.getString(R.string.relative_time_year_ago)
        else -> context.getString(R.string.relative_time_years_ago, years)
    }
}

@Composable
fun RelativeTimeText(
    diff: List<Long>?,
    color: Color,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
) {
    val context = LocalContext.current
    val relativeTime = getRelativeTimeFromNow(context, diff)
    Text(
        text = relativeTime, color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        maxLines = maxLines,
        textAlign = textAlign,
        modifier = modifier
    )
}





