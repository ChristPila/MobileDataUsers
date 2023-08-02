package com.odc.finalappodc.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.odc.finalappodc.model.User

    @Database(entities = [User::class], version = 1, exportSchema = false)
    abstract  class MyDataBase : RoomDatabase() {
        abstract fun userDao(): User

        companion object {
            @Volatile
            private var INSTANCE: MyDataBase? = null

            fun getInstance(context: Context): MyDataBase {
                synchronized(this) {
                    var instance = INSTANCE

                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            MyDataBase::class.java,
                            "my_database"
                        ).fallbackToDestructiveMigration().build()

                        INSTANCE = instance
                    }
                    return instance
                }

            }
        }
    }