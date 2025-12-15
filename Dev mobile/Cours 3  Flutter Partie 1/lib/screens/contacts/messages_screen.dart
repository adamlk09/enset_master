import 'dart:async';

import 'package:flutter/material.dart';
import 'package:getready/models/contact_model.dart';
import 'package:getready/models/message_model.dart';
import 'package:getready/services/api_service.dart';
import 'package:intl/intl.dart';

class MessagesScreen extends StatefulWidget {
  final Contact contact;
  const MessagesScreen({super.key, required this.contact});

  @override
  State<MessagesScreen> createState() => _MessagesScreenState();
}

class _MessagesScreenState extends State<MessagesScreen> {
  final _messageController = TextEditingController();
  final _scrollController = ScrollController();
  List<Message> _messages = [];
  bool _isLoading = true;
  String? _error;

  @override
  void initState() {
    super.initState();
    _fetchMessages();
  }

  Future<void> _fetchMessages() async {
    try {
      final messages = await ApiService.getMessagesForContact(widget.contact.id);
      setState(() {
        _messages = messages;
        _isLoading = false;
      });
      _scrollToBottom();
    } catch (e) {
      setState(() {
        _error = "Failed to load messages.";
        _isLoading = false;
      });
    }
  }

  void _scrollToBottom() {
    WidgetsBinding.instance.addPostFrameCallback((_) {
      if (_scrollController.hasClients) {
        _scrollController.animateTo(
          _scrollController.position.maxScrollExtent,
          duration: const Duration(milliseconds: 300),
          curve: Curves.easeOut,
        );
      }
    });
  }

  Future<void> _sendMessage() async {
    if (_messageController.text.trim().isEmpty) return;

    final newMessage = Message(
      contactID: widget.contact.id,
      message: _messageController.text.trim(),
      type: 'sent',
      dateTime: DateTime.now(),
    );

    _messageController.clear();
    setState(() {
      _messages.add(newMessage);
    });
    _scrollToBottom();

    try {
      await ApiService.sendMessage(newMessage);
      // await _fetchMessages();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Failed to send message.')),
      );
      setState(() {
        _messages.remove(newMessage);
      });
    }
  }

  @override
  void dispose() {
    _messageController.dispose();
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Chat with ${widget.contact.name}')),
      body: Column(
        children: [
          Expanded(child: _buildMessagesList()),
          _buildMessageComposer(),
        ],
      ),
    );
  }

  Widget _buildMessagesList() {
    if (_isLoading) {
      return const Center(child: CircularProgressIndicator());
    }
    if (_error != null) {
      return Center(child: Text(_error!));
    }
    return ListView.builder(
      controller: _scrollController,
      padding: const EdgeInsets.all(8.0),
      itemCount: _messages.length,
      itemBuilder: (context, index) {
        final message = _messages[index];
        final isSent = message.type == 'sent';
        return Align(
          alignment: isSent ? Alignment.centerRight : Alignment.centerLeft,
          child: Container(
            margin: const EdgeInsets.symmetric(vertical: 4.0),
            padding: const EdgeInsets.symmetric(horizontal: 14.0, vertical: 10.0),
            decoration: BoxDecoration(
              color: isSent ? Theme.of(context).primaryColor : Colors.grey[300],
              borderRadius: BorderRadius.circular(20),
            ),
            child: Text(
              message.message,
              style: TextStyle(color: isSent ? Colors.white : Colors.black87),
            ),
          ),
        );
      },
    );
  }

  Widget _buildMessageComposer() {
    return Container(
      padding: const EdgeInsets.all(8.0),
      color: Colors.white,
      child: Row(
        children: [
          Expanded(
            child: TextField(
              controller: _messageController,
              decoration: const InputDecoration(
                hintText: 'Type a message...',
                border: OutlineInputBorder(),
                contentPadding: EdgeInsets.symmetric(horizontal: 12.0),
              ),
              onSubmitted: (_) => _sendMessage(),
            ),
          ),
          IconButton(
            icon: const Icon(Icons.send),
            onPressed: _sendMessage,
            color: Theme.of(context).primaryColor,
          ),
        ],
      ),
    );
  }
}