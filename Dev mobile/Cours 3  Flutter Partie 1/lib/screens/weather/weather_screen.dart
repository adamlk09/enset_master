import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:intl/intl.dart';

class WeatherScreen extends StatefulWidget {
  final String city;
  const WeatherScreen({super.key, required this.city});

  @override
  State<WeatherScreen> createState() => _WeatherScreenState();
}

class _WeatherScreenState extends State<WeatherScreen> {
  Map<String, dynamic>? _weatherData;
  bool _isLoading = true;
  String? _error;

  @override
  void initState() {
    super.initState();
    _fetchWeatherData();
  }

  Future<void> _fetchWeatherData() async {
    // IMPORTANT: Replace with your own API key from OpenWeatherMap
    //const apiKey = 'b6907d289e10d714a6e88b30761fae22';
    const apiKey = 'a4578e39643716894ec78b28a71c7110';
    final url = 'https://api.openweathermap.org/data/2.5/forecast?q=${widget.city}&appid=$apiKey&units=metric';

    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        setState(() {
          _weatherData = json.decode(response.body);
          _isLoading = false;
        });
      } else {
        setState(() {
          _error = 'Failed to load weather data. City not found?';
          _isLoading = false;
        });
      }
    } catch (e) {
      setState(() {
        _error = 'Failed to connect. Please check your internet connection.';
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Weather in ${widget.city}'),
        backgroundColor: Colors.orange,
      ),
      body: _buildBody(),
    );
  }

  Widget _buildBody() {
    if (_isLoading) {
      return const Center(child: CircularProgressIndicator());
    }
    if (_error != null) {
      return Center(child: Text(_error!, style: const TextStyle(color: Colors.red, fontSize: 18)));
    }
    if (_weatherData == null || (_weatherData!['list'] as List).isEmpty) {
      return const Center(child: Text('No weather data available.'));
    }

    final forecastList = _weatherData!['list'] as List;

    return ListView.builder(
      itemCount: forecastList.length,
      itemBuilder: (context, index) {
        final item = forecastList[index];
        final weatherMain = item['weather'][0]['main'].toString().toLowerCase();
        final dt = DateTime.fromMillisecondsSinceEpoch(item['dt'] * 1000);

        return Card(
          color: Colors.deepOrangeAccent,
          margin: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0),
          child: Padding(
            padding: const EdgeInsets.all(12.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Row(
                  children: [
                    CircleAvatar(
                      backgroundImage: AssetImage('assets/images/$weatherMain.png'),
                      backgroundColor: Colors.transparent,
                    ),
                    const SizedBox(width: 12),
                    Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          DateFormat('E, dd/MM/yyyy').format(dt),
                          style: const TextStyle(fontSize: 16, color: Colors.white, fontWeight: FontWeight.bold),
                        ),
                        Text(
                          "${DateFormat('HH:mm').format(dt)} | ${item['weather'][0]['main']}",
                          style: const TextStyle(fontSize: 20, color: Colors.white, fontWeight: FontWeight.bold),
                        ),
                      ],
                    ),
                  ],
                ),
                Text(
                  "${item['main']['temp'].round()} °C",
                  style: const TextStyle(fontSize: 22, color: Colors.white, fontWeight: FontWeight.bold),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}