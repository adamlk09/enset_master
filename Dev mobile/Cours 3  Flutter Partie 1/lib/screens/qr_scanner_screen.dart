import 'package:flutter/material.dart';
import 'package:barcode_scan2/barcode_scan2.dart';
import 'package:flutter/services.dart';

class QrScannerScreen extends StatefulWidget {
  const QrScannerScreen({super.key});

  @override
  State<QrScannerScreen> createState() => _QrScannerScreenState();
}

class _QrScannerScreenState extends State<QrScannerScreen> {
  String _scanResult = 'Scan a QR code to see the result here.';

  Future<void> _scanQR() async {
    try {
      final result = await BarcodeScanner.scan();
      setState(() {
        _scanResult = result.rawContent;
      });
    } on PlatformException catch (e) {
      if (e.code == BarcodeScanner.cameraAccessDenied) {
        setState(() {
          _scanResult = 'Camera permission was denied.';
        });
      } else {
        setState(() {
          _scanResult = 'An unknown error occurred: $e';
        });
      }
    } on FormatException {
      setState(() {
        _scanResult = 'You pressed the back button before scanning anything.';
      });
    } catch (e) {
      setState(() {
        _scanResult = 'An unexpected error occurred: $e';
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('QR Code Scanner'),
      ),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Text(
            _scanResult,
            style: const TextStyle(fontSize: 18),
            textAlign: TextAlign.center,
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: _scanQR,
        icon: const Icon(Icons.scanner),
        label: const Text('Start Scan'),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }
}