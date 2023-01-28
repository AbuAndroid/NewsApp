package com.example.viewmodellivedata.manager

import android.content.Context
import android.content.SharedPreferences.Editor
import android.util.Log
import com.example.viewmodellivedata.model.Article
import com.example.viewmodellivedata.warehouse.Constants.Companion.SHARED_PREFERENCE_NAME
import com.example.viewmodellivedata.warehouse.Constants.Companion.USER_NAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferenceHandler(context: Context) {

    private val sharedPreference =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor: Editor = sharedPreference.edit()

    /* This Function returns all savedNewsList From DB*/
    fun getSavedList(): MutableList<Article> {
        var savedList: MutableList<Article> = mutableListOf()
        val user = sharedPreference.getString(USER_NAME, null)
        return if (user?.isNotEmpty() == true) {
            val type = object : TypeToken<ArrayList<Article>>() {}.type
            savedList = Gson().fromJson<ArrayList<Article>>(user, type)
            savedList
        } else {
            savedList.toMutableList()
        }
    }

    /*save userSelected Item in DB*/
    fun saveItemToDb(item: Article) {
        Log.e("preferenceSave", item.isSaved.toString())
        val getUserList = getSavedList()
        val userModel = getUserList.find { item.title == it.title }
        if (userModel == null) {
            item.let { getUserList.add(it) }
            val list = Gson().toJson(getUserList)
            editor.putString(USER_NAME, list)?.apply()
        }
    }

    /*Remove UserSelected Item in DB*/
    fun removeItemInDb(item: Article) {
        Log.e("preferenceSave", item.isSaved.toString())
        val allSavedNewsList = getSavedList()
        val userModel = allSavedNewsList.find { it.title == item.title }
        if (userModel != null) {
            item.let { allSavedNewsList.remove(userModel) }
            val list = Gson().toJson(allSavedNewsList)
            editor.putString(USER_NAME, list)?.apply()
        }
    }

    /* This Function is Used to save specific position in Saved News List*/
    fun saveItemToSpecificPosition(item: Article, position: Int) {
        val getUserList = getSavedList()
        val userModel = getUserList.find { item.title == it.title }
        if (userModel == null) {
            item.let { getUserList.add(position, it) }
            val list = Gson().toJson(getUserList)
            editor.putString(USER_NAME, list)?.apply()
        }
    }

}