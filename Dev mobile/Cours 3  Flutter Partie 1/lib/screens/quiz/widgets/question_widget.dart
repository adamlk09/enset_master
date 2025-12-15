import 'package:flutter/material.dart';

class QuestionWidget extends StatelessWidget {
  final String question;
  final int currentIndex;
  final int totalQuestions;

  const QuestionWidget({
    super.key,
    required this.question,
    required this.currentIndex,
    required this.totalQuestions,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Text(
          'Question: ${currentIndex + 1}/$totalQuestions',
          style: const TextStyle(
            fontSize: 22,
            fontWeight: FontWeight.bold,
            color: Colors.deepOrangeAccent,
          ),
        ),
        const SizedBox(height: 10),
        Text(
          '$question?',
          style: const TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
          textAlign: TextAlign.center,
        ),
      ],
    );
  }
}