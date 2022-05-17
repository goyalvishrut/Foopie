package com.example.foopie.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foopie.data.DataStoreRepository
import com.example.foopie.data.MealAndDietType
import com.example.foopie.data.NutrientTypeAndValue
import com.example.foopie.util.Constants.Companion.API_KEY
import com.example.foopie.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foopie.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foopie.util.Constants.Companion.DEFAULT_NUTRIENT_TYPE
import com.example.foopie.util.Constants.Companion.DEFAULT_NUTRIENT_VALUE
import com.example.foopie.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foopie.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foopie.util.Constants.Companion.QUERY_API_KEY
import com.example.foopie.util.Constants.Companion.QUERY_DIET
import com.example.foopie.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foopie.util.Constants.Companion.QUERY_NUMBER
import com.example.foopie.util.Constants.Companion.QUERY_SEARCH
import com.example.foopie.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private lateinit var mealAndDiet: MealAndDietType

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    private lateinit var nutrient: NutrientTypeAndValue

    private var nutrientsType = DEFAULT_NUTRIENT_TYPE
    private var nutrientsValue = DEFAULT_NUTRIENT_VALUE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readNutrientTypeAndValue = dataStoreRepository.readNutrientTypeAndValue
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()


    fun saveMealAndDietType() =
        viewModelScope.launch(Dispatchers.IO) {
            if (this@RecipesViewModel::mealAndDiet.isInitialized) {
                dataStoreRepository.saveMealAndDietType(
                    mealAndDiet.selectedMealType,
                    mealAndDiet.selectedMealTypeId,
                    mealAndDiet.selectedDietType,
                    mealAndDiet.selectedDietTypeId
                )
            }
        }

    fun saveMealAndDietTypeTemp(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        mealAndDiet = MealAndDietType(
            mealType,
            mealTypeId,
            dietType,
            dietTypeId
        )
    }


    fun saveNutrientTypeAndValue() =
        viewModelScope.launch(Dispatchers.IO) {
            if (this@RecipesViewModel::nutrient.isInitialized) {
                dataStoreRepository.saveNutrientAndValueType(
                    nutrient.selectedNutrientsType,
                    nutrient.selectedNutrientsTypeId,
                    nutrient.selectedNutrientsValue,
                    nutrient.selectedNutrientsValueId
                )
            }
        }

    fun saveNutrientTypeAndValueTemp(
        nutrientsType: String,
        nutrientsTypeId: Int,
        nutrientsValue: String,
        nutrientsValueId: Int
    ) {
        nutrient = NutrientTypeAndValue(
            nutrientsType,
            nutrientsTypeId,
            nutrientsValue,
            nutrientsValueId
        )
    }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"


        if (this@RecipesViewModel::mealAndDiet.isInitialized) {
            Log.d("RecipesViewModel", "mealAndDiet.isInitialized")
            queries[QUERY_TYPE] = mealAndDiet.selectedMealType
            queries[QUERY_DIET] = mealAndDiet.selectedDietType
        } else {
            Log.d("RecipesViewModel", "mealAndDiet not Initialized")
            queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
            queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        }

        if (this@RecipesViewModel::nutrient.isInitialized) {
            Log.d("RecipesViewModel", "nutrientTypeAndValue.isInitialized")
            queries[nutrient.selectedNutrientsType] = nutrient.selectedNutrientsValue
        } else {
            Log.d("RecipesViewModel", "nutrientTypeAndValue not Initialized")
        }
//
//        Log.d(
//            "RecipesViewModel",
//            "nutrientsType = ${nutrient.selectedNutrientsType} nutrientValue = ${nutrient.selectedNutrientsValue}"
//        )

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }


    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "Internet Restored", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}