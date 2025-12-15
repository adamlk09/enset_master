import 'package:flutter/material.dart';
import 'package:getready/widgets/main_drawer.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.orange,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {
  const MyHomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('My Professor App'),
        backgroundColor: Colors.orange,
      ),
      drawer: const MainDrawer(),
      body: const Center(
        child: Text(
          'Hola! Choisir un service dans le menu.',
          style: TextStyle(fontSize: 22),
          textAlign: TextAlign.center,
        ),
      ),
    );
  }
}