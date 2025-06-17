package com.thmanyah.thmanyahdemo.ui.common
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thmanyah.thmanyahdemo.ui.utils.NavigationKeys.Route.SEARCH


@Composable
fun WelcomeBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    navController: NavController
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
    Row {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Back",
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Hello Ahmed Gaber",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

    }

        IconButton(onClick = {
            navController.navigate(SEARCH)
        }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Back",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun HeaderUI(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    header:String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Back",
            modifier = Modifier.size(28.dp)
        )

    }
}