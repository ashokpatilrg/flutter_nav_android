package com.poc.nav.flutter_nav_android

import android.content.Intent
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.StringCodec
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class MainActivity: FlutterActivity() {
    private val CHANNEL = "com.channel/android"


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                if (call.method == "perform_operation") {
                    val operationResult = performOperation(call.argument<Int>("arg1")!!, call.argument<Int>("arg2")!!)
                    result.success(operationResult)
                    startNewActivity()
                } else {
                    result.notImplemented()
                }
            }
    }

    private fun performOperation(argument1: Int, argument2: Int): Int {
        return (argument1 + argument2)
    }

   /* override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(FlutterEngine(this@MainActivity))

        flutterEngine?.dartExecutor?.binaryMessenger?.let {
            MethodChannel(it, CHANNEL).setMethodCallHandler(
                object : MethodChannel.MethodCallHandler{
                    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                        if (call.method.equals("startNewActivity")){
                            startNewActivity()
                        }
                    }
                }
            )
        }
    }*/

    private fun startNewActivity() {
        startActivity(Intent(this@MainActivity,SecondActivity::class.java))
    }
}
