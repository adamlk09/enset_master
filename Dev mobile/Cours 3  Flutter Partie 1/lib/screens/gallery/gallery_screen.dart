import 'package:flutter/material.dart';
import 'package:getready/screens/gallery/gallery_data_screen.dart';

class GalleryScreen extends StatefulWidget {
  const GalleryScreen({super.key});

  @override
  State<GalleryScreen> createState() => _GalleryScreenState();
}

class _GalleryScreenState extends State<GalleryScreen> {
  final _keywordController = TextEditingController();

  void _searchImages() {
    if (_keywordController.text.isNotEmpty) {
      Navigator.of(context).push(MaterialPageRoute(
        builder: (context) => GalleryDataScreen(keyword: _keywordController.text),
      ));
    }
  }

  @override
  void dispose() {
    _keywordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Image Gallery'),
        backgroundColor: Colors.deepOrange,
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _keywordController,
              decoration: const InputDecoration(
                hintText: 'e.g., mountains, dogs',
                labelText: 'Search Keyword',
                border: OutlineInputBorder(),
              ),
              onSubmitted: (_) => _searchImages(),
            ),
            const SizedBox(height: 20),
            SizedBox(
              width: double.infinity,
              child: ElevatedButton(
                onPressed: _searchImages,
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.deepOrange,
                  foregroundColor: Colors.white,
                  padding: const EdgeInsets.symmetric(vertical: 16.0),
                ),
                child: const Text('Get Images', style: TextStyle(fontSize: 18)),
              ),
            ),
          ],
        ),
      ),
    );
  }
}