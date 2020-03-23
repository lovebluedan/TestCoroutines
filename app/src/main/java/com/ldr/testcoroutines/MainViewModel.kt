package com.ldr.testcoroutines

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception

class MainViewModel : ViewModel() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    val TAG = this.javaClass.simpleName
    fun LogPrint(): Unit {
        viewModelScope.launch {
            var result1 = withContext(Dispatchers.IO) {
                Log.i(TAG, "result1-1")
                Log.i(TAG, "result1-2")
                Thread.sleep(4000)
                Log.i(TAG, "result1-3")
                "Hello"
            }
            var result2 = withContext(Dispatchers.IO) {
                Log.i(TAG, "result2-1")
                Log.i(TAG, "result2-2")
                Log.i(TAG, "result2-3")
                "world"
            }
            val result = result1 + result2
            Log.i(TAG, result)
        }
    }

    fun LogPrint1() {
        viewModelScope.launch() {
            launch(Dispatchers.IO) {
                Log.i(TAG, "result1-1")
                Log.i(TAG, "result1-2")
                Thread.sleep(4000)
                Log.i(TAG, "result1-3")
            }

            launch(Dispatchers.IO) {
                Log.i(TAG, "result2-1")
                Log.i(TAG, "result2-2")
                Log.i(TAG, "result2-3")
            }
        }
    }

    fun LogAsync() {
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                Thread.sleep(4000)
                Log.i(TAG, "result1-1")
                Log.i(TAG, "result1-2")
                Log.i(TAG, "result1-3")
                "Hello"
            }
            val deferred1 = async {
                Log.i(TAG, "result2-1")
                Log.i(TAG, "result2-2")
                Log.i(TAG, "result2-3")
                "world"
            }
            var str = deferred.await() + deferred1.await()
            Log.i(TAG, str)
        }
    }

    fun catchFun(): Unit {
        viewModelScope.launch(handler) {
            throw IOException()
        }
    }
    fun catch1Fun(): Unit {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                throw IOException()
            }
        }catch (e:Exception){
            Log.i(this.javaClass.name,e.cause?.message?:"抛出了异常")
        }
    }


    fun catch2Fun(): Unit {
        viewModelScope.launch(handler) {
            launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    throw IOException()
                }
            }
        }
    }

    fun catchasyncFun(){
        viewModelScope.launch(handler) {
            val deferred = async(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    throw IOException()
                }
            }
            deferred.await()
        }
    }

    fun catchasyncFun2(){
        viewModelScope.launch {
            val deferred = async(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    try {
                    throw IOException()
                    }catch (e:Exception){
                        Log.i(this.javaClass.name,e.cause?.message?:"抛出了异常")
                    }
                }
            }
             deferred.await()
        }
    }



}