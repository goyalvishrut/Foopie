package com.example.foopie.util

class Constants {

    companion object{

        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val API_KEY = "54c2acc55beb4f7596a0b8f248658e5a"
//        const val API_KEY = "c47fa7a0ad1f47a4a0db418234af7d50"
//        const val API_KEY = ""

        const val RECIPE_RESULT_KEY ="recipeBundle"

        //API Queries Keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //ROOM Databse
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FAVORITE_RECIPES_TABLE = "favorite_recipe_table"
        const val FOOD_JOKE_TABLE = "food_joke_table"

        // Bottom Sheet and Preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_NUTRIENT_TYPE = "minCarbs"
        const val DEFAULT_NUTRIENT_VALUE = "10"

        const val PREFERENCES_NAME = "foopie_preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"

        const val PREFERENCES_BACK_ONLINE = "backOnline"
    }

}