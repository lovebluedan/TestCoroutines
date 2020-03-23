package com.ldr.testcoroutines

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus

open class BaseCoroutineScopeActivity : AppCompatActivity() , CoroutineScope by MainScope()


//open class BaseCoroutineScopeActivity : AppCompatActivity() {
//    val mainLaunch =  MainScope()+ CoroutineName(this.javaClass.simpleName)
//}
