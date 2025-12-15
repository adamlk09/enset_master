import 'package:flutter/material.dart';
import 'package:dash_chat_2/dash_chat_2.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Ollama Chat App',
      theme: ThemeData(
        primarySwatch: Colors.orange,
      ),
      home: const ChatScreen(),
    );
  }
}

class ChatScreen extends StatefulWidget {
  const ChatScreen({super.key});

  @override
  State<ChatScreen> createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {

  final ChatUser _currentUser = ChatUser(id: '1', firstName: 'Me');
  final ChatUser _chatAI = ChatUser(id: '2', firstName: 'Llama3.2');

  List<ChatMessage> _messages = <ChatMessage>[];
  final List<ChatUser> _typingUsers = <ChatUser>[];

  Future<void> getChatResponse(ChatMessage m) async {
    setState(() {
      _messages.insert(0, m);
      _typingUsers.add(_chatAI);
    });

    var messageHistory = _messages.reversed.map((msg) {
      return {
        'role': msg.user == _currentUser ? 'user' : 'assistant',
        'content': msg.text
      };
    }).toList();

    final requestBody = {
      "model": "llama3.2",
      "messages": messageHistory,
      "stream": false,
    };

    try {
      final response = await http.post(
        Uri.parse('http://10.0.2.2:11434/api/chat'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(requestBody),
      );

      if (response.statusCode == 200) {
        final decodedResponse = json.decode(response.body);

        final content = decodedResponse['message']['content'];

        final aiMessage = ChatMessage(
          user: _chatAI,
          createdAt: DateTime.now(),
          text: content,
        );

        setState(() {
          _messages.insert(0, aiMessage);
        });
      } else {
        print("Error: ${response.statusCode}");
        print("Response Body: ${response.body}");
      }
    } catch (e) {
      print("An error occurred: $e");
    } finally {
      setState(() {
        _typingUsers.remove(_chatAI);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      //backgroundColor: Colors.white70,
      appBar: AppBar(
        backgroundColor: Colors.orange,
        //backgroundColor: Theme.of(context).primaryColor,
        title: const Text(
          'Nova CHAT',
          style: TextStyle(color: Colors.white),
        ),
      ),
      body: DashChat(
        currentUser: _currentUser,
        onSend: (ChatMessage m) {
          getChatResponse(m);
        },
        messages: _messages,
        typingUsers: _typingUsers,
        messageOptions: MessageOptions(
          currentUserContainerColor: Colors.orange,
          containerColor: const Color.fromARGB(255, 230, 230, 230),
          textColor: Colors.black,
        ),
      ),
    );
  }
}