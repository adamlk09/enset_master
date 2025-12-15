import 'package:flutter/material.dart';
import 'package:getready/screens/weather/weather_screen.dart';

class WeatherFormScreen extends StatefulWidget {
  const WeatherFormScreen({super.key});

  @override
  State<WeatherFormScreen> createState() => _WeatherFormScreenState();
}

class _WeatherFormScreenState extends State<WeatherFormScreen> {
  final _cityController = TextEditingController();

  void _getWeather() {
    if (_cityController.text.isNotEmpty) {
      Navigator.of(context).push(MaterialPageRoute(
        builder: (context) => WeatherScreen(city: _cityController.text),
      ));
    }
  }

  @override
  void dispose() {
    _cityController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Enter City'),
        backgroundColor: Colors.deepOrangeAccent,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _cityController,
              decoration: const InputDecoration(
                hintText: 'e.g., London, Tokyo',
                labelText: 'City Name',
                border: OutlineInputBorder(),
              ),
              onSubmitted: (_) => _getWeather(),
            ),
            const SizedBox(height: 20),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: _getWeather,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepOrangeAccent,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 16.0),
                ),
                child: const Text('Get Weather', style: TextStyle(fontSize: 18)),
              ),
            )
          ],
        ),
      ),
    );
  }
}