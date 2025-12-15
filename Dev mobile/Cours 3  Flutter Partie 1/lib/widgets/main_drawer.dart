import 'package:flutter/material.dart';
import 'package:getready/screens/camera_screen.dart';
import 'package:getready/screens/contacts/contacts_screen.dart';
import 'package:getready/screens/gallery/gallery_screen.dart';
import 'package:getready/screens/quiz/quiz_screen.dart';
import 'package:getready/screens/qr_scanner_screen.dart';
import 'package:getready/screens/weather/weather_form_screen.dart';

class MainDrawer extends StatelessWidget {
  const MainDrawer({super.key});

  void _navigateTo(BuildContext context, Widget screen) {
    Navigator.of(context).pop();
    Navigator.of(context).push(MaterialPageRoute(builder: (_) => screen));
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: [
          DrawerHeader(
            decoration: const BoxDecoration(
              gradient: LinearGradient(
                colors: [Colors.orange, Colors.white],
                begin: Alignment.topLeft,
                end: Alignment.bottomRight,
              ),
            ),
            child: Center(
              child: CircleAvatar(
                radius: 50,
                backgroundImage: AssetImage('assets/images/profile.jpg'),
              ),
            ),
          ),
          ListTile(
            leading: const Icon(Icons.quiz),
            title: const Text('Quiz', style: TextStyle(fontSize: 18)),
            trailing: const Icon(Icons.arrow_forward_ios),
            onTap: () => _navigateTo(context, const QuizScreen()),
          ),
          ListTile(
            leading: const Icon(Icons.cloud),
            title: const Text('Weather', style: TextStyle(fontSize: 18)),
            trailing: const Icon(Icons.arrow_forward_ios),
            onTap: () => _navigateTo(context, WeatherFormScreen()),
          ),
          ListTile(
            leading: const Icon(Icons.photo_library),
            title: const Text('Gallery', style: TextStyle(fontSize: 18)),
            trailing: const Icon(Icons.arrow_forward_ios),
            onTap: () => _navigateTo(context, GalleryScreen()),
          ),
          ListTile(
            leading: const Icon(Icons.camera_alt),
            title: const Text('Camera OCR', style: TextStyle(fontSize: 18)),
            trailing: const Icon(Icons.arrow_forward_ios),
            onTap: () => _navigateTo(context, CameraScreen()),
          ),
          ListTile(
            leading: const Icon(Icons.qr_code_scanner),
            title: const Text('QR Scanner', style: TextStyle(fontSize: 18)),
            trailing: const Icon(Icons.arrow_forward_ios),
            onTap: () => _navigateTo(context, const QrScannerScreen()),
          ),
          ListTile(
            leading: const Icon(Icons.contacts),
            title: const Text('Contacts', style: TextStyle(fontSize: 18)),
            trailing: const Icon(Icons.arrow_forward_ios),
            onTap: () => _navigateTo(context, const ContactsScreen()),
          ),
        ],
      ),
    );
  }
}