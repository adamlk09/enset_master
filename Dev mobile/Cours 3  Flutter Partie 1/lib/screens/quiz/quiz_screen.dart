import 'package:flutter/material.dart';
import 'package:getready/screens/quiz/widgets/answer_widget.dart';
import 'package:getready/screens/quiz/widgets/question_widget.dart';
import 'package:getready/screens/quiz/widgets/score_widget.dart';

class QuizScreen extends StatefulWidget {
  const QuizScreen({super.key});

  @override
  State<QuizScreen> createState() => _QuizScreenState();
}

class _QuizScreenState extends State<QuizScreen> {
  int _currentQuestionIndex = 0;
  int _score = 0;

  final List<Map<String, dynamic>> _quizData = const [
    {
      'title': 'c\'est quoi debian',
      'answers': [
        {'answer': 'distribution linux', 'correct': true},
        {'answer': 'language de programation', 'correct': false},
        {'answer': 'saas', 'correct': false},
      ]
    },
    {
      'title': 'a quoi signifier ci dans le devops',
      'answers': [
        {'answer': 'continuous integration', 'correct': true},
        {'answer': 'continuous implementation', 'correct': false},
        {'answer': 'continuous injections', 'correct': false},
      ]
    },
  ];

  void _handleAnswer(bool isCorrect) {
    if (isCorrect) {
      _score++;
    }
    setState(() {
      _currentQuestionIndex++;
    });
  }

  void _resetQuiz() {
    setState(() {
      _currentQuestionIndex = 0;
      _score = 0;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Quiz'),
        backgroundColor: Colors.orange,
      ),
      body: _currentQuestionIndex < _quizData.length
          ? ListView(
        padding: const EdgeInsets.all(16.0),
        children: [
          QuestionWidget(
            question: _quizData[_currentQuestionIndex]['title'] as String,
            currentIndex: _currentQuestionIndex,
            totalQuestions: _quizData.length,
          ),
          const SizedBox(height: 20),
          ...(_quizData[_currentQuestionIndex]['answers'] as List<Map<String, dynamic>>).map((answer) {
            return AnswerWidget(
              answerText: answer['answer'] as String,
              onTap: () => _handleAnswer(answer['correct'] as bool),
            );
          }),
        ],
      )
          : ScoreWidget(
        score: _score,
        totalQuestions: _quizData.length,
        onReset: _resetQuiz,
      ),
    );
  }
}