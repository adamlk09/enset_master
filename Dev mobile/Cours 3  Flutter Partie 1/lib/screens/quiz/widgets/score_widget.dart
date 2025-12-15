import 'package:flutter/material.dart';

class ScoreWidget extends StatelessWidget {
  final int score;
  final int totalQuestions;
  final VoidCallback onReset;

  const ScoreWidget({
    super.key,
    required this.score,
    required this.totalQuestions,
    required this.onReset,
  });

  @override
  Widget build(BuildContext context) {
    final double percentage = (score / totalQuestions * 100);

    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            'Score: ${percentage.round()}%',
            style: const TextStyle(
              color: Colors.deepOrangeAccent,
              fontSize: 24,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: 20),
          ElevatedButton(
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.deepOrangeAccent,
              foregroundColor: Colors.white,
            ),
            onPressed: onReset,
            child: const Text('Restart', style: TextStyle(fontSize: 22)),
          ),
        ],
      ),
    );
  }
}