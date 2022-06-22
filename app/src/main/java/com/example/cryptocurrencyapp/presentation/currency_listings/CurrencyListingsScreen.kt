package com.example.cryptocurrencyapp.presentation.currency_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.cryptocurrencyapp.util.UiEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurrencyListingsScreen(
    viewModel: CurrencyListingsViewModel = hiltViewModel(),
) {

    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = scaffoldState) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.currenciesList.size) { i ->
                val item = state.currenciesList[i]
                if (i >= state.currenciesList.size - 1 && !state.atTheEnd) {
                    viewModel.onEvent(CurrencyListingsEvent.OnPaginateList)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Text(
                            text = "${i + 1}.",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(80.dp)
                        ) {
                            AsyncImage(
                                model = "https://s2.coinmarketcap.com/static/img/coins/64x64/${item.id}.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(
                                        CircleShape
                                    ),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = item.name,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.subtitle2,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            text = "$${item.price}"
                        )
                        Text(
                            text = item.symbol,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle1
                        )

                    }
                }
                if (i < state.currenciesList.size - 1) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
            item {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}




