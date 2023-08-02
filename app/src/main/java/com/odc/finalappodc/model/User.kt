package com.odc.finalappodc.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

    @Entity(tableName = "tb_users")
    data class User(@PrimaryKey(autoGenerate = true) var id:Int,
                    var email: String,
                    var first_name:String,
                    var last_name:String,
                    var avatar:String)



@Dao
interface UserDAO{
    @Insert
    fun insert(data: User):Long

    @Query("SELECT * FROM tb_users ORDER BY id DESC")
    fun select(): Flow<List<User>>

}