package kz.home.walletapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
    @TypeConverter
    fun storedStringToMyObjects(data: String?): List<Account?>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Account?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: List<Account?>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}