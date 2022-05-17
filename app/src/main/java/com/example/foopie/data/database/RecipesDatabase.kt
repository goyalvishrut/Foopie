package com.example.foopie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foopie.data.database.entities.FavoritesEntity
import com.example.foopie.data.database.entities.FoodJokeEntity
import com.example.foopie.data.database.entities.RecipesEntity
import com.example.foopie.models.FoodJoke

@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipiesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
}