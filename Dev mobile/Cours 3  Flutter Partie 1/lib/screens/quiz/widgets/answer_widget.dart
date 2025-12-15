import 'package:flutter/material.dart';

class AnswerWidget extends StatelessWidget {
  final String answerText;
  final VoidCallback onTap;

  const AnswerWidget({
    super.key,
    required this.answerText,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0),
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          backgroundColor: Colors.deepOrangeAccent,
          foregroundColor: Colors.white,
          padding: const EdgeInsets.all(12),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8),
          ),
        ),
        onPressed: onTap,
        child: SizedBox(
          width: double.infinity,
          child: Text(
            answerText,
            textAlign: TextAlign.left,
            style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
          ),
        ),
      ),
    );
  }
}