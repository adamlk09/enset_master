import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class GalleryDataScreen extends StatefulWidget {
  final String keyword;
  const GalleryDataScreen({super.key, required this.keyword});

  @override
  State<GalleryDataScreen> createState() => _GalleryDataScreenState();
}

class _GalleryDataScreenState extends State<GalleryDataScreen> {
  final _scrollController = ScrollController();
  List<dynamic> _hits = [];
  int _currentPage = 1;
  int _totalPages = 0;
  bool _isLoading = false;
  String? _error;

  // IMPORTANT get API key from pixabay.com
  final String _apiKey = '5832566-81dc7429a63c86e3b707d0429';

  @override
  void initState() {
    super.initState();
    _fetchImages();
    _scrollController.addListener(_onScroll);
  }

  void _onScroll() {
    if (_scrollController.position.pixels == _scrollController.position.maxScrollExtent) {
      if (_currentPage < _totalPages && !_isLoading) {
        _fetchImages();
      }
    }
  }

  Future<void> _fetchImages() async {
    setState(() {
      _isLoading = true;
      if (_currentPage > 1) _error = null;
    });

    final url = "https://pixabay.com/api/?key=$_apiKey&q=${widget.keyword}&page=$_currentPage&per_page=10";

    try {
      final response = await http.get(Uri.parse(url));
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        setState(() {
          _hits.addAll(data['hits']);
          final totalHits = data['totalHits'] as int;
          _totalPages = (totalHits / 10).ceil();
          _currentPage++;
        });
      } else {
        setState(() {
          _error = "Failed to load images. Please try again.";
        });
      }
    } catch (e) {
      setState(() {
        _error = "Failed to connect. Check your internet connection.";
      });
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('${widget.keyword} (${_hits.length} images)'),
        backgroundColor: Colors.deepOrange,
      ),
      body: _buildBody(),
    );
  }

  Widget _buildBody() {
    if (_hits.isEmpty && _isLoading) {
      return const Center(child: CircularProgressIndicator());
    }
    if (_error != null && _hits.isEmpty) {
      return Center(child: Text(_error!, style: const TextStyle(color: Colors.red, fontSize: 18)));
    }
    if (_hits.isEmpty) {
      return const Center(child: Text("No images found for this keyword.", style: TextStyle(fontSize: 18)));
    }

    return ListView.builder(
      controller: _scrollController,
      itemCount: _hits.length + (_isLoading ? 1 : 0),
      itemBuilder: (context, index) {
        if (index == _hits.length) {
          return const Center(child: Padding(
            padding: EdgeInsets.all(8.0),
            child: CircularProgressIndicator(),
          ));
        }
        final hit = _hits[index];
        return Card(
          margin: const EdgeInsets.symmetric(horizontal: 8.0, vertical: 4.0),
          child: Column(
            children: [
              Container(
                width: double.infinity,
                color: Colors.deepOrange,
                padding: const EdgeInsets.all(8.0),
                child: Text(
                  hit['tags'],
                  style: const TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
                  textAlign: TextAlign.center,
                ),
              ),
              Image.network(hit['webformatURL'], fit: BoxFit.fitWidth),
            ],
          ),
        );
      },
    );
  }
}