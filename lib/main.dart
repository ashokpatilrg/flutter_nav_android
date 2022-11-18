import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';

const platform = MethodChannel('test_activity');

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Method Channel',
      theme: ThemeData(
        primarySwatch: Colors.red,
      ),
      home: MyHomePage(title: 'Platform Method Channel'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel('com.channel/android');

  String _operationResultState = 'Unknown operation';

  Future<void> _performOperation() async {
    String operationResult;
    try {
      final int result = await platform
          .invokeMethod('perform_operation', {"arg1": 20, "arg2": 40});
      operationResult = 'Performed operation value is $result .';
    } on PlatformException catch (e) {
      operationResult = "Failed to perform operation: '${e.message}'.";
    }
    setState(() {
      _operationResultState = operationResult;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: <Widget>[
            ElevatedButton(
                onPressed: () {
                  _performOperation();
                },
                child: Text('Perform Operation')),
            Text(
              _operationResultState,
              style: TextStyle(fontSize: 22),
            )
          ],
        ),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }

  _getNewActivity() async {
    try {
      await platform.invokeMethod('startNewActivity');
    } on PlatformException catch (e) {
      print(e.message);
    }
  }
}
