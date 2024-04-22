package com.elsharif.retrofitandroidtutorial.presintation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsharif.retrofitandroidtutorial.data.ProductRepository
import com.elsharif.retrofitandroidtutorial.data.Result
import com.elsharif.retrofitandroidtutorial.data.model.Product
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository:ProductRepository

):ViewModel() {

    private val _product =MutableStateFlow<List<Product>>(emptyList())
    val product=_product.asStateFlow()

    private val _showErrorToastChannel= Channel<Boolean>()
    val showErrorToastChannel=_showErrorToastChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            productRepository.getProductsList().collectLatest {result->

                when(result){
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { products ->
                            _product.update { products }
                        }
                    }
                }


            }

        }
    }

}