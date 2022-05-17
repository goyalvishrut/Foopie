package com.example.foopie.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.foopie.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foopie.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foopie.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.foopie.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.foopie.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.foopie.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.foopie.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.foopie.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import androidx.datastore.preferences.core.Preferences
import com.example.foopie.util.Constants.Companion.DEFAULT_NUTRIENT_TYPE
import com.example.foopie.util.Constants.Companion.DEFAULT_NUTRIENT_VALUE
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)

        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)

        val selectedNutrientsType = stringPreferencesKey("nutrientType")
        val selectedNutrientsTypeId = intPreferencesKey("nutrientTypeId")

        val selectedNutrientsValue = stringPreferencesKey("nutrientValue")
        val selectedNutrientsValueId = intPreferencesKey("nutrientValueId")

        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }

    suspend fun saveNutrientAndValueType(
        nutrientsType: String,
        nutrientsTypeId: Int,
        nutrientsTypeValue: String,
        nutrientsTypeValueId: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedNutrientsType] = nutrientsType
            preferences[PreferenceKeys.selectedNutrientsTypeId] = nutrientsTypeId
            preferences[PreferenceKeys.selectedNutrientsValue] = nutrientsTypeValue
            preferences[PreferenceKeys.selectedNutrientsValueId] = nutrientsTypeValueId
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readNutrientTypeAndValue: Flow<NutrientTypeAndValue> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedNutrientsType = preferences[PreferenceKeys.selectedNutrientsType] ?: DEFAULT_NUTRIENT_TYPE
            val selectedNutrientsTypeId = preferences[PreferenceKeys.selectedNutrientsTypeId] ?: 0
            val selectedNutrientsTypeValue = preferences[PreferenceKeys.selectedNutrientsValue]?: DEFAULT_NUTRIENT_VALUE
            val selectedNutrientsTypeValueId = preferences[PreferenceKeys.selectedNutrientsValueId]?: 0
            NutrientTypeAndValue(
                selectedNutrientsType,
                selectedNutrientsTypeId,
                selectedNutrientsTypeValue,
                selectedNutrientsTypeValueId
            )
        }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }


}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)

data class NutrientTypeAndValue(
    val selectedNutrientsType: String,
    val selectedNutrientsTypeId: Int,
    val selectedNutrientsValue: String,
    val selectedNutrientsValueId: Int
)